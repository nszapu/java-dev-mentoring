package org.palmetto.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.palmetto.model.OrderMessage;
import org.palmetto.model.Status;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
public class CookingServiceTest {

    @Mock
    private MessageProducerService messageProducerService;
    @InjectMocks
    private CookingService cookingService;

    @Test
    void cook() {
        String key = "key";
        OrderMessage message = new OrderMessage("pizza", "comment", Status.RECEIVED, "name", "address", "phone-number", LocalDate.now());
        doNothing().when(messageProducerService).send(isA(String.class), isA(OrderMessage.class));
        cookingService.cook(key, message);
    }
}
