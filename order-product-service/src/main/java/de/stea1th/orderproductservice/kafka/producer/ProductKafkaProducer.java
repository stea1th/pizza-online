package de.stea1th.orderproductservice.kafka.producer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.stea1th.orderproductservice.dto.ProductCostDto;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.List;

@Component
@Slf4j
public class ProductKafkaProducer extends KafkaProducer{

    @Value("${product-cost.get}")
    private String getProductCostTopic;

    @Value("${product-cost.get.all.by.ids}")
    private String getProductCostListByIdsTopic;

    public ProductKafkaProducer(ReplyingKafkaTemplate<String, String, String> replyingKafkaTemplate, ObjectMapper objectMapper) {
        super(replyingKafkaTemplate, objectMapper);
    }

    @SneakyThrows
    public ProductCostDto getProductCostDtoById(int productCostId) {
        logMessage(productCostId, getProductCostTopic);
        String json =  produce(getProductCostTopic, productCostId);
        return objectMapper.readValue(json, ProductCostDto.class);
    }

    @SneakyThrows
    public List<ProductCostDto> getProductCostListByIds(List<Integer> ids) {
        logMessage(ids, getProductCostListByIdsTopic);
        String json = produce(getProductCostListByIdsTopic, ids);
        return objectMapper.readValue(json, new TypeReference<List<ProductCostDto>>() {
        });
    }

    private void logMessage(Object message, String topic) {
        log.info("Sending message {} to {} topic", message, topic);
    }
}
