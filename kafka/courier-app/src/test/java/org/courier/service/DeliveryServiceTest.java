package org.courier.service;

import org.courier.model.OrderMessage;
import org.courier.model.Status;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
public class DeliveryServiceTest {

    @Mock
    private MessageProducerService messageProducerService;
    @InjectMocks
    private DeliveryService deliveryService;

    @Test
    void deliver() {
        String key = "key";
        OrderMessage message = new OrderMessage("pizza", "comment", Status.RECEIVED, "name", "address", "phone-number", LocalDate.now());
        doNothing().when(messageProducerService).send(isA(String.class), isA(OrderMessage.class));
        deliveryService.deliver(key, message);
    }
}
