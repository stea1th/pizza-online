package de.stea1th.persist.repository;

import de.stea1th.kafkalibrary.exception.MyEntityNotFoundException;
import de.stea1th.persist.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface PersonRepository extends JpaRepository<Person, Integer> {

    default Person get(int id) throws MyEntityNotFoundException {
        return this.findById(id).orElseThrow(() -> new MyEntityNotFoundException("no such person with id: " + id + " exists"));
    }

    Person getByKeycloak(String keycloak);
}
