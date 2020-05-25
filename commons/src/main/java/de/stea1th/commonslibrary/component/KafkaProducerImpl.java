package de.stea1th.commonslibrary.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaProducerImpl implements KafkaProducer {

//    private final KafkaTemplate<String, Object> kafkaTemplate;
//
////    private final ReplyingKafkaTemplate<String, String, String> replyingKafkaTemplate;
//
//    private final ObjectMapper objectMapper;
//
//    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
//    @Autowired
//    public KafkaProducerImpl(KafkaTemplate<String, Object> kafkaTemplate, ObjectMapper objectMapper) {
//        this.kafkaTemplate = kafkaTemplate;
//        this.objectMapper = objectMapper;
//    }
//
//    public void produce(String topic, String groupId, Object object) {
//        log.info("sending object to kafka = '{}' with topic '{}'", object, topic);
//        kafkaTemplate.send(topic, groupId, object);
//    }
//
//    public void produce(String topic, Object object) {
//        produce(topic, "pizza-online", object);
//    }
//
////    @SneakyThrows
////    public String produceWithReply(String topic, Object object) {
////        ProducerRecord<String, String> record = new ProducerRecord<>(topic, objectMapper.writeValueAsString(object));
////        RequestReplyFuture<String, String, String> requestReplyFuture = replyingKafkaTemplate.sendAndReceive(record);
////        ConsumerRecord<String, String> consumerRecord = requestReplyFuture.get(10, TimeUnit.SECONDS);
////        log.info("Return value: " + consumerRecord.value());
////        return "null";
////    }
}
