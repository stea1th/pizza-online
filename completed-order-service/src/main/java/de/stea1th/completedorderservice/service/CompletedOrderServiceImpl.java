package de.stea1th.completedorderservice.service;


import de.stea1th.completedorderservice.converter.OrderConverter;
import de.stea1th.completedorderservice.dto.CompletedOrderDto;
import de.stea1th.completedorderservice.dto.OrderDto;
import de.stea1th.completedorderservice.dto.ProductCostInCartDto;
import de.stea1th.completedorderservice.kafka.OrderKafkaProducer;
import de.stea1th.completedorderservice.kafka.PersonKafkaProducer;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CompletedOrderServiceImpl {

    private final PersonKafkaProducer personKafkaProducer;
    private final OrderKafkaProducer orderKafkaProducer;
    private final OrderConverter orderConverter;

    public CompletedOrderServiceImpl(PersonKafkaProducer personKafkaProducer, OrderKafkaProducer orderKafkaProducer, OrderConverter orderConverter) {
        this.personKafkaProducer = personKafkaProducer;
        this.orderKafkaProducer = orderKafkaProducer;
        this.orderConverter = orderConverter;
    }

    public OrderDto test(String keycloak) {
        String json = orderKafkaProducer.getOrderAsJson(keycloak);
        return orderConverter.convertJsonToDto(json);
    }


    public List<CompletedOrderDto> getCompletedOrders(String keycloak, String value) {
        Map<String, String> message = Collections.singletonMap(keycloak, value);
        String json = orderKafkaProducer.getOrdersForTimeInterval(message);
        List<OrderDto> orders = orderConverter.convertToDtoList(json);
        return orders
                .stream()
                .map(this::createCompletedOrderDto)
                .collect(Collectors.toList());
    }

    public CompletedOrderDto createCompletedOrderDto(OrderDto order) {
        List<ProductCost> productCostList = productCostRepository.getAllByOrderId(order.getId());
        List<ProductCostInCartDto> dtoList = productCostList
                .stream()
                .map(productCost -> {
                    var orderProductCost = orderProductCostService.get(order.getId(), productCost.getId());
                    return productCostConverter.convertToDtoInCart(productCost, orderProductCost);
                }).collect(Collectors.toList());
//        OrderDto orderDto = orderConverter.convertToDtoWithoutOPC(order);
        var completedOrderDto = new CompletedOrderDto();
        completedOrderDto.setOrderDto(order);
        completedOrderDto.setProductCostInCartDtoList(dtoList);
        return completedOrderDto;
    }

    public List<ProductCostInCartDto> createProductCostInCartDtoList() {

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
