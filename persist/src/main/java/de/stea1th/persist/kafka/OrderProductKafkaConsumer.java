package de.stea1th.persist.kafka;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.stea1th.persist.entity.OrderProduct;
import de.stea1th.persist.service.OrderProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderProductKafkaConsumer {

    private final OrderProductService orderProductService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public OrderProductKafkaConsumer(OrderProductService orderProductService) {
        this.orderProductService = orderProductService;
    }

    @KafkaListener(topics = "${order-product.save.id}", groupId = "pizza-online")
    public void processSaveOrderProduct(String message) {
        log.info("received message = {}", message);
        try {
            OrderProduct orderProduct = objectMapper.readValue(message, OrderProduct.class);
            log.info("order-product: {} prepared to save", objectMapper.writeValueAsString(orderProduct));
            orderProductService.save(orderProduct);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }

    }
}
