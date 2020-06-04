package de.stea1th.completedorderservice.service;


import de.stea1th.completedorderservice.converter.OrderConverter;
import de.stea1th.completedorderservice.dto.CartElementDto;
import de.stea1th.completedorderservice.dto.CompletedOrderDto;
import de.stea1th.completedorderservice.dto.OrderDto;
import de.stea1th.completedorderservice.kafka.OrderKafkaProducer;
import de.stea1th.completedorderservice.kafka.OrderProductKafkaProducer;
import de.stea1th.completedorderservice.kafka.PersonKafkaProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CompletedOrderServiceImpl implements CompletedOrderService {

    private final PersonKafkaProducer personKafkaProducer;
    private final OrderKafkaProducer orderKafkaProducer;
    private final OrderConverter orderConverter;
    private final OrderProductKafkaProducer orderProductKafkaProducer;

    public CompletedOrderServiceImpl(PersonKafkaProducer personKafkaProducer, OrderKafkaProducer orderKafkaProducer, OrderConverter orderConverter, OrderProductKafkaProducer orderProductKafkaProducer) {
        this.personKafkaProducer = personKafkaProducer;
        this.orderKafkaProducer = orderKafkaProducer;
        this.orderConverter = orderConverter;
        this.orderProductKafkaProducer = orderProductKafkaProducer;
    }

//    public OrderDto test(String keycloak) {
//        String json = orderKafkaProducer.getOrderAsJson(keycloak);
//        return orderConverter.convertJsonToDto(json);
//    }


    public List<CompletedOrderDto> getCompletedOrders(String keycloak, String value) {
        Map<String, String> message = Collections.singletonMap(keycloak, value);
        String json = orderKafkaProducer.getOrdersForTimeInterval(message);
        List<OrderDto> orders = orderConverter.convertToDtoList(json);
        return orders
                .stream()
                .map(this::createCompletedOrderDto)
                .collect(Collectors.toList());
    }

    private CompletedOrderDto createCompletedOrderDto(OrderDto order) {
        CompletedOrderDto completedOrderDto = new CompletedOrderDto();
        completedOrderDto.setOrderDto(order);
        List<CartElementDto> cartElementsByOrderId = orderProductKafkaProducer.getCartElementsByOrderId(order.getId());
        completedOrderDto.setCartElementList(cartElementsByOrderId);
        return completedOrderDto;
    }

    //    @Transactional
//    public Order completeOrder(String keycloak) {
//        Order order = complete(keycloak);
////        produceInvoiceAsPdf(order);
//        return order;
//    }
//
//    public void produceInvoiceAsPdf(Order order) {
//        CompletedOrderDto completedOrderDto = createCompletedOrderDto(order);
//        kafkaProducer.produce(pdfCreateTopic, completedOrderDto);
//    }

}
