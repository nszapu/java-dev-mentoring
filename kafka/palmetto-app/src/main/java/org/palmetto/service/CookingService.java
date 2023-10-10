package org.palmetto.service;

import org.palmetto.model.OrderMessage;
import org.palmetto.model.Status;
import org.springframework.stereotype.Service;

@Service
public class CookingService {

    private final MessageProducerService messageProducerService;

    public CookingService(MessageProducerService messageProducerService) {
        this.messageProducerService = messageProducerService;
    }

    public void cook(String key, OrderMessage orderMessage) {
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        orderMessage.setStatus(Status.READY);
        messageProducerService.send(key, orderMessage);
    }
}
