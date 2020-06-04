package de.stea1th.completedorderservice.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductCostDto {

    private int productCostId;

    private BigDecimal price;

    private Integer discount;

    private int quantity;
}
