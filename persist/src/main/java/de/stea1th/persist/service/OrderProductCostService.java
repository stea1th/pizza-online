package de.stea1th.persist.service;

import de.stea1th.commonslibrary.dto.OrderProductCostDto;
import de.stea1th.persist.entity.Order;
import de.stea1th.persist.entity.OrderProductCost;

public interface OrderProductCostService {

    OrderProductCost save(OrderProductCostDto orderProductCostDto, Order order);

    Integer getQuantitiesSum(String keycloak);

    Integer addToCart(OrderProductCostDto orderProductCostDto);

    int getQuantityByOrderProductCostId(int orderId, int productCostId);

    Integer updateQuantity(OrderProductCostDto orderProductCostDto);

    Integer deleteFromCart(OrderProductCostDto orderProductCostDto);
}
