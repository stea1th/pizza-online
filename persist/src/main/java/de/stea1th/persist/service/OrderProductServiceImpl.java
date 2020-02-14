package de.stea1th.persist.service;

import de.stea1th.commonslibrary.dto.OrderProductDto;
import de.stea1th.persist.entity.Order;
import de.stea1th.persist.entity.OrderProduct;
import de.stea1th.persist.entity.OrderProductPK;
import de.stea1th.persist.repository.OrderProductRepository;
import de.stea1th.persist.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderProductServiceImpl implements OrderProductService {

    private final OrderProductRepository orderProductRepository;

    private final OrderService orderService;

    public OrderProductServiceImpl(OrderProductRepository orderProductRepository, OrderService orderService) {
        this.orderProductRepository = orderProductRepository;
        this.orderService = orderService;
    }

    @Override
    public OrderProduct save(OrderProductDto orderProductDto) {
        Order order = orderService.getUncompletedOrderByPersonKeycloak("\"" + orderProductDto.getKeycloak() + "\"");
        OrderProductPK orderProductPK = new OrderProductPK();
        orderProductPK.setOrderId(order.getId());
        orderProductPK.setProductId(orderProductDto.getProductId());
        OrderProduct orderProduct = orderProductRepository.findById(orderProductPK).orElse(null);
        if(orderProduct == null) {
            log.info("new order-product created");
            orderProduct = new OrderProduct();
            orderProduct.setId(orderProductPK);
            orderProduct.setQuantity(orderProductDto.getQuantity());
        } else {
            log.info("existing order-product updated");
            orderProduct.setQuantity(orderProduct.getQuantity() + orderProductDto.getQuantity());
        }
        log.info("order-product: {} saved", orderProduct);
        return orderProductRepository.save(orderProduct);
    }

    @Override
    public OrderProduct delete(OrderProductPK orderProductId) {
        return null;
    }
}
