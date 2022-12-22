package io.brunoonofre64.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class OrderItemsInputDTO {

    private Long product;

    private BigDecimal amount;
}
