package org.palmetto.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.palmetto.model.OrderMessage;
import org.palmetto.model.Status;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
public class MessageListenerService {

    private final CookingService service;
    @Getter
    private OrderMessage payload;

    public MessageListenerService(CookingService service) {
        this.service = service;
    }

    @KafkaListener(topics = "${kafka.topic.to-consume-from}")
    public void listener(ConsumerRecord<String, OrderMessage> record) {
        log.info(record.toString());
        payload = record.value();
        if (Objects.equals(record.value().getStatus(), Status.RECEIVED)) {
            service.cook(record.key(), record.value());
        }
    }
}
