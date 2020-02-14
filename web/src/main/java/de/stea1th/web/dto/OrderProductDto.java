package de.stea1th.web.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductDto implements Serializable {

    private String keycloak;

    private int productId;

    private int quantity;
}
