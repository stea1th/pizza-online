package de.stea1th.web.kafka.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.stea1th.commonslibrary.dto.ProductCostInCartDto;
import de.stea1th.web.kafka.ProductCostKafkaConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@Slf4j
public class ProductCostKafkaConsumerImpl implements ProductCostKafkaConsumer {


    private List<ProductCostInCartDto> productCostInCartDtoList;

    @Override
    public List<ProductCostInCartDto> getProductCostInCartDtoList() {
        List<ProductCostInCartDto> tempProductCosts = productCostInCartDtoList;
        productCostInCartDtoList = null;
        return tempProductCosts;
    }

    @KafkaListener(topics = "${product-cost.receive.cart}", groupId = "pizza-online")
    public void processGetCartProductCosts(String products) {
        log.info("received product-costs in cart = {}", products);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            productCostInCartDtoList = objectMapper.readValue(products, new TypeReference<List<ProductCostInCartDto>>() {
            });
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

}
