package org.client.service;

import org.client.model.OrderMessage;
import org.client.model.Status;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MessageProducerServiceTest {

    @Mock
    private KafkaTemplate<String, OrderMessage> kafkaTemplate;
    @InjectMocks
    private MessageProducerService service;

    @Test
    void send() {
        ReflectionTestUtils.setField(service, "topic", "topic");
        String key = "key";
        OrderMessage value = new OrderMessage("pizza", "comment", Status.RECEIVED, "name", "address", "phone-number", LocalDate.now());
        when(kafkaTemplate.send(isA(String.class), isA(String.class), isA(OrderMessage.class))).thenReturn(new CompletableFuture<>());
        service.send(key, value);
    }
}
