package de.stea1th.web.service.impl;

import de.stea1th.commonslibrary.component.KafkaProducer;
import de.stea1th.commonslibrary.dto.LocalDateTimeDto;
import de.stea1th.commonslibrary.dto.OrderDto;
import de.stea1th.web.kafka.OrderKafkaConsumer;
import de.stea1th.web.service.OrderService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final KafkaProducer kafkaProducer;
    private OrderKafkaConsumer orderKafkaConsumer;

    @Value("${order.get.complete}")
    private String orderGetCompleteTopic;

    @Value("#{new Integer('${kafka.service.attempt}')}")
    private Integer attempt;

    @Value("#{new Integer('${kafka.service.delay}')}")
    private Integer delay;

    public OrderServiceImpl(KafkaProducer kafkaProducer, OrderKafkaConsumer orderKafkaConsumer) {
        this.kafkaProducer = kafkaProducer;
        this.orderKafkaConsumer = orderKafkaConsumer;
    }

    @SneakyThrows
    @Override
    public LocalDateTimeDto complete(String keycloak) {
        log.info("complete order for keycloak {}", keycloak);
        OrderDto orderDto = null;
        kafkaProducer.produce(orderGetCompleteTopic, keycloak);
        for (int i = 0; i < attempt; i++) {
            TimeUnit.MILLISECONDS.sleep(delay);
            orderDto = orderKafkaConsumer.getOrderDto();
            if (orderDto != null) {
                break;
            }
        }
        return orderDto == null ? null : orderDto.getCompleted();
    }
}
