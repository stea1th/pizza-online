package de.stea1th.orderservice.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class KafkaProducer {

    private final ReplyingKafkaTemplate<String, String, String> replyingKafkaTemplate;
    private final ObjectMapper objectMapper;

    public KafkaProducer(ReplyingKafkaTemplate<String, String, String> replyingKafkaTemplate, ObjectMapper objectMapper) {
        this.replyingKafkaTemplate = replyingKafkaTemplate;
        this.objectMapper = objectMapper;
    }

    @SneakyThrows
    public String produceWithReply(String topic, Object object) {
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, objectMapper.writeValueAsString(object));
        RequestReplyFuture<String, String, String> requestReplyFuture = replyingKafkaTemplate.sendAndReceive(record);
        ConsumerRecord<String, String> consumerRecord = requestReplyFuture.get(10, TimeUnit.SECONDS);
        log.info("Return value: " + consumerRecord.value());
        return "null";
    }


}
