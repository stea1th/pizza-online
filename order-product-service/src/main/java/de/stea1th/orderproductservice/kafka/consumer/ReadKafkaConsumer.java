package de.stea1th.orderproductservice.kafka.consumer;


import com.fasterxml.jackson.databind.ObjectMapper;
import de.stea1th.orderproductservice.entity.OrderProductCost;
import de.stea1th.orderproductservice.service.OrderProductCostService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class ReadKafkaConsumer {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final OrderProductCostService orderProductCostService;

    public ReadKafkaConsumer(OrderProductCostService orderProductCostService) {
        this.orderProductCostService = orderProductCostService;
    }

    @SneakyThrows
    @KafkaListener(topics="${order-product.all}")
    @SendTo
    public String getAllOrderProductCostsByOrderId(String message) {
        log.info("receiving orderId: {}", message);
        List<OrderProductCost> allOrderProductCosts = orderProductCostService.getAllOrderProductCostsByOrderId(Integer.parseInt(message));
        return objectMapper.writeValueAsString(allOrderProductCosts);
    }


}
