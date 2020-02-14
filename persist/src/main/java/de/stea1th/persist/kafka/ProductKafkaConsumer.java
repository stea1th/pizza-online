package de.stea1th.persist.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.stea1th.commonslibrary.component.KafkaProducer;
import de.stea1th.persist.entity.Product;
import de.stea1th.persist.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class ProductKafkaConsumer {

    private KafkaProducer kafkaProducer;

    private ProductService productService;

    private Product product;

    @Value("${product.receive.product.all}")
    private String receiveAllProductsTopic;

    @Value("${product.receive.product.cart}")
    private String receiveCartProductsTopic;


    @Autowired
    public ProductKafkaConsumer(KafkaProducer kafkaProducer, ProductService productService) {
        this.kafkaProducer = kafkaProducer;
        this.productService = productService;
    }

    @KafkaListener(topics = "${product.get.all}", groupId = "pizza-online")
    public void processGetAllProducts() {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Product> products = productService.getAll();
        kafkaProducer.produce(receiveAllProductsTopic, "pizza-online", products);
        try {
            log.info("products data: {} sent to topic {}", objectMapper.writeValueAsString(products), receiveAllProductsTopic);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
    }

    @KafkaListener(topics = "${product.get.cart}", groupId = "pizza-online")
    public void processGetAllProducts(String message) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Product> products = productService.getAllProductsByKeycloak(message);
        kafkaProducer.produce(receiveCartProductsTopic, "pizza-online", products);
        try {
            log.info("products data: {} sent to topic {}", objectMapper.writeValueAsString(products), receiveCartProductsTopic);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
    }
}
