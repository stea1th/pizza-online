package de.stea1th.web.service;

public interface KafkaProducer {

    void getById(String topic, String groupId, Integer id);

}
