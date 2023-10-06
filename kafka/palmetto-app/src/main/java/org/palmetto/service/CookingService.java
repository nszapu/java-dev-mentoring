package org.palmetto.service;

import org.palmetto.model.OrderMessage;
import org.palmetto.model.Status;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class CookingService {

    private KafkaTemplate<String, OrderMessage> kafkaTemplate;
    @Value("${kafka.topic.to-produce-to}")
    private String topic;

    public CookingService(KafkaTemplate<String, OrderMessage> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void cook(String key, OrderMessage orderMessage) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        orderMessage.setStatus(Status.READY);
        kafkaTemplate.send(topic, key, orderMessage);
    }
}
