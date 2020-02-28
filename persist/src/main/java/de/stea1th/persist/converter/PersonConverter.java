package de.stea1th.persist.converter;

import de.stea1th.commonslibrary.dto.PersonDto;
import de.stea1th.persist.entity.Person;
import lombok.var;
import org.springframework.stereotype.Component;

@Component
public class PersonConverter {

    private AddressConverter addressConverter;

    public PersonConverter(AddressConverter addressConverter) {
        this.addressConverter = addressConverter;
    }

    public PersonDto convertToDto(Person person) {
        var personDto = new PersonDto();
        personDto.setId(person.getId());
        personDto.setFirstName(person.getFirstName());
        personDto.setLastName(person.getLastName());
        personDto.setEmail(person.getEmail());
        personDto.setKeycloak(person.getKeycloak());
        var addressDto = addressConverter.convertToDto(person.getAddress());
        personDto.setAddress(addressDto);
        return personDto;
    }
}
