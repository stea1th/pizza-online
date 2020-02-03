package de.stea1th.kafkalibrary.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaProducerImpl implements KafkaProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

//    private final ObjectMapper objectMapper;

    @Autowired
    public KafkaProducerImpl(@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
//        this.objectMapper = new ObjectMapper();
    }

    public void produce(String topic, String groupId, Object object) {
        log.info("sending object to kafka = '{} with topic '{}'", object, topic);
        kafkaTemplate.send(topic, groupId, object);
    }
}
