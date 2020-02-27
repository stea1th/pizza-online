package de.stea1th.persist.service;


import de.stea1th.commonslibrary.dto.PersonDto;
import de.stea1th.persist.entity.Person;

public interface PersonService {

    PersonDto get(int personId);

    Person getByKeycloak(String keycloak);

    PersonDto getDtoByKeycloak(String keycloak);

    Person save(Person person);
}
