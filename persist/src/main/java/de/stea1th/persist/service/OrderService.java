package de.stea1th.persist.service;

import de.stea1th.persist.entity.Order;
import de.stea1th.persist.entity.Person;

public interface OrderService {

    Order getUncompletedOrderByPersonKeycloak(String keycloak);

    Order getUncompletedOrderByPerson(Person person);

    Order completeOrder(String keycloak);

}
