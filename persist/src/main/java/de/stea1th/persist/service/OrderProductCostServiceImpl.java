package de.stea1th.persist.service;

import de.stea1th.commonslibrary.dto.OrderProductCostDto;
import de.stea1th.persist.entity.Order;
import de.stea1th.persist.entity.OrderProductCost;
import de.stea1th.persist.entity.OrderProductCostPK;
import de.stea1th.persist.repository.OrderProductCostRepository;
import lombok.extern.slf4j.Slf4j;
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
    public OrderProductCost save(OrderProductCostDto orderProductCostDto) {
        Order order = orderService.getUncompletedOrderByPersonKeycloak("\"" + orderProductCostDto.getKeycloak() + "\"");
        OrderProductCostPK orderProductCostPK = new OrderProductCostPK();
        orderProductCostPK.setOrderId(order.getId());
        orderProductCostPK.setProductCostId(orderProductCostDto.getProductCostId());
        OrderProductCost orderProductCost = orderProductCostRepository.findById(orderProductCostPK).orElse(null);
        if(orderProductCost == null) {
            log.info("new order-product-cost created");
            orderProductCost = new OrderProductCost();
            orderProductCost.setId(orderProductCostPK);
            orderProductCost.setQuantity(orderProductCostDto.getQuantity());
        } else {
            log.info("existing order-product-cost updated");
            orderProductCost.setQuantity(orderProductCost.getQuantity() + orderProductCostDto.getQuantity());
        }
        log.info("order-product-cost: {} saved", orderProductCost);
        return orderProductCostRepository.save(orderProductCost);
//        return null;
    }

    @Override
    public OrderProductCost delete(OrderProductCostPK orderProductId) {
        return null;
    }
}
