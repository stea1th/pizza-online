package de.stea1th.orderproductservice.service;

import de.stea1th.commonslibrary.dto.OrderProductCostDto;

import de.stea1th.orderproductservice.entity.OrderProductCost;
import de.stea1th.orderproductservice.entity.OrderProductCostPK;
import de.stea1th.orderproductservice.repository.OrderProductCostRepository;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Slf4j
public class OrderProductCostServiceImpl implements OrderProductCostService {

    private final OrderProductCostRepository orderProductCostRepository;

    private final OrderService orderService;

    private ProductCostService productCostService;

    public OrderProductCostServiceImpl(OrderProductCostRepository orderProductCostRepository, @Lazy OrderService orderService, @Lazy ProductCostService productCostService) {
        this.orderProductCostRepository = orderProductCostRepository;
        this.orderService = orderService;
        this.productCostService = productCostService;
    }

    @Override
    @Transactional
    public OrderProductCost save(OrderProductCostDto orderProductCostDto, Order order) {
        OrderProductCost orderProductCost = get(order.getId(), orderProductCostDto.getProductCostId());
        var productCost = productCostService.get(orderProductCostDto.getProductCostId());
        if (orderProductCost == null) {
            log.info("new order-product-cost created");
            orderProductCost = new OrderProductCost();
            orderProductCost.setId(createPK(order.getId(), orderProductCostDto.getProductCostId()));
            orderProductCost.setQuantity(orderProductCostDto.getQuantity());
        } else {
            log.info("existing order-product-cost updated");
            orderProductCost.setQuantity(orderProductCost.getQuantity() + orderProductCostDto.getQuantity());
        }
        orderProductCost.setPrice(productCost.getPrice());
        orderProductCost.setDiscount(productCost.getDiscount());
        log.info("order-product-cost: {} saved", orderProductCost);
        return orderProductCostRepository.save(orderProductCost);
    }

    @Override
    public int getQuantityByOrderProductCostId(int orderId, int productCostId) {
        return orderProductCostRepository.findQuantityById(createPK(orderId, productCostId));
    }

    @Override
    public Integer updateQuantityAndPriceWithDiscount(OrderProductCostDto orderProductCostDto) {
        Order order = orderService.getUncompletedOrderByPersonKeycloak(orderProductCostDto.getKeycloak());
        OrderProductCost orderProductCost = get(order.getId(), orderProductCostDto.getProductCostId());
        var productCost = productCostService.get(orderProductCostDto.getProductCostId());

        if (orderProductCost != null) {
            log.info("in order-product-cost product quantity updated");
            orderProductCost.setQuantity(orderProductCostDto.getQuantity());
            orderProductCost.setPrice(productCost.getPrice());
            orderProductCost.setDiscount(productCost.getDiscount());
            orderProductCostRepository.save(orderProductCost);
        }
        return getSumQuantitiesByOrderId(order.getId());
    }

    @Override
    public Integer deleteFromCart(OrderProductCostDto orderProductCostDto) {
        log.info("delete from order-product-cost product");
        var order = orderService.getUncompletedOrderByPersonKeycloak(orderProductCostDto.getKeycloak());
        orderProductCostRepository.deleteById(createPK(order.getId(), orderProductCostDto.getProductCostId()));
        return getSumQuantitiesByOrderId(order.getId());
    }

    @Override
    public Integer addToCart(OrderProductCostDto orderProductCostDto) {
        Order order = orderService.getUncompletedOrderByPersonKeycloak(orderProductCostDto.getKeycloak());
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

    @Transactional
    public OrderProductCost get(int orderId, int productCostId) {
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
