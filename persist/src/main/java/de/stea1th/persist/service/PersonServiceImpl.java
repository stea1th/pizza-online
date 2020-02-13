package de.stea1th.persist.service;

import de.stea1th.kafkalibrary.exception.MyEntityNotFoundException;
import de.stea1th.persist.entity.Person;
import de.stea1th.persist.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    @Transactional
    public Person get(int personId) {
        Person person = null;
        try {
            person = personRepository.get(personId);
        } catch (MyEntityNotFoundException e) {
            log.error(e.getMessage());
        }
        return person;
    }

    @Override
    @Transactional
    public Person getByKeycloak(String keycloak) {
        Person person = personRepository.getByKeycloak(keycloak);
        if(person == null) {
            log.error("no such person with keycloak id: {} exists", keycloak);
            person = new Person();
            person.setKeycloak(keycloak);
            log.info("new person with keycloak id: {} created", keycloak);
            person = save(person);
        }
        return person;
    }

    @Override
    @Transactional
    public Person save(Person person) {
        log.info("person: {} successful saved", person);
        return personRepository.save(person);
    }
}
