package de.stea1th.persist.repository;

import de.stea1th.persist.entity.Order;
import de.stea1th.persist.entity.Person;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByPersonAndCompletedOrderByCreatedAsc(Person person, LocalDateTime completed);

    List<Order> findByPersonAndCompletedBeforeOrderByCreatedAsc(Person person, LocalDateTime completed);

    @Query("SELECT DISTINCT(SUBSTRING(CONCAT(o.completed, ''), 1, 4)) AS years " +
            "FROM Order o " +
            "WHERE o.person = :person AND o.completed <> null  " +
            "ORDER BY years DESC ")
    List<Integer> findCompletedYearsByPerson(@Param("person") Person person);


}
