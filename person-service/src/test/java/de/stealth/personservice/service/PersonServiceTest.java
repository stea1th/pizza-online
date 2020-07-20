package de.stealth.personservice.service;

import de.stealth.personservice.entity.Address;
import de.stealth.personservice.entity.Person;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonServiceTest {

    private static Person person;
    private static Address address;

    @BeforeAll
    public static void setUp() {
        address = new Address();
        address.setId(997);
        address.setStreet("Sedulinos");
        address.setCity("Visaginas");
        address.setZip("1234");
        address.setCountry("Lithuania");

        person = new Person();
        person.setId(1001);
        person.setFirstName("Dmitrij");
        person.setLastName("Gusev");
        person.setEmail("a@a.de");
        person.setAddress(address);
    }

    @Test
    void get() {
    }

    @Test
    void getByKeycloak() {
    }

    @Test
    void save() {
    }

    @Test
    void getByPrincipal() {
    }
}
