package org.courier.service;

import org.courier.model.OrderMessage;
import org.courier.model.Status;
import org.springframework.stereotype.Service;

@Service
public class DeliveryService {

    private final MessageProducerService messageProducerService;

    public DeliveryService(MessageProducerService messageProducerService) {
        this.messageProducerService = messageProducerService;
    }

    public void deliver(String key, OrderMessage order) {
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        order.setStatus(Status.DELIVERED);
        messageProducerService.send(key, order);
    }
}
