package de.stea1th.persist.service.impl;


import de.stea1th.persist.entity.Order;
import de.stea1th.persist.entity.Person;
import de.stea1th.persist.repository.OrderRepository;
import de.stea1th.persist.service.OrderService;
import de.stea1th.persist.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private PersonService personService;

    private OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(PersonService personService, OrderRepository orderRepository) {
        this.personService = personService;
        this.orderRepository = orderRepository;
    }

    @Override
    public Order getUncompletedOrderByPersonKeycloak(String keycloak) {
        Person person = personService.getByKeycloak(keycloak);
        return getUncompletedOrderByPerson(person);
    }

    @Override
    public Order getUncompletedOrderByPerson(Person person) {
        List<Order> orders = orderRepository.findByPersonAndCompletedOrderByCreatedAsc(person, null);
        return orders.isEmpty() ? createEmptyOrder(person) : getUncompletedOrder(orders);
    }

    @Override
    @Transactional
    public Order completeOrder(String keycloak) {
        Person person = personService.getByKeycloak(keycloak);
        Order order = getUncompletedOrderByPerson(person);
        if (!order.getOrderProductCosts().isEmpty()) {
            order.setCompleted(LocalDateTime.now());
            order = orderRepository.save(order);
            createEmptyOrder(person);
        }
        return order;
    }

    private Order createEmptyOrder(Person person) {
        Order order = new Order();
        order.setPerson(person);
        order = orderRepository.save(order);
        log.info("new uncompleted order with id: {} created", order.getId());
        return order;
    }

    private Order getUncompletedOrder(List<Order> orders) {
        Order order = orders.get(0);
        log.info("existing uncompleted order with id: {}", order.getId());
        return order;
    }
}
