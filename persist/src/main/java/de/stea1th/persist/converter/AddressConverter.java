package de.stea1th.persist.converter;

import de.stea1th.commonslibrary.dto.AddressDto;
import de.stea1th.persist.entity.Address;
import lombok.var;
import org.springframework.stereotype.Component;

@Component
public class AddressConverter {

    public AddressDto convertToDto(Address address) {
        var addressDto = new AddressDto();
        addressDto.setId(address.getId());
        addressDto.setStreet(address.getStreet());
        addressDto.setZip(address.getZip());
        addressDto.setCity(address.getCity());
        addressDto.setCountry(address.getCountry());
        return addressDto;
    }
}
