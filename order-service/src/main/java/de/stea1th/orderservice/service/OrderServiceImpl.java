package de.stea1th.orderservice.service;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.stea1th.orderservice.entity.Order;
import de.stea1th.orderservice.kafka.PersonKafkaProducer;
import de.stea1th.orderservice.repository.OrderRepository;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final PersonKafkaProducer personKafkaProducer;
    private final OrderRepository orderRepository;
//    private final ObjectMapper objectMapper = new ObjectMapper();



//    public OrderServiceImpl(KafkaProducer kafkaProducer) {
//        this.kafkaProducer = kafkaProducer;
//    }


//    @Override
//    public Order getUncompletedOrderByPersonKeycloak(String keycloak) {
//
//        kafkaProducer.produce("test", keycloak);
//
//
//        return null;
//    }
//
//    @Override
//    public Order completeOrder(String keycloak) {
//        return null;
//    }
//
//    @Override
//    public List<Integer> getCompletedYearsByPerson(String keycloak) {
//        return null;
//    }
//
//    @Override
//    public List<CompletedOrderDto> getCompletedOrders(CompletedOrdersRequestDto completedOrdersRequestDto) {
//        return null;
//    }

//    private PersonService personService;


//    private ProductCostRepository productCostRepository;
//
//    private ProductCostConverter productCostConverter;
//
//    private OrderProductCostService orderProductCostService;
//
//    private OrderConverter orderConverter;

//    private KafkaProducer kafkaProducer;

//    @Value("${pdf-creator.create.invoice}")
//    private String pdfCreateTopic;

    @Autowired
    public OrderServiceImpl(PersonKafkaProducer personKafkaProducer, OrderRepository orderRepository) {
        this.personKafkaProducer = personKafkaProducer;
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

//    private Order complete(String keycloak) {
//        Person person = personService.getByKeycloak(keycloak);
//        Order order = getUncompletedOrderByPersonId(person);
//        if (!order.getOrderProductCosts().isEmpty()) {
//            order.setCompleted(LocalDateTime.now());
//            order = orderRepository.save(order);
//            createEmptyOrder(person);
//        }
//        return order;
//    }

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
