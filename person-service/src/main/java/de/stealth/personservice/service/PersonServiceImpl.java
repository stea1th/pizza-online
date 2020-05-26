package de.stealth.personservice.service;

import de.stealth.personservice.entity.Person;
import de.stealth.personservice.repository.AddressRepository;
import de.stealth.personservice.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    private final AddressRepository addressRepository;

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository, AddressRepository addressRepository) {
        this.personRepository = personRepository;
        this.addressRepository = addressRepository;
    }

    @Override
    @Transactional
    public Person get(int personId) {
        return personRepository.getOne(personId);
    }

    @Override
    @Transactional
    public Person getByKeycloak(String keycloak) {

        Person person = personRepository.getByKeycloak(keycloak);
        if (person == null) {
            log.error("no such person with keycloak id: {} exists", keycloak);
            person = new Person();
            person.setKeycloak(keycloak);
            var address = addressRepository.createEmptyAddress();
            person.setAddress(address);
            log.info("new person with keycloak id: {} created", keycloak);
            person = save(person);
        }
        return person;
    }

    @Transactional
    @Override
    public Person save(Person person) {
        log.info("person: {} successful saved", person);
        addressRepository.save(person.getAddress());
        return personRepository.save(person);
    }

    private String removeQuotes(String keycloak) {
        return keycloak.replace("\\\"", "");
    }

    private String addQuotes(String keycloak) {
        return keycloak.contains("\"") ?  keycloak : "\"" + keycloak + "\"";
    }
}
