package de.stea1th.orderproductservice.service;


import de.stea1th.orderproductservice.converter.CartElementConverter;
import de.stea1th.orderproductservice.dto.CartElementDto;
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
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderProductCostServiceImpl implements OrderProductCostService {

    private final OrderProductCostRepository orderProductCostRepository;

    private final OrderKafkaProducer orderKafkaProducer;

    private final ProductKafkaProducer productKafkaProducer;

    private final CartElementConverter cartElementConverter;


    public OrderProductCostServiceImpl(OrderProductCostRepository orderProductCostRepository, OrderKafkaProducer orderKafkaProducer, ProductKafkaProducer productKafkaProducer, CartElementConverter cartElementConverter) {
        this.orderProductCostRepository = orderProductCostRepository;
        this.orderKafkaProducer = orderKafkaProducer;
        this.productKafkaProducer = productKafkaProducer;
        this.cartElementConverter = cartElementConverter;
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
        log.info("Delete from cart product-cost with id: {}", orderProductCostDto.getProductCostId());
        int orderId = orderKafkaProducer.getOrderIdByKeycloak(orderProductCostDto.getKeycloak());
        orderProductCostRepository.deleteById(createPK(orderId, orderProductCostDto.getProductCostId()));
        return getSumQuantitiesByOrderId(orderId);
    }

    public Integer addToCart(OrderProductCostDto orderProductCostDto) {
        log.info("Adding to cart product-cost with id: {}", orderProductCostDto.getProductCostId());
        int orderId = orderKafkaProducer.getOrderIdByKeycloak(orderProductCostDto.getKeycloak());
        save(orderProductCostDto, orderId);
        return getSumQuantitiesByOrderId(orderId);
    }

    public Integer getQuantitiesSum(String keycloak) {
        log.info("Get summary all products in cart for keycloak: {}", keycloak);
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

    private Map<Integer, OrderProductCost> getAllOrderProductCostsAsMapByOrderId(int orderId) {
        return getAllOrderProductCostsByOrderId(orderId)
                .stream()
                .collect(Collectors.toMap(x -> x.getId().getProductCostId(), x -> x));
    }

    public List<ProductCostDto> getProductCostListByOrderId(int orderId) {
        List<Integer> ids = orderProductCostRepository.findAllProductCostIdsByOrderId(orderId);
        return productKafkaProducer.getProductCostListByIds(ids);
    }

    public List<CartElementDto> createCart(int orderId) {
        log.info("Aggregating cart for order with id: {}", orderId);
        Map<Integer, OrderProductCost> orderProductCostMap = getAllOrderProductCostsAsMapByOrderId(orderId);
        List<ProductCostDto> productCostListByOrderId = getProductCostListByOrderId(orderId);
        return aggregateCartElements(orderProductCostMap, productCostListByOrderId);
    }

    private List<CartElementDto> aggregateCartElements(Map<Integer, OrderProductCost> map, List<ProductCostDto> list) {
        return list
                .stream()
                .map(productCost -> {
                    OrderProductCost orderProductCost = map.get(productCost.getId());
                    return cartElementConverter.convertToDto(orderProductCost, productCost);
                })
                .collect(Collectors.toList());
    }
}
