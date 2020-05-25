package de.stealth.personservice.service;

import de.stea1th.commonslibrary.dto.PersonDto;
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
    @Override
    public PersonDto get(int personId) {
        return null;
    }

    @Override
    public Person getByKeycloak(String keycloak) {
        return null;
    }

    @Override
    public PersonDto getDtoByKeycloak(String keycloak) {
        return null;
    }

    @Override
    public Person save(Person person) {
        return null;
    }

//    private final PersonRepository personRepository;
//
//    private final AddressRepository addressRepository;
//
//    private final PersonConverter personConverter;
//
//    @Autowired
//    public PersonServiceImpl(PersonRepository personRepository, AddressRepository addressRepository, PersonConverter personConverter) {
//        this.personRepository = personRepository;
//        this.addressRepository = addressRepository;
//        this.personConverter = personConverter;
//    }
//
//    @Override
//    @Transactional
//    public PersonDto get(int personId) {
//        Person person = personRepository.getOne(personId);
//        return personConverter.convertToDto(person);
//    }
//
//    @Override
//    @Transactional
//    public Person getByKeycloak(String keycloak) {
//        keycloak = addQuotes(keycloak);
//        Person person = personRepository.getByKeycloak(keycloak);
//        if (person == null) {
//            log.error("no such person with keycloak id: {} exists", keycloak);
//            person = new Person();
//            person.setKeycloak(keycloak);
//            var address = addressRepository.createEmptyAddress();
//            person.setAddress(address);
//            log.info("new person with keycloak id: {} created", keycloak);
//            person = save(person);
//        }
//        return person;
//    }
//
//    @Override
//    public PersonDto getDtoByKeycloak(String keycloak) {
//        var person = getByKeycloak(keycloak);
//        return personConverter.convertToDto(person);
//    }
//
//    @Transactional
//    @Override
//    public Person save(Person person) {
//        log.info("person: {} successful saved", person);
//        addressRepository.save(person.getAddress());
//        return personRepository.save(person);
//    }
//
//    private String addQuotes(String keycloak) {
//        return keycloak.contains("\"") ?  keycloak : "\"" + keycloak + "\"";
//    }
}
