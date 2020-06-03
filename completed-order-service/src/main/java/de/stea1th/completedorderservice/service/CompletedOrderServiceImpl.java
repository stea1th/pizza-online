package de.stea1th.completedorderservice.service;


import de.stea1th.completedorderservice.converter.OrderConverter;
import de.stea1th.completedorderservice.dto.OrderDto;
import de.stea1th.completedorderservice.kafka.OrderKafkaProducer;
import de.stea1th.completedorderservice.kafka.PersonKafkaProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
        return orderConverter.convertToDto(json);
    }


//    public List<CompletedOrderDto> getCompletedOrders(String keycloak, String value) {
//        int person = personService.getByKeycloak(completedOrdersRequestDto.getKeycloak());
//        List<Order> orders;
//        try {
//            var timeInterval = TimeInterval.valueOf(completedOrdersRequestDto.getValue());
//            orders = orderRepository.findByPersonAndCompletedAfterOrderByCompletedDesc(person, timeInterval.getTime());
//        } catch (IllegalArgumentException e) {
//            orders = orderRepository.findByPersonAndCompletedYear(person, completedOrdersRequestDto.getValue());
//        }
//        return orders
//                .stream()
//                .map(this::createCompletedOrderDto)
//                .collect(Collectors.toList());
//    }

    //    @Transactional
//    public CompletedOrderDto createCompletedOrderDto(Order order) {
//        List<ProductCost> productCostList = productCostRepository.getAllByOrderId(order.getId());
//        List<ProductCostInCartDto> dtoList = productCostList
//                .stream()
//                .map(productCost -> {
//                    var orderProductCost = orderProductCostService.get(order.getId(), productCost.getId());
//                    return productCostConverter.convertToDtoInCart(productCost, orderProductCost);
//                }).collect(Collectors.toList());
//        OrderDto orderDto = orderConverter.convertToDtoWithoutOPC(order);
//        var pdfCreatorDto = new CompletedOrderDto();
//        pdfCreatorDto.setOrderDto(orderDto);
//        pdfCreatorDto.setProductCostInCartDtoList(dtoList);
//        return pdfCreatorDto;
//    }

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
