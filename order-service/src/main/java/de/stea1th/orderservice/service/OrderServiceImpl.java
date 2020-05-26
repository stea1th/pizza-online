package de.stea1th.orderservice.service;


import de.stea1th.orderservice.entity.Order;
import de.stea1th.orderservice.kafka.OrderProductKafkaProducer;
import de.stea1th.orderservice.kafka.PersonKafkaProducer;
import de.stea1th.orderservice.repository.OrderRepository;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final PersonKafkaProducer personKafkaProducer;
    private final OrderProductKafkaProducer orderProductKafkaProducer;
    private final OrderRepository orderRepository;

//    @Value("${pdf-creator.create.invoice}")
//    private String pdfCreateTopic;

    @Autowired
    public OrderServiceImpl(PersonKafkaProducer personKafkaProducer, OrderProductKafkaProducer orderProductKafkaProducer, OrderRepository orderRepository) {
        this.personKafkaProducer = personKafkaProducer;
        this.orderProductKafkaProducer = orderProductKafkaProducer;
        this.orderRepository = orderRepository;
    }

    @SneakyThrows
    @Override
    public Order getUncompletedOrderByPersonKeycloak(String keycloak) {
        int personId = personKafkaProducer.getPersonIdByKeycloak(keycloak);
        return getUncompletedOrderByPersonId(personId);
    }

    public Order getUncompletedOrderByPersonId(int personId) {
        List<Order> orders = orderRepository.findByPersonIdAndCompletedIsNull(personId);
        return orders.isEmpty() ? createEmptyOrder(personId) : getUncompletedOrder(orders);
    }

//    @Override
//    public List<CompletedOrderDto> getCompletedOrders(CompletedOrdersRequestDto completedOrdersRequestDto) {
//        Person person = personService.getByKeycloak(completedOrdersRequestDto.getKeycloak());
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
//
//    @Override
//    @Transactional
//    public Order completeOrder(String keycloak) {
//        Order order = complete(keycloak);
//        produceInvoiceAsPdf(order);
//        return order;
//    }
//
//    public void produceInvoiceAsPdf(Order order) {
//        CompletedOrderDto completedOrderDto = createCompletedOrderDto(order);
//        kafkaProducer.produce(pdfCreateTopic, completedOrderDto);
//    }
//
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
//
//    public List<Integer> getCompletedYearsByPerson(String keycloak) {
//        var person = personService.getByKeycloak(keycloak);
//        return orderRepository.findCompletedYearsByPerson(person);
//    }

    public Order complete(String keycloak) {
        Order order = getUncompletedOrderByPersonKeycloak(keycloak);
        if (orderProductKafkaProducer.isOrderProductExists(order.getId())) {
            order.setCompleted(LocalDateTime.now());
            order = orderRepository.save(order);
            createEmptyOrder(order.getPersonId());
            log.info("order complete for keycloak: {}", keycloak);
        }
        return order;
    }

    private Order createEmptyOrder(int personId) {
        Order order = new Order();
        order.setPersonId(personId);
        order = orderRepository.save(order);
        log.info("new uncompleted order with id: {} created", order.getId());
        return order;
    }

    private Order getUncompletedOrder(List<Order> orders) {
        Order order = orders.get(0);
        log.info("existing uncompleted order with id: {}", order.getId());
        return order;
    }
}
