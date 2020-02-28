package de.stea1th.web.kafka.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.stea1th.commonslibrary.dto.ProductDto;
import de.stea1th.web.kafka.ProductKafkaConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@Slf4j
public class ProductKafkaConsumerImpl implements ProductKafkaConsumer {

    private List<ProductDto> allProducts;

    private List<ProductDto> productsInCart;

    public List<ProductDto> getAllProducts() {
        List<ProductDto> tempProducts = allProducts;
        allProducts = null;
        return tempProducts;
    }

    public List<ProductDto> getProductsInCart() {
        List<ProductDto> tempProducts = productsInCart;
        productsInCart = null;
        return tempProducts;
    }

    @KafkaListener(topics = "${product.receive.product.all}", groupId = "pizza-online")
    public void processGetAllProducts(String products) {
        log.info("received all products = {}", products);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            allProducts = objectMapper.readValue(products, new TypeReference<List<ProductDto>>() {
            });
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    @KafkaListener(topics = "${product.receive.product.cart}", groupId = "pizza-online")
    public void processGetCartProducts(String products) {
        log.info("received products in cart = {}", products);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            productsInCart = objectMapper.readValue(products, new TypeReference<List<ProductDto>>() {
            });
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
