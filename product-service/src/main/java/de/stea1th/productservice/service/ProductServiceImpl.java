package de.stea1th.productservice.service;

import de.stea1th.productservice.converter.ImageConverter;
import de.stea1th.productservice.entity.Product;
import de.stea1th.productservice.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ImageConverter imageConverter;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ImageConverter imageConverter) {
        this.productRepository = productRepository;
        this.imageConverter = imageConverter;
    }

    @Override
    public List<Product> getAll(boolean withFrozen) {
        log.info(withFrozen ? "get all products" : "get all products without frozen");
        return withFrozen ?
                productRepository
                        .findAll()
                        .stream()
                        .map(this::attachPic)
                        .collect(Collectors.toList()) :
                productRepository.getAllWithoutFrozen()
                        .stream()
                        .map(this::attachPic)
                        .collect(Collectors.toList());
    }

    public Product get(int id) {
        log.info("get product with id: {}", id);
        return attachPic(Objects.requireNonNull(productRepository.findById(id).orElse(null)));
    }

    public Product attachPic(Product product) {
        try {
            String base64 = imageConverter.encodeFileFromResourcesToBase64(product.getPicture());
            product.setPicture(base64);
        } catch (IOException e) {
            log.info("no picture for product with id {} available", product.getId());
        }
        return product;
    }


//    @Override
//    public List<Product> getAllProductsByKeycloak(String keycloak) {
////        Order order = orderService.getUncompletedOrderByPersonKeycloak(keycloak);
//        log.info("get products for keycloak: {}", keycloak);
//        return productRepository
//                .getAllByOrderId(order.getId())
//                .stream()
//                .map(x -> productConverter.convertToDto(x, false))
//                .collect(Collectors.toList());
//    }
}
