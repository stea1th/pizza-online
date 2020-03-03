package de.stea1th.commonslibrary.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
public class PdfCreatorDto {

    private OrderDto orderDto;

    private List<ProductCostInCartDto> productCostInCartDtoList;

}
