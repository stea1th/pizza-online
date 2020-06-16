package de.stea1th.productservice.kafka.consumer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.stea1th.productservice.dto.ProductCostDto;
import de.stea1th.productservice.entity.ProductCost;
import de.stea1th.productservice.service.ProductCostService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class ReadKafkaConsumer {

    private final ObjectMapper objectMapper;

    private final ProductCostService productCostService;

    public ReadKafkaConsumer(ProductCostService productCostService) {
        this.productCostService = productCostService;
        this.objectMapper = new ObjectMapper();
        objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
    }

    @SneakyThrows
    @KafkaListener(topics = "${product-cost.get.all.by.ids}")
    @SendTo
    public String getProductCostByIds(String message) {
        log.info("Receiving message: {}", message);

        List<Integer> ids = objectMapper.readValue(message, new TypeReference<List<Integer>>() {
        });
        List<ProductCostDto> allByIds = productCostService.getAllByIds(ids);
        return objectMapper.writeValueAsString(allByIds);
    }

    @SneakyThrows
    @KafkaListener(topics = "${product-cost.get}")
    @SendTo
    public String getProductCostById(String message) {
        log.info("Receiving message: {}", message);
        ProductCostDto productCostDto = productCostService.get(Integer.parseInt(message));
        return objectMapper.writeValueAsString(productCostDto);
    }
}
