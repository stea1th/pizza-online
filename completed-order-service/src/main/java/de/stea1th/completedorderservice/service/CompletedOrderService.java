package de.stea1th.completedorderservice.service;

import de.stea1th.completedorderservice.dto.CompletedOrderDto;

import java.util.List;

public interface CompletedOrderService {

    List<CompletedOrderDto> getCompletedOrders(String keycloak, String value);
}
