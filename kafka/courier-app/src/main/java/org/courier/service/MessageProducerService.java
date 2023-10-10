package org.courier.service;

import org.courier.model.OrderMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageProducerService {

    private final KafkaTemplate<String, OrderMessage> kafkaTemplate;
    @Value("${kafka.topic.to-produce-to}")
    private String topic;

    public MessageProducerService(KafkaTemplate<String, OrderMessage> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(String key, OrderMessage message) {
        kafkaTemplate.send(topic, key, message);
    }
}
