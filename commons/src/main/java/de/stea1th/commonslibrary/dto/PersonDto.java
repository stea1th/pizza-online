package de.stea1th.commonslibrary.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class PersonDto extends AbstractBaseDto {

    private String firstName;

    private String lastName;

    private String email;

    private String keycloak;

    private AddressDto address;
}
