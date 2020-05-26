package de.stealth.personservice.service;


import de.stea1th.commonslibrary.dto.PersonDto;
import de.stealth.personservice.entity.Person;

public interface PersonService {

    Person get(int personId);

    Person getByKeycloak(String keycloak);

    Person save(Person person);
}
