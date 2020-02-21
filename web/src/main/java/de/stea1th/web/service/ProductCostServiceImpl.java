package de.stea1th.web.service;

import de.stea1th.commonslibrary.component.KafkaProducer;
import de.stea1th.commonslibrary.dto.ProductCostInCartDto;
import de.stea1th.web.kafka.ProductCostKafkaConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProductCostServiceImpl implements ProductCostService {

    private final KafkaProducer kafkaProducer;

    private final ProductCostKafkaConsumer productCostKafkaConsumer;

    @Value("#{new Integer('${kafka.service.attempt}')}")
    private Integer attempt;

    @Value("#{new Integer('${kafka.service.delay}')}")
    private Integer delay;

    @Value("${product-cost.get.cart}")
    private String productCostGetCartTopic;

    public ProductCostServiceImpl(KafkaProducer kafkaProducer, ProductCostKafkaConsumer productCostKafkaConsumer) {
        this.kafkaProducer = kafkaProducer;
        this.productCostKafkaConsumer = productCostKafkaConsumer;
    }

    public List<ProductCostInCartDto> getProductCostsInCart(String keycloak) {
        kafkaProducer.produce(productCostGetCartTopic, "pizza-online", keycloak);
        return null;
    }
}
