package de.stea1th.completedorderservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
public class CompletedOrderDto {

    private OrderDto orderDto;

    private List<CartElementDto> cartElementList;

}
