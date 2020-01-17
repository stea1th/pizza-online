package de.stea1th.web.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaProducerImpl implements KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final ObjectMapper objectMapper;

    @Autowired
    public KafkaProducerImpl(@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = new ObjectMapper();
    }

    public void getById(String topic, String groupId, Integer id) {
        log.info("Sending id to kafka = '{} with topic '{}'", id, topic);
        kafkaTemplate.send(topic, groupId, String.valueOf(id));
    }


}
