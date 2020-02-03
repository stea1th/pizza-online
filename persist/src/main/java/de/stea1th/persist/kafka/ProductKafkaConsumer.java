package de.stea1th.persist.kafka;

import de.stea1th.kafkalibrary.component.KafkaProducer;
import de.stea1th.persist.entity.Product;
import de.stea1th.persist.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ProductKafkaConsumer {

    private KafkaProducer kafkaProducer;

    private ProductService productService;

    private Product product;


    @Autowired
    public ProductKafkaConsumer(KafkaProducer kafkaProducer, ProductService productService) {
        this.kafkaProducer = kafkaProducer;
        this.productService = productService;
    }


}
