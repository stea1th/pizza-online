package de.stea1th.commonslibrary.dto;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class ProductCostInCartDto extends ProductCostDto {

    private ProductDto product;

    private int quantity;

}
