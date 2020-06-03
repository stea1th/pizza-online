package de.stea1th.completedorderservice.dto;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class ProductCostInCartDto extends AbstractBaseDto {

    private String property;

    private BigDecimal price;

    private Integer discount;

    private boolean frozen;

    private ProductDto product;

    private int quantity;
}
