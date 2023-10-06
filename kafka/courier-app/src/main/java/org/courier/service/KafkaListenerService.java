package org.courier.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.courier.model.OrderMessage;
import org.courier.model.Status;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
public class KafkaListenerService {

    private DeliveryService service;

    public KafkaListenerService(DeliveryService service) {
        this.service = service;
    }

    @KafkaListener(topics = "notification")
    public void listener(ConsumerRecord<String, OrderMessage> record) {
        log.info(record.toString());
        if (Objects.equals(record.value().getStatus(), Status.READY)) {
            service.deliver(record.key(), record.value());
        }
    }
}
