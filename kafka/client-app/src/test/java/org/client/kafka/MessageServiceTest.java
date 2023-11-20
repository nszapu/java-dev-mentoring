package org.client.kafka;

import lombok.SneakyThrows;
import org.client.model.OrderMessage;
import org.client.model.Status;
import org.client.service.MessageListenerService;
import org.client.service.MessageProducerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

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

        await().atMost(10, TimeUnit.SECONDS).until(() -> messageListenerService.getPayload() != null);
        OrderMessage actualOrderMessage = messageListenerService.getPayload();
        assertThat(actualOrderMessage)
                .usingRecursiveComparison()
                .isEqualTo(orderMessage);
    }
}
