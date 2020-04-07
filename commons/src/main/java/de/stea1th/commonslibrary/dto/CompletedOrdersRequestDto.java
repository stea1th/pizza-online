package de.stea1th.commonslibrary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompletedOrdersRequestDto implements Serializable {

    private String keycloak;
    private String value;

}
