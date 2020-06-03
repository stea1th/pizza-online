package de.stea1th.orderservice.service;

import de.stea1th.orderservice.entity.Order;

import java.util.List;
import java.util.Map;

public interface OrderService {

    Order getUncompletedOrderByPersonKeycloak(String keycloak);

    Map<String, String> getInterval(String keycloak);

//    Order getUncompletedOrderByPerson(Person person);

//    Order completeOrder(String keycloak);
//
//    List<Integer> getCompletedYearsByPerson(String keycloak);
//
//    List<CompletedOrderDto> getCompletedOrders(CompletedOrdersRequestDto completedOrdersRequestDto);

    Order complete(String keycloak);

}
