package de.stea1th.orderservice.service;


import de.stea1th.commonslibrary.component.KafkaProducer;
import de.stea1th.commonslibrary.dto.CompletedOrderDto;
import de.stea1th.commonslibrary.dto.CompletedOrdersRequestDto;
import de.stea1th.commonslibrary.dto.OrderDto;
import de.stea1th.commonslibrary.dto.ProductCostInCartDto;
import de.stea1th.commonslibrary.num.TimeInterval;
import de.stea1th.orderservice.entity.Order;
import de.stea1th.orderservice.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private PersonService personService;

    private OrderRepository orderRepository;

    private ProductCostRepository productCostRepository;

    private ProductCostConverter productCostConverter;

    private OrderProductCostService orderProductCostService;

    private OrderConverter orderConverter;

    private KafkaProducer kafkaProducer;

    @Value("${pdf-creator.create.invoice}")
    private String pdfCreateTopic;

    @Autowired
    public OrderServiceImpl(PersonService personService,
                            OrderRepository orderRepository,
                            ProductCostRepository productCostRepository,
                            ProductCostConverter productCostConverter,
                            @Lazy OrderProductCostService orderProductCostService,
                            OrderConverter orderConverter, KafkaProducer kafkaProducer) {
        this.personService = personService;
        this.orderRepository = orderRepository;
        this.productCostRepository = productCostRepository;
        this.productCostConverter = productCostConverter;
        this.orderProductCostService = orderProductCostService;
        this.orderConverter = orderConverter;
        this.kafkaProducer = kafkaProducer;
    }

    @Override
    public Order getUncompletedOrderByPersonKeycloak(String keycloak) {
        Person person = personService.getByKeycloak(keycloak);
        return getUncompletedOrderByPerson(person);
    }

    @Override
    public Order getUncompletedOrderByPerson(Person person) {
        List<Order> orders = orderRepository.findByPersonAndCompletedOrderByCreatedAsc(person, null);
        return orders.isEmpty() ? createEmptyOrder(person) : getUncompletedOrder(orders);
    }

    @Override
    public List<CompletedOrderDto> getCompletedOrders(CompletedOrdersRequestDto completedOrdersRequestDto) {
        Person person = personService.getByKeycloak(completedOrdersRequestDto.getKeycloak());
        List<Order> orders;
        try {
            var timeInterval = TimeInterval.valueOf(completedOrdersRequestDto.getValue());
            orders = orderRepository.findByPersonAndCompletedAfterOrderByCompletedDesc(person, timeInterval.getTime());
        } catch (IllegalArgumentException e) {
            orders = orderRepository.findByPersonAndCompletedYear(person, completedOrdersRequestDto.getValue());
        }
        return orders
                .stream()
                .map(this::createCompletedOrderDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Order completeOrder(String keycloak) {
        Order order = complete(keycloak);
        produceInvoiceAsPdf(order);
        return order;
    }

    public void produceInvoiceAsPdf(Order order) {
        CompletedOrderDto completedOrderDto = createCompletedOrderDto(order);
        kafkaProducer.produce(pdfCreateTopic, completedOrderDto);
    }

    @Transactional
    public CompletedOrderDto createCompletedOrderDto(Order order) {
        List<ProductCost> productCostList = productCostRepository.getAllByOrderId(order.getId());
        List<ProductCostInCartDto> dtoList = productCostList
                .stream()
                .map(productCost -> {
                    var orderProductCost = orderProductCostService.get(order.getId(), productCost.getId());
                    return productCostConverter.convertToDtoInCart(productCost, orderProductCost);
                }).collect(Collectors.toList());
        OrderDto orderDto = orderConverter.convertToDtoWithoutOPC(order);
        var pdfCreatorDto = new CompletedOrderDto();
        pdfCreatorDto.setOrderDto(orderDto);
        pdfCreatorDto.setProductCostInCartDtoList(dtoList);
        return pdfCreatorDto;
    }

    public List<Integer> getCompletedYearsByPerson(String keycloak) {
        var person = personService.getByKeycloak(keycloak);
        return orderRepository.findCompletedYearsByPerson(person);
    }

    private Order complete(String keycloak) {
        Person person = personService.getByKeycloak(keycloak);
        Order order = getUncompletedOrderByPerson(person);
        if (!order.getOrderProductCosts().isEmpty()) {
            order.setCompleted(LocalDateTime.now());
            order = orderRepository.save(order);
            createEmptyOrder(person);
        }
        return order;
    }

    private Order createEmptyOrder(Person person) {
        Order order = new Order();
        order.setPerson(person);
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
