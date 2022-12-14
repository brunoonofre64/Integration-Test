package io.brunoonofre64.infrastructure.service;

import io.brunoonofre64.domain.dto.order.OrderInputDTO;
import io.brunoonofre64.domain.dto.order.OrderInformationDTO;
import io.brunoonofre64.domain.dto.order.OrderNewStatusDTO;
import io.brunoonofre64.domain.dto.orderitems.OrderItemsInputDTO;
import io.brunoonofre64.domain.dto.order.OrderOutputDTO;
import io.brunoonofre64.domain.entities.CustomerEntity;
import io.brunoonofre64.domain.entities.OrderEntity;
import io.brunoonofre64.domain.entities.OrderItemsEntity;
import io.brunoonofre64.domain.entities.ProductEntity;
import io.brunoonofre64.domain.enums.CodeMessage;
import io.brunoonofre64.domain.exception.*;
import io.brunoonofre64.domain.mapper.OrderMapper;
import io.brunoonofre64.domain.service.OrderService;
import io.brunoonofre64.infrastructure.jpa.CustomerRepository;
import io.brunoonofre64.infrastructure.jpa.OrderItemsRepository;
import io.brunoonofre64.infrastructure.jpa.OrderRepository;
import io.brunoonofre64.infrastructure.jpa.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;

    private CustomerRepository customerRepository;

    private ProductRepository productRepository;

    private OrderItemsRepository orderItemsRepository;

    private OrderMapper orderMapper;

    @Override
    @Transactional
    public OrderOutputDTO saveNewOrderInDb(OrderInputDTO dto) {
       validateOrder(dto);

       CustomerEntity customer = getCustomerInDatabase(dto);
       OrderEntity order = orderMapper.convertDTOAndCustomerToOrderEntity(dto, customer);

       OrderEntity orderSuccess = orderRepository.save(order);

       List<OrderItemsEntity> orderItems = getOrderItems(orderSuccess, dto.getOrderItems());
       order.setOrderItems(orderItems);

       orderItemsRepository.saveAll(orderItems);

       return orderMapper.convertEntityToDTO(order, customer, orderItems);
    }

    @Override
    public OrderInformationDTO getOrderItemsInformationByUuid(String uuid) {
        validateOrderUuid(uuid);

        OrderEntity orderItems = orderRepository.findByUuidAndFetchOrderItems(uuid);

        return orderMapper.convertOrderItemsToInformationsDTO(orderItems);
    }

    @Override
    public OrderInformationDTO updateStatusOrderByUuid(String uuid, OrderNewStatusDTO dto) {
        validateOrderUuid(uuid);

        if(ObjectUtils.isEmpty(dto.getStatus())) {
            throw new DtoNullOrIsEmptyException(CodeMessage.DTO_NULL_OR_IS_EMPTY);
        }
        OrderEntity order = orderRepository.findByUuid(uuid);
        order.setStatus(dto.getStatus());

        orderRepository.save(order);
        return orderMapper.convertOrderItemsToInformationsDTO(order);
    }

    @Transactional
    public void deleteOrderByUuid(String uuid) {
        validateOrderUuid(uuid);

        orderRepository.deleteByUuid(uuid);
    }

    public CustomerEntity getCustomerInDatabase(OrderInputDTO dto) {
        if(ObjectUtils.isEmpty(dto)) {
            throw new DtoNullOrIsEmptyException(CodeMessage.DTO_NULL_OR_IS_EMPTY);
        }
            String uuidCustomer = dto.getCustomer();

        try{
           return customerRepository.findByUuid(uuidCustomer);
        } catch(Exception ex) {
            throw new OrderNotFoundException(CodeMessage.ORDER_NOT_FOUND);
        }
    }

    public ProductEntity getProductInDatabase(OrderItemsInputDTO dto) {
        if(ObjectUtils.isEmpty(dto)) {
            throw new UuidNotFoundOrNullException(CodeMessage.DTO_NULL_OR_IS_EMPTY);
        }
         String uuidProduct = dto.getUuidProduct();

        try{
            return productRepository.findByUuid(uuidProduct);
        } catch (Exception ex) {
            throw new ProductNotFoundException(CodeMessage.PRODUCT_NOT_FOUND);
        }
    }

    private void validateOrderUuid(String uuid) {
        if(ObjectUtils.isEmpty(uuid) || !orderRepository.existsByUuid(uuid)) {
            throw new OrderNotFoundException(CodeMessage.ORDER_NOT_FOUND);
        }
    }

    private void validateOrder(OrderInputDTO dto) {
       if(ObjectUtils.isEmpty(dto.getCustomer()) && ObjectUtils.isEmpty(dto.getTotal())
                && CollectionUtils.isEmpty(dto.getOrderItems())) {
           throw new DtoNullOrIsEmptyException(CodeMessage.DTO_NULL_OR_IS_EMPTY);
       }
    }

    private List<OrderItemsEntity> getOrderItems(OrderEntity order, List<OrderItemsInputDTO> orderitems) {
        if(CollectionUtils.isEmpty(orderitems)) {
            throw new ListIsEmptyException(CodeMessage.ORDER_NOT_FOUND);
        }
        if(order == null) {
            throw new OrderNotFoundException(CodeMessage.ORDER_NOT_FOUND);
        }

        return orderitems
                .stream()
                .map(dto -> {
                    ProductEntity product =  getProductInDatabase(dto);

                    return OrderItemsEntity
                            .builder()
                            .orderEntity(order)
                            .amount(dto.getAmount())
                            .product(product)
                            .build();
                }).collect(Collectors.toList());
    }
}
