package de.stea1th.orderservice.dto;

import de.stea1th.commonslibrary.dto.AbstractBaseDto;
import de.stea1th.commonslibrary.dto.AddressDto;
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
