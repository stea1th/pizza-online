package de.stealth.personservice.service;

import de.stealth.personservice.entity.Person;
import de.stealth.personservice.repository.AddressRepository;
import de.stealth.personservice.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

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
        log.info("Getting person by personId: {}", personId);
        return personRepository.getOne(personId);
    }

    @Override
    @Transactional
    public Person getByKeycloak(String keycloak) {
        keycloak = removeQuotes(keycloak);
        keycloak = addQuotes(keycloak);

        Person person = personRepository.getByKeycloak(keycloak);
        if (person == null) {
            log.error("No such person with keycloak id: {} exists", keycloak);
            person = new Person();
            person.setKeycloak(keycloak);
            var address = addressRepository.createEmptyAddress();
            person.setAddress(address);
            log.info("New person with keycloak id: {} created", keycloak);
            person = save(person);
        }
        return person;
    }

    @Transactional
    @Override
    public Person save(Person person) {
        log.info("Person: {} successful saved", person);
        addressRepository.save(person.getAddress());
        person.setKeycloak(addQuotes(person.getKeycloak()));
        return personRepository.save(person);
    }


    public Person getByPrincipal(Principal principal) {
        log.info("Getting person by principal: {}", principal);

        KeycloakAuthenticationToken keycloakAuthenticationToken = (KeycloakAuthenticationToken) principal;
        String keycloak = keycloakAuthenticationToken.getName();
        Person person = getByKeycloak(keycloak);
        if (person != null) {
            KeycloakSecurityContext securityContext = ((KeycloakPrincipal) keycloakAuthenticationToken.getPrincipal()).getKeycloakSecurityContext();
            AccessToken accessToken = securityContext.getToken();
            setPersonDetails(accessToken, person);
        }
        return person;
    }

    private void setPersonDetails(AccessToken accessToken, Person person) {
        boolean needSave = false;
        if (person.getFirstName() == null) {
            person.setFirstName(accessToken.getGivenName());
            needSave = true;
        }
        if (person.getLastName() == null) {
            person.setLastName(accessToken.getFamilyName());
            needSave = true;
        }
        if (person.getEmail() == null) {
            person.setEmail(accessToken.getEmail());
            needSave = true;
        }
        if (needSave) save(person);
    }

    private String removeQuotes(String keycloak) {
        return keycloak.replace("\\\"", "");
    }

    private String addQuotes(String keycloak) {
        return keycloak.contains("\"") ?  keycloak : "\"" + keycloak + "\"";
    }
}
