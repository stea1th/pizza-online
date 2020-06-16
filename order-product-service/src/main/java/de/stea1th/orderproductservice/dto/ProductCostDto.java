package de.stea1th.orderproductservice.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class ProductCostDto {

    private Integer id;

    private String property;

    private BigDecimal price;

    private Integer discount;

    private ProductDto product;

    private boolean frozen;

}
