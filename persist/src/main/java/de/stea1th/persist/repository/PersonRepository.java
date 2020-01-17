package de.stea1th.persist.repository;

import de.stea1th.persist.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface PersonRepository extends JpaRepository<Person, Integer> {

    default Person get(int id) throws ClassNotFoundException {
        return this.findById(id).orElseThrow(() -> new ClassNotFoundException("No such person with id:" + id));
    }

}
