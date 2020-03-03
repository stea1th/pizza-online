package de.stea1th.persist.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.stea1th.commonslibrary.component.KafkaProducer;
import de.stea1th.commonslibrary.dto.ProductCostInCartDto;
import de.stea1th.persist.service.ProductCostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class ProductCostKafkaConsumer {

    private final KafkaProducer kafkaProducer;

    private final ProductCostService productCostService;

    @Value("${product-cost.receive.cart}")
    private String receiveCartProductCostsTopic;


    public ProductCostKafkaConsumer(KafkaProducer kafkaProducer, ProductCostService productCostService) {
        this.kafkaProducer = kafkaProducer;
        this.productCostService = productCostService;
    }

    @KafkaListener(topics = "${product-cost.get.cart}", groupId = "pizza-online")
    public void processGetAllProductCosts(String message) {
//        ObjectMapper objectMapper = new ObjectMapper();
        List<ProductCostInCartDto> allProductCostsByKeycloak = productCostService.getAllProductCostsByKeycloak(message);
        kafkaProducer.produce(receiveCartProductCostsTopic, "pizza-online", allProductCostsByKeycloak);
//        try {
//            log.info("product-costs data: {} sent to topic {}", objectMapper.writeValueAsString(allProductCostsByKeycloak), receiveCartProductCostsTopic);
//        } catch (JsonProcessingException e) {
//            log.error(e.getMessage());
//        }
    }
}
