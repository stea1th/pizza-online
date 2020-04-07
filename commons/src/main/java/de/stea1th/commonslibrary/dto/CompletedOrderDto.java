package de.stea1th.commonslibrary.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


@Data
@NoArgsConstructor
public class CompletedOrderDto implements Serializable {

    private OrderDto orderDto;

    private List<ProductCostInCartDto> productCostInCartDtoList;

}
