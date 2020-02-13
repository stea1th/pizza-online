package de.stea1th.persist.service;


import de.stea1th.persist.entity.Order;
import de.stea1th.persist.entity.Person;
import de.stea1th.persist.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Order getUncompletedOrderByPersonKeycloak(String keycloak) {
        Person person = personService.getByKeycloak(keycloak);
        List<Order> orders = orderRepository.findByPersonAndCompletedOrderByCreatedAsc(person, false);
        Order order;
        if (orders.isEmpty()) {
            order = new Order();
            order.setPerson(person);
            order = orderRepository.save(order);
            log.info("new uncompleted order with id: {} created", order.getId());
        } else {
            order = orders.get(0);
            log.info("existing uncompleted order with id: {}", order.getId());
        }
        return order;
    }
}
