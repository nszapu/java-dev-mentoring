package org.courier.service;

import org.courier.model.OrderMessage;
import org.courier.model.Status;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class DeliveryService {

    private KafkaTemplate<String, OrderMessage> kafkaTemplate;
    @Value("${kafka.topic.to-produce-to}")
    private String topic;

    public DeliveryService(KafkaTemplate<String, OrderMessage> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void deliver(String key, OrderMessage order) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        order.setStatus(Status.DELIVERED);
        kafkaTemplate.send(topic, key, order);
    }
}
