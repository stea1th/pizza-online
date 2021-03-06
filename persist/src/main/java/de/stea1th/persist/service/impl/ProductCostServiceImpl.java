package de.stea1th.persist.service.impl;


import de.stea1th.commonslibrary.dto.ProductCostInCartDto;
import de.stea1th.persist.converter.ProductCostConverter;
import de.stea1th.persist.entity.Order;
import de.stea1th.persist.entity.ProductCost;
import de.stea1th.persist.repository.ProductCostRepository;
import de.stea1th.persist.service.OrderProductCostService;
import de.stea1th.persist.service.OrderService;
import de.stea1th.persist.service.ProductCostService;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductCostServiceImpl implements ProductCostService {

    private final OrderService orderService;

    private OrderProductCostService orderProductCostService;

    private final ProductCostRepository productCostRepository;

    private ProductCostConverter productCostConverter;

    public ProductCostServiceImpl(OrderService orderService, @Lazy OrderProductCostService orderProductCostService, ProductCostRepository productCostRepository, ProductCostConverter productCostConverter) {
        this.orderService = orderService;
        this.orderProductCostService = orderProductCostService;
        this.productCostRepository = productCostRepository;
        this.productCostConverter = productCostConverter;
    }

    @Override
    public List<ProductCostInCartDto> getAllProductCostsInCartByKeycloak(String keycloak) {
        Order order = orderService.getUncompletedOrderByPersonKeycloak(keycloak);
        log.info("get product-costs for keycloak: {}", keycloak);
        List<ProductCost> allByOrderId = productCostRepository.getAllByOrderId(order.getId());
        return allByOrderId.stream().map(productCost -> {
            var orderProductCost = orderProductCostService.get(order.getId(), productCost.getId());
            return productCostConverter.convertToDtoInCart(productCost, orderProductCost);
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ProductCost get(int productCostId) {
        return productCostRepository.findById(productCostId).get();
    }
}
