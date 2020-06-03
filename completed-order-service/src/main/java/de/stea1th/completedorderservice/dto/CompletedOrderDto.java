package de.stea1th.completedorderservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


@Data
@NoArgsConstructor
public class CompletedOrderDto {

    private OrderDto orderDto;

    private List<ProductCostInCartDto> productCostInCartDtoList;

}
