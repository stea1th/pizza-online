package de.stealth.personservice.service;

import de.stealth.personservice.TestDataGenerator;
import de.stealth.personservice.entity.Person;
import de.stealth.personservice.repository.PersonRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.security.Principal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@SpringBootTest
@ActiveProfiles("test")
class PersonServiceTest {

    @MockBean
    private PersonRepository personRepository;

    @MockBean
    private Principal principal;

    @Autowired
    private PersonService personService;

    private static Person person;

    @BeforeAll
    public static void setUp() {
        person = TestDataGenerator.generatePerson();
    }

    @Test
    void get_ById_ReturnPerson() {

        int id = person.getId();
        given(personRepository.getOne(id)).willReturn(person);

        Person current = personService.get(id);

        assertEquals(person, current);
    }

    @Test
    void get_ByKeycloak_ReturnPerson() {
        String keycloak = person.getKeycloak();
        given(personRepository.getByKeycloak(keycloak)).willReturn(person);

        Person current = personService.getByKeycloak(keycloak);

        assertEquals(person, current);
    }

    @Test
    void save() {
    }

    @Test
    void getByPrincipal() {
        String keycloak = person.getKeycloak();
//        given(principal.getName()).willReturn()
    }
}
