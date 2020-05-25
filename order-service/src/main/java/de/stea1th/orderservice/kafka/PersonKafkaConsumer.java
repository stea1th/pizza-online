package de.stea1th.orderservice.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PersonKafkaConsumer {

    private final ObjectMapper objectMapper = new ObjectMapper();
//
//    @KafkaListener
//    public void processPersonGetResponse(String json) {
//
//    }



}
