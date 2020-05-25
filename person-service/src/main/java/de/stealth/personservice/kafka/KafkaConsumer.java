package de.stealth.personservice.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaConsumer {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics="test", groupId = "pizza-online")
    @SendTo
    public String processPersonGetResponse(String json) {
        log.info("received: {}", json);
        return "Azm esm Zcar :)))";

    }


}
