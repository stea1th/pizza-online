package de.stealth.personservice.service;


import de.stealth.personservice.entity.Person;

public interface PersonService {

    Person get(int personId);

    Person getByKeycloak(String keycloak);

    Person save(Person person);
}
