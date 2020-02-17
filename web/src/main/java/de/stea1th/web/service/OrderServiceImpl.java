package de.stea1th.web.service;

import de.stea1th.commonslibrary.component.KafkaProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final KafkaProducer kafkaProducer;

    public OrderServiceImpl(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @Override
    public void buy() {
        log.info("");
//        kafkaProducer.produce();

    }
}
