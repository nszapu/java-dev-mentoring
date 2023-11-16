package org.client.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.client.model.OrderMessage;
import org.client.model.Status;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
public class MessageListenerServiceTest {

    @Mock
    private OrderService orderService;
    @InjectMocks
    private MessageListenerService messageListenerService;

    @Test
    void listener() {
        String key = "key";
        OrderMessage value = new OrderMessage("pizza", "comment", Status.DELIVERED, "name", "address", "phone-number", LocalDate.now());
        ConsumerRecord<String, OrderMessage> record = new ConsumerRecord<>("topic", 1, 1, key, value);
        doNothing().when(orderService).updateOrder(isA(String.class), isA(OrderMessage.class));
        messageListenerService.listener(record);
    }
}
