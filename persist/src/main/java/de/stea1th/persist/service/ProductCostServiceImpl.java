package de.stea1th.persist.service;


import de.stea1th.commonslibrary.dto.ProductCostInCartDto;
import de.stea1th.commonslibrary.dto.ProductDto;
import de.stea1th.persist.entity.Order;
import de.stea1th.persist.entity.Product;
import de.stea1th.persist.entity.ProductCost;
import de.stea1th.persist.repository.ProductCostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductCostServiceImpl implements ProductCostService {

    private final OrderService orderService;

    private OrderProductCostService orderProductCostService;

    private final ProductCostRepository productCostRepository;

    public ProductCostServiceImpl(OrderService orderService, OrderProductCostService orderProductCostService, ProductCostRepository productCostRepository) {
        this.orderService = orderService;
        this.orderProductCostService = orderProductCostService;
        this.productCostRepository = productCostRepository;
    }

    @Override
    public List<ProductCostInCartDto> getAllProductCostsByKeycloak(String keycloak) {
        Order order = orderService.getUncompletedOrderByPersonKeycloak(keycloak);
        log.info("get product-costs for keycloak: {}", keycloak);
        int orderId = order.getId();
        List<ProductCost> allByOrderId = productCostRepository.getAllByOrderId(orderId);
        return allByOrderId.stream().map(x-> transform(x, orderId)).collect(Collectors.toList());
    }

    private ProductCostInCartDto transform(ProductCost productCost, int orderId) {
        int quantity = orderProductCostService.getQuantityByOrderProductCostId(orderId, productCost.getId());
        ProductCostInCartDto productCostDto = new ProductCostInCartDto();
        ProductDto productDto = new ProductDto();
        Product product = productCost.getProduct();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPicture(product.getPicture());
        productCostDto.setProduct(productDto);
        productCostDto.setId(productCost.getId());
        productCostDto.setProperty(productCost.getProperty());
        productCostDto.setDiscount(productCost.getDiscount());
        productCostDto.setPrice(productCost.getPrice());
        productCostDto.setQuantity(quantity);
        return productCostDto;
    }



}
