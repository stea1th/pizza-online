package de.stea1th.persist.service;

import de.stea1th.commonslibrary.exception.MyEntityNotFoundException;
import de.stea1th.persist.entity.Order;
import de.stea1th.persist.entity.Product;
import de.stea1th.persist.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    private OrderService orderService;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, OrderService orderService) {
        this.productRepository = productRepository;
        this.orderService = orderService;
    }

    @Override
    public List<Product> getAll() {
        log.info("get all products");
        return productRepository.findAll();
    }

    @Override
    public Product get(int productId) {
        Product product = null;
        try {
            product = productRepository.get(productId);
        } catch (MyEntityNotFoundException e) {
            log.error(e.getMessage());
        }
        log.info("get product with id: {}", productId);
        return product;
    }

    @Override
    public List<Product> getAllProductsByKeycloak(String keycloak) {
        Order order = orderService.getUncompletedOrderByPersonKeycloak(keycloak);
        log.info("get products for keycloak: {}", keycloak);
        return productRepository.getAllByOrderId(order.getId());
    }
}
