package de.stea1th.persist.service;


import de.stea1th.persist.entity.Order;
import de.stea1th.persist.entity.ProductCost;
import de.stea1th.persist.repository.ProductCostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProductCostServiceImpl implements ProductCostService {

    private final OrderService orderService;

    private final ProductCostRepository productCostRepository;

    public ProductCostServiceImpl(OrderService orderService, ProductCostRepository productCostRepository) {
        this.orderService = orderService;
        this.productCostRepository = productCostRepository;
    }

    @Override
    public List<ProductCost> getAllProductCostsByKeycloak(String keycloak) {
        Order order = orderService.getUncompletedOrderByPersonKeycloak(keycloak);
        log.info("get product-costs for keycloak: {}", keycloak);
        return productCostRepository.getAllByOrderId(order.getId());
    }

}
