package de.stea1th.persist.service;


import de.stea1th.persist.entity.Person;

public interface PersonService {

    Person get(int personId);

    Person getByKeycloak(String keycloak);

    Person save(Person person);
}
