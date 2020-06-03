package de.stea1th.completedorderservice.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductCostDto implements Serializable {

    private String keycloak;

    private int productCostId;

    private int quantity;
}
