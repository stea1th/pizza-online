package de.stea1th.persist.kafka;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.stea1th.commonslibrary.component.KafkaProducer;
import de.stea1th.commonslibrary.dto.OrderProductCostDto;
import de.stea1th.persist.service.OrderProductCostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderProductCostKafkaConsumer {

    private final OrderProductCostService orderProductCostService;

    private final KafkaProducer kafkaProducer;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Value("${order-product-cost.receive.sum}")
    private String receiveSumTopic;

    @Autowired
    public OrderProductCostKafkaConsumer(OrderProductCostService orderProductCostService, KafkaProducer kafkaProducer) {
        this.orderProductCostService = orderProductCostService;
        this.kafkaProducer = kafkaProducer;
    }

    @KafkaListener(topics = "${order-product-cost.cart.add}", groupId = "pizza-online")
    public void processSaveOrderProductCost(String message) {
        log.info("received message = {}", message);
        try {
            OrderProductCostDto orderProductCostDto = objectMapper.readValue(message, OrderProductCostDto.class);
            log.info("order-product-cost dto: {} prepared to save", objectMapper.writeValueAsString(orderProductCostDto));
            Integer sum = orderProductCostService.addToCart(orderProductCostDto);
            sendSum(sum);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
    }

    @KafkaListener(topics = "${order-product-cost.get.sum}", groupId = "pizza-online")
    public void processGetSum(String message) {
        log.info("received message = {}", message);
        Integer sum = orderProductCostService.getQuantitiesSum(message);
        sendSum(sum);
    }

    @KafkaListener(topics = "${order-product-cost.cart.update}", groupId = "pizza-online")
    public void processUpdateOrderProductCost(String message) {
        log.info("received message = {}", message);
        try {
            OrderProductCostDto orderProductCostDto = objectMapper.readValue(message, OrderProductCostDto.class);
            log.info("order-product-cost dto: {} prepared to update", objectMapper.writeValueAsString(orderProductCostDto));
            Integer sum = orderProductCostService.updateQuantity(orderProductCostDto);
            sendSum(sum);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
    }

    @KafkaListener(topics = "${order-product-cost.cart.delete}", groupId = "pizza-online")
    public void processDeleteOrderProductCost(String message) {
        log.info("received message = {}", message);
        try {
            OrderProductCostDto orderProductCostDto = objectMapper.readValue(message, OrderProductCostDto.class);
            log.info("order-product-cost dto: {} prepared to update", objectMapper.writeValueAsString(orderProductCostDto));
            Integer sum = orderProductCostService.deleteFromCart(orderProductCostDto);
            sendSum(sum);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
    }

    private void sendSum(Integer sum) {
        if (sum != null) {
            kafkaProducer.produce(receiveSumTopic, "pizza-online", sum);
            log.info("sum: {} sent to topic {}", sum, receiveSumTopic);
        }
    }
}
