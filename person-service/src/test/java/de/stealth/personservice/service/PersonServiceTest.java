package de.stealth.personservice.service;

import de.stealth.personservice.TestDataGenerator;
import de.stealth.personservice.entity.Address;
import de.stealth.personservice.entity.Person;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonServiceTest {

    private static Person person;

    @BeforeAll
    public static void setUp() {
        person = TestDataGenerator.generatePerson();
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
