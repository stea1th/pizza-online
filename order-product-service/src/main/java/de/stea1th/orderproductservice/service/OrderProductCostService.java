package de.stea1th.orderproductservice.service;

import de.stea1th.orderproductservice.dto.CartElementDto;
import de.stea1th.orderproductservice.dto.OrderProductCostDto;
import de.stea1th.orderproductservice.dto.ProductCostDto;
import de.stea1th.orderproductservice.entity.OrderProductCost;

import java.util.List;

public interface OrderProductCostService {

    OrderProductCost save(OrderProductCostDto orderProductCostDto, int orderId);

    Integer getQuantitiesSum(String keycloak);

    Integer addToCart(OrderProductCostDto orderProductCostDto);

    int getQuantityByOrderProductCostId(int orderId, int productCostId);

    Integer updateQuantityAndPriceWithDiscount(OrderProductCostDto orderProductCostDto);

    Integer deleteFromCart(OrderProductCostDto orderProductCostDto);

    List<OrderProductCost> getAllOrderProductCostsByOrderId(int orderId);

    OrderProductCost get(int orderId, int productCostId);

    List<ProductCostDto> getProductCostListByOrderId(int orderId);

    List<CartElementDto> createCart(int orderId);
}
