package de.stea1th.web.service;

import de.stea1th.kafkalibrary.component.PizzaService;
import de.stea1th.kafkalibrary.dto.PizzaDto;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

@Service
@Slf4j
@EnableScheduling
public class WebService {

    private final PizzaService pizzaService;

    @Autowired
    public WebService(PizzaService pizzaService) {
        this.pizzaService = pizzaService;
    }

    @Scheduled(initialDelay = 5000, fixedDelay = 5000)
    public void produce() {

        var pizzaDto = createDto();
        pizzaService.send(pizzaDto);
    }


    @KafkaListener(id = "group_id", topics = {"server.pizza"})
    @SneakyThrows
    public void consume(String message) {
        log.info("=> consumed {}", message);
//        var pizzaDto = objectMapper.readValue(message, PizzaDto.class);
//        log.info("=> Pizza price: {}", pizzaDto.getPrice());
    }

    private PizzaDto createDto() {
        var pizzaDto = new PizzaDto();
        pizzaDto.setName("Pizza Mozarella");
        pizzaDto.setPrice(getRandomPrice());
        return pizzaDto;
    }

    private double getRandomPrice() {
        var random = new Random();
        var bigDecimal = new BigDecimal(random.nextDouble() * 100);
        return bigDecimal.setScale(2, RoundingMode.CEILING).doubleValue();
    }


}
