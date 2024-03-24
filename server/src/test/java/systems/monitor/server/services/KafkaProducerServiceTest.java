package systems.monitor.server.services;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// Unit tests for the {@link KafkaProducerService} class.
// The tests examine both successful and unsuccessful scenarios.
// @author Antonio Scardace
// @version 1.0

@SpringBootTest
class KafkaProducerServiceTest {

    @Mock
    KafkaTemplate<String, String> kafkaTemplate;

    @InjectMocks
    KafkaProducerService kafkaProducerService;

    @Test
    void testSendMessage_Successful() {
        String topicName = "test-topic";
        String message = "test-message";
        assertTrue(kafkaProducerService.sendMessage(topicName, message));
        verify(kafkaTemplate).send(topicName, message);
    }

    @Test
    void testSendMessage_Failure() {
        String topicName = "test-topic";
        String message = "test-message";

        doThrow(new RuntimeException("Failed to send message."))
            .when(kafkaTemplate).send(topicName, message);

        assertFalse(kafkaProducerService.sendMessage(topicName, message));
        verify(kafkaTemplate).send(topicName, message);
    }
}