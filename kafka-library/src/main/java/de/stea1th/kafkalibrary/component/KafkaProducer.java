package de.stea1th.kafkalibrary.component;

public interface KafkaProducer {

    void produce(String topic, String groupId, Object object);

}
