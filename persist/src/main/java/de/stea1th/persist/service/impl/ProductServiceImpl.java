package de.stea1th.persist.service.impl;

import de.stea1th.commonslibrary.dto.ProductDto;
import de.stea1th.persist.converter.ProductConverter;
import de.stea1th.persist.entity.Order;
import de.stea1th.persist.repository.ProductRepository;
import de.stea1th.persist.service.OrderService;
import de.stea1th.persist.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final OrderService orderService;

    private final ProductConverter productConverter;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, OrderService orderService, ProductConverter productConverter) {
        this.productRepository = productRepository;
        this.orderService = orderService;
        this.productConverter = productConverter;
    }

    @Override
    public List<ProductDto> getAll(boolean withFrozen) {
        log.info("???????????????? {}", withFrozen);
        log.info(withFrozen? "get all products" : "get all products without frozen");
        return productConverter.convertListToDto(withFrozen? productRepository.findAll() : productRepository.getAllWithoutFrozen(), withFrozen);
    }


    @Override
    public List<ProductDto> getAllProductsByKeycloak(String keycloak) {
        Order order = orderService.getUncompletedOrderByPersonKeycloak(keycloak);
        log.info("get products for keycloak: {}", keycloak);
        return productRepository
                .getAllByOrderId(order.getId())
                .stream()
                .map(x -> productConverter.convertToDto(x, false))
                .collect(Collectors.toList());
    }
}
