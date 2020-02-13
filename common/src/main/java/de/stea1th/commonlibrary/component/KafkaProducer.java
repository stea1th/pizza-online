package de.stea1th.commonlibrary.component;

public interface KafkaProducer {

    void produce(String topic, String groupId, Object object);

}
