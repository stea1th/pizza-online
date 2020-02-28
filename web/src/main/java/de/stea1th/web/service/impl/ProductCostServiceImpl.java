package de.stea1th.web.service.impl;

import de.stea1th.commonslibrary.component.KafkaProducer;
import de.stea1th.commonslibrary.dto.ProductCostInCartDto;
import de.stea1th.web.kafka.ProductCostKafkaConsumer;
import de.stea1th.web.service.ProductCostService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

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

    @SneakyThrows
    @Override
    public List<ProductCostInCartDto> getProductCostsInCart(String keycloak) {
        kafkaProducer.produce(productCostGetCartTopic, "pizza-online", keycloak);
        List<ProductCostInCartDto> productCostInCartDtoList = null;
        for (int i = 0; i < attempt; i++) {
            TimeUnit.MILLISECONDS.sleep(delay);
            productCostInCartDtoList = productCostKafkaConsumer.getProductCostInCartDtoList();
            if (productCostInCartDtoList != null) break;
        }
        return productCostInCartDtoList;
    }
}
