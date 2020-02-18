package de.stea1th.persist.service;

import de.stea1th.commonslibrary.dto.OrderProductCostDto;
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
//        Order order = orderService.getUncompletedOrderByPersonKeycloak("\"" + orderProductDto.getKeycloak() + "\"");
//        OrderProductPK orderProductPK = new OrderProductPK();
//        orderProductPK.setOrderId(order.getId());
//        orderProductPK.setProductId(orderProductDto.getProductId());
//        OrderProduct orderProduct = orderProductRepository.findById(orderProductPK).orElse(null);
//        if(orderProduct == null) {
//            log.info("new order-product created");
//            orderProduct = new OrderProduct();
//            orderProduct.setId(orderProductPK);
//            orderProduct.setQuantity(orderProductDto.getQuantity());
//        } else {
//            log.info("existing order-product updated");
//            orderProduct.setQuantity(orderProduct.getQuantity() + orderProductDto.getQuantity());
//        }
//        log.info("order-product: {} saved", orderProduct);
//        return orderProductRepository.save(orderProduct);
        return null;
    }

    @Override
    public OrderProductCost delete(OrderProductCostPK orderProductId) {
        return null;
    }
}
