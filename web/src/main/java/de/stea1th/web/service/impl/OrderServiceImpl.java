package de.stea1th.web.service.impl;

import de.stea1th.commonslibrary.component.KafkaProducer;
import de.stea1th.commonslibrary.dto.*;
import de.stea1th.commonslibrary.num.TimeInterval;
import de.stea1th.web.kafka.OrderKafkaConsumer;
import de.stea1th.web.service.OrderService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final KafkaProducer kafkaProducer;
    private OrderKafkaConsumer orderKafkaConsumer;

    @Value("${order.get.complete}")
    private String orderGetCompleteTopic;

    @Value("${order.get.complete.year}")
    private String orderGetCompletedYear;

    @Value("${order.get.completed.orders}")
    private String orderGetCompletedOrders;

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

    @SneakyThrows
    @Override
    public List<TimeIntervalDto> getInterval(String keycloak) {
        log.info("get time interval for keycloak {}", keycloak);
        var intervals = new ArrayList<TimeIntervalDto>();
        Arrays.stream(TimeInterval.values()).forEach(x -> {
            intervals.add(new TimeIntervalDto(x.name(), x.getDescription()));
        });
        List<Integer> years;
        kafkaProducer.produce(orderGetCompletedYear, keycloak);
        for (int i = 0; i < attempt; i++) {
            TimeUnit.MILLISECONDS.sleep(delay);
            years = orderKafkaConsumer.getYears();
            if(years != null) {
                years.forEach(year-> intervals.add(new TimeIntervalDto(year.toString(), year.toString())));
                break;
            }
        }
        return intervals;
    }

    @SneakyThrows
    public List<CompletedOrderDto> getCompletedOrders(CompletedOrdersRequestDto completedOrdersRequestDto) {
        log.info("get time interval for keycloak {}", completedOrdersRequestDto);
        kafkaProducer.produce(orderGetCompletedOrders, completedOrdersRequestDto);
        List<CompletedOrderDto> completedOrderDtoList = null;
        for (int i = 0; i < attempt; i++) {
            TimeUnit.MILLISECONDS.sleep(delay);
            completedOrderDtoList = orderKafkaConsumer.getCompletedOrderDtoList();
            if(completedOrderDtoList != null) break;
        }
        return completedOrderDtoList;
    }
}
