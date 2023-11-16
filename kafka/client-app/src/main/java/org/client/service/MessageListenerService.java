package org.client.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.client.model.OrderMessage;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Slf4j
@Component
public class MessageListenerService {

    private final OrderService service;
    @Getter
    private CountDownLatch latch = new CountDownLatch(1);
    @Getter
    private OrderMessage payload;

    public MessageListenerService(OrderService service) {
        this.service = service;
    }

    @KafkaListener(topics = "${kafka.topic.to-consume-from}")
    public void listener(ConsumerRecord<String, OrderMessage> record) {
        log.info(record.toString());
        payload = record.value();
        service.updateOrder(record.key(), record.value());
        latch.countDown();
    }

    public void resetLatch() {
        latch = new CountDownLatch(1);
    }
}
