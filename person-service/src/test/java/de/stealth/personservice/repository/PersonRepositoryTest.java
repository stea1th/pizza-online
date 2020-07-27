package de.stealth.personservice.repository;

import de.stealth.personservice.entity.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ActiveProfiles("test")
class PersonRepositoryTest {

//    @Autowired
//    private TestEntityManager entityManager;

    @Autowired
    private PersonRepository personRepository;

//    private static Person person;

//    @BeforeAll
//    static void setUp() {
//        person = TestDataGenerator.generatePerson();
//        person.setId(null);
//    }

    @Test
    void whenFindByStoredKeycloak_ThenReturnPerson() {
        String keycloak = "\"cd55a854-1b96-44d0-ba30-73176a0708f5\"";
//        entityManager.persist(person.getAddress());
//        entityManager.persist(person);
//        entityManager.flush();

        Person current = personRepository.findByKeycloak(keycloak);

        assertEquals(current.getFirstName(), "Ivan");
    }
}
