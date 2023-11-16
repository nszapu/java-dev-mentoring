package org.palmetto.kafka;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.palmetto.model.OrderMessage;
import org.palmetto.model.Status;
import org.palmetto.service.MessageListenerService;
import org.palmetto.service.MessageProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@DirtiesContext
@SpringBootTest
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9093", "port=9093"})
public class MessageServiceTest {

    @Autowired
    private MessageProducerService messageProducerService;
    @Autowired
    private MessageListenerService messageListenerService;

    @SneakyThrows
    @Test
    void givenEmbeddedKafkaBroker_whenSendingWithProducer_thenMessageReceived() {
        OrderMessage orderMessage = OrderMessage
                .builder()
                .pizza("pizza")
                .address("address")
                .date(LocalDate.now())
                .name("name")
                .comment("comment")
                .phoneNumber("phone-number")
                .status(Status.RECEIVED)
                .build();

        messageProducerService.send(UUID.randomUUID().toString(), orderMessage);

        orderMessage.setStatus(Status.READY);
        boolean messageConsumed = messageListenerService.getLatch().await(10, TimeUnit.SECONDS);
        assertTrue(messageConsumed);
        assertThat(messageListenerService.getPayload())
                .usingRecursiveComparison()
                .isEqualTo(orderMessage);
    }
}
