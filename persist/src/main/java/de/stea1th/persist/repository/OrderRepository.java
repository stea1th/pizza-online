package de.stea1th.persist.repository;

import de.stea1th.persist.entity.Order;
import de.stea1th.persist.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByPersonAndCompletedOrderByCreatedAsc(Person person, LocalDateTime completed);

}
