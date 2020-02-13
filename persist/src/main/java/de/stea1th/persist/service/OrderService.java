package de.stea1th.persist.service;

import de.stea1th.persist.entity.Order;

public interface OrderService {

    Order getUncompletedOrderByPersonKeycloak(String keycloak);

}
