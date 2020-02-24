package de.stea1th.persist.service;

import de.stea1th.commonslibrary.dto.OrderProductCostDto;
import de.stea1th.persist.entity.Order;
import de.stea1th.persist.entity.OrderProductCost;
import de.stea1th.persist.entity.OrderProductCostPK;

public interface OrderProductCostService {

//    OrderProductCost save(OrderProductCostDto orderProductCostDto);

    OrderProductCost save(OrderProductCostDto orderProductCostDto, Order order);

    Integer getQuantitiesSum(String keycloak);

    Integer addToCart(OrderProductCostDto orderProductCostDto);

    OrderProductCost delete(OrderProductCostPK orderProductId);

    int getQuantityByOrderProductCostId(int orderId, int productCostId);

}
