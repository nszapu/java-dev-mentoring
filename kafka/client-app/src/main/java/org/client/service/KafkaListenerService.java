package org.client.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.client.model.OrderMessage;
import org.client.model.OrderResponse;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaListenerService {

    private OrderService service;

    public KafkaListenerService(OrderService service) {
        this.service = service;
    }

    @KafkaListener(topics = "notification")
    public void listener(ConsumerRecord<String, OrderMessage> record) {
        log.info(record.toString());
        service.updateOrder(record.key(), record.value());
    }
}
