package de.stea1th.completedorderservice.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class CartElementDto {

    private Integer id;

    private String property;

    private BigDecimal price;

    private Integer discount;

    private boolean frozen;

    private ProductDto product;

    private int quantity;
}
