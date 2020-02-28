package de.stea1th.persist.service.impl;

import de.stea1th.commonslibrary.dto.OrderProductCostDto;
import de.stea1th.persist.entity.Order;
import de.stea1th.persist.entity.OrderProductCost;
import de.stea1th.persist.entity.OrderProductCostPK;
import de.stea1th.persist.repository.OrderProductCostRepository;
import de.stea1th.persist.service.OrderProductCostService;
import de.stea1th.persist.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderProductCostServiceImpl implements OrderProductCostService {

    private final OrderProductCostRepository orderProductCostRepository;

    private final OrderService orderService;

    public OrderProductCostServiceImpl(OrderProductCostRepository orderProductCostRepository, OrderService orderService) {
        this.orderProductCostRepository = orderProductCostRepository;
        this.orderService = orderService;
    }

    @Override
    public OrderProductCost save(OrderProductCostDto orderProductCostDto, Order order) {
        OrderProductCost orderProductCost = get(order.getId(), orderProductCostDto.getProductCostId());
        if (orderProductCost == null) {
            log.info("new order-product-cost created");
            orderProductCost = new OrderProductCost();
            orderProductCost.setId(createPK(order.getId(), orderProductCostDto.getProductCostId()));
            orderProductCost.setQuantity(orderProductCostDto.getQuantity());
        } else {
            log.info("existing order-product-cost updated");
            orderProductCost.setQuantity(orderProductCost.getQuantity() + orderProductCostDto.getQuantity());
        }
        log.info("order-product-cost: {} saved", orderProductCost);
        return orderProductCostRepository.save(orderProductCost);
    }

    @Override
    public int getQuantityByOrderProductCostId(int orderId, int productCostId) {
        var orderProductCostId = new OrderProductCostPK();
        orderProductCostId.setOrderId(orderId);
        orderProductCostId.setProductCostId(productCostId);
        return orderProductCostRepository.findQuantityById(orderProductCostId);
    }

    @Override
    public Integer updateQuantity(OrderProductCostDto orderProductCostDto) {
        Order order = orderService.getUncompletedOrderByPersonKeycloak("\"" + orderProductCostDto.getKeycloak() + "\"");
        OrderProductCost orderProductCost = get(order.getId(), orderProductCostDto.getProductCostId());

        if (orderProductCost != null) {
            log.info("in order-product-cost product quantity updated");
            orderProductCost.setQuantity(orderProductCostDto.getQuantity());
            orderProductCostRepository.save(orderProductCost);
        }
        return getSumQuantitiesByOrderId(order.getId());
    }

    @Override
    public Integer deleteFromCart(OrderProductCostDto orderProductCostDto) {
        log.info("delete from order-product-cost product");
        var order = orderService.getUncompletedOrderByPersonKeycloak("\"" + orderProductCostDto.getKeycloak() + "\"");
        orderProductCostRepository.deleteById(createPK(order.getId(), orderProductCostDto.getProductCostId()));
        return getSumQuantitiesByOrderId(order.getId());
    }

    @Override
    public Integer addToCart(OrderProductCostDto orderProductCostDto) {
        Order order = orderService.getUncompletedOrderByPersonKeycloak("\"" + orderProductCostDto.getKeycloak() + "\"");
        save(orderProductCostDto, order);
        return getSumQuantitiesByOrderId(order.getId());
    }

    @Override
    public Integer getQuantitiesSum(String keycloak) {
        Order order = orderService.getUncompletedOrderByPersonKeycloak(keycloak);
        return getSumQuantitiesByOrderId(order.getId());
    }

    private Integer getSumQuantitiesByOrderId(int orderId) {
        Integer sum = orderProductCostRepository.findSumQuantitiesByOrderId(orderId);
        return sum == null ? 0 : sum;
    }

    private OrderProductCost get(int orderId, int productCostId) {
        OrderProductCostPK orderProductCostPK = createPK(orderId, productCostId);
        return orderProductCostRepository.findById(orderProductCostPK).orElse(null);
    }

    private OrderProductCostPK createPK(int orderId, int productCostId) {
        OrderProductCostPK orderProductCostPK = new OrderProductCostPK();
        orderProductCostPK.setOrderId(orderId);
        orderProductCostPK.setProductCostId(productCostId);
        return orderProductCostPK;
    }
}
