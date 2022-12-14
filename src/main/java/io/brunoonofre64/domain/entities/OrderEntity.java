package io.brunoonofre64.domain.entities;

import io.brunoonofre64.domain.enums.Status;
import lombok.*;
import org.hibernate.annotations.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TBL_ORDER")
@SQLDelete(sql = "UPDATE TBL_ORDER SET STATUS = 'CANCELED' WHERE ID = ?", check = ResultCheckStyle.COUNT)
@FilterDef(name = "deleteOrder", parameters = @ParamDef(name = "deleted", type = "Status"))
@Filter(name = "deleteOrder", condition = "Status = :deleted")
@SequenceGenerator(name = "requestSequence", sequenceName = "SQ_request", allocationSize = 1)
public class OrderEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "requestSequence")
    @Column(name = "ID")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false)
    private Status status = Status.APPROVED;

    @Column(name = "TOTAL", nullable = false)
    private BigDecimal total;

    @ManyToOne
    @JoinColumn(name = "CUSTOMER_ID", nullable = false)
    private CustomerEntity customer;

    @OneToMany(mappedBy = "orderEntity")
    private List<OrderItemsEntity> orderItems;

    public OrderEntity(String uuid, LocalDateTime inclusionDate, LocalDateTime modifyDate, Long id, Status status,
                       BigDecimal total, CustomerEntity customer, List<OrderItemsEntity> orderItems) {
        super(uuid, inclusionDate, modifyDate);
        this.id = id;
        this.status = status;
        this.total = total;
        this.customer = customer;
        this.orderItems = orderItems;
    }
}
