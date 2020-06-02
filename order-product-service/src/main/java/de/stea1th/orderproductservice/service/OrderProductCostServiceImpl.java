package de.stea1th.orderproductservice.service;


import de.stea1th.orderproductservice.dto.OrderProductCostDto;
import de.stea1th.orderproductservice.dto.ProductCostDto;
import de.stea1th.orderproductservice.entity.OrderProductCost;
import de.stea1th.orderproductservice.entity.OrderProductCostPK;
import de.stea1th.orderproductservice.kafka.producer.OrderKafkaProducer;
import de.stea1th.orderproductservice.kafka.producer.ProductKafkaProducer;
import de.stea1th.orderproductservice.repository.OrderProductCostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
public class OrderProductCostServiceImpl implements OrderProductCostService {

    private final OrderProductCostRepository orderProductCostRepository;

    private final OrderKafkaProducer orderKafkaProducer;

    private final ProductKafkaProducer productKafkaProducer;


    public OrderProductCostServiceImpl(OrderProductCostRepository orderProductCostRepository, OrderKafkaProducer orderKafkaProducer, ProductKafkaProducer productKafkaProducer) {
        this.orderProductCostRepository = orderProductCostRepository;
        this.orderKafkaProducer = orderKafkaProducer;
        this.productKafkaProducer = productKafkaProducer;
    }

    @Transactional
    public OrderProductCost save(OrderProductCostDto orderProductCostDto, int orderId) {
        OrderProductCost orderProductCost = get(orderId, orderProductCostDto.getProductCostId());
        ProductCostDto productCostDto = productKafkaProducer.getProductCostDtoById(orderProductCostDto.getProductCostId());
        if (orderProductCost == null) {
            log.info("new order-product-cost created");
            orderProductCost = new OrderProductCost();
            orderProductCost.setId(createPK(orderId, orderProductCostDto.getProductCostId()));
            orderProductCost.setQuantity(orderProductCostDto.getQuantity());
        } else {
            log.info("existing order-product-cost updated");
            orderProductCost.setQuantity(orderProductCost.getQuantity() + orderProductCostDto.getQuantity());
        }
        orderProductCost.setPrice(productCostDto.getPrice());
        orderProductCost.setDiscount(productCostDto.getDiscount());
        log.info("order-product-cost: {} saved", orderProductCost);
        return orderProductCostRepository.save(orderProductCost);
    }


    public int getQuantityByOrderProductCostId(int orderId, int productCostId) {
        return orderProductCostRepository.findQuantityById(createPK(orderId, productCostId));
    }

    public Integer updateQuantityAndPriceWithDiscount(OrderProductCostDto orderProductCostDto) {
        int orderId = orderKafkaProducer.getOrderIdByKeycloak(orderProductCostDto.getKeycloak());
        OrderProductCost orderProductCost = get(orderId, orderProductCostDto.getProductCostId());
        ProductCostDto productCostDto = productKafkaProducer.getProductCostDtoById(orderProductCostDto.getProductCostId());

        if (orderProductCost != null) {
            log.info("in order-product-cost product quantity updated");
            orderProductCost.setQuantity(orderProductCostDto.getQuantity());
            orderProductCost.setPrice(productCostDto.getPrice());
            orderProductCost.setDiscount(productCostDto.getDiscount());
            orderProductCostRepository.save(orderProductCost);
        }
        return getSumQuantitiesByOrderId(orderId);
    }

    public Integer deleteFromCart(OrderProductCostDto orderProductCostDto) {
        log.info("delete from order-product-cost product");
        int orderId = orderKafkaProducer.getOrderIdByKeycloak(orderProductCostDto.getKeycloak());
        orderProductCostRepository.deleteById(createPK(orderId, orderProductCostDto.getProductCostId()));
        return getSumQuantitiesByOrderId(orderId);
    }

    public Integer addToCart(OrderProductCostDto orderProductCostDto) {
        int orderId = orderKafkaProducer.getOrderIdByKeycloak(orderProductCostDto.getKeycloak());
        save(orderProductCostDto, orderId);
        return getSumQuantitiesByOrderId(orderId);
    }

    public Integer getQuantitiesSum(String keycloak) {
        int orderId = orderKafkaProducer.getOrderIdByKeycloak(keycloak);
        return getSumQuantitiesByOrderId(orderId);
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

    public List<OrderProductCost> getAllOrderProductCostsByOrderId(int orderId) {
        return orderProductCostRepository.findAllByOrderId(orderId);
    }
}
