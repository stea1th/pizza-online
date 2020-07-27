package de.stealth.personservice.repository;

import de.stealth.personservice.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface PersonRepository extends JpaRepository<Person, Integer> {

    Person findByKeycloak(String keycloak);
}
