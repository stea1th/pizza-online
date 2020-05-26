package de.stea1th.orderservice.repository;

import de.stea1th.orderservice.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByPersonIdAndCompletedOrderByCreatedAsc(int personId, LocalDateTime completed);

//    List<Order> findByPersonIdAndCompletedAfterOrderByCompletedDesc(int personId, LocalDateTime completed);

    @Query("SELECT o FROM Order o WHERE o.personId = :personId AND o.completed IS NULL ")
    List<Order> findByPersonIdAndCompletedIsNull(@Param("personId")int personId);


    @Query("SELECT DISTINCT(SUBSTRING(CONCAT(o.completed, ''), 1, 4)) AS years " +
            "FROM Order o " +
            "WHERE o.personId = :personId AND o.completed <> null  " +
            "ORDER BY years DESC ")
    List<Integer> findCompletedYearsByPersonId(@Param("personId") int personId);

    @Query("SELECT o " +
            "FROM Order o " +
            "WHERE o.personId = :personId AND SUBSTRING(CONCAT(o.completed, ''), 1, 4) = :year " +
            "ORDER BY o.completed DESC")
    List<Order> findByPersonAndCompletedYear(@Param("personId") int personId, @Param("year") String year);
}
