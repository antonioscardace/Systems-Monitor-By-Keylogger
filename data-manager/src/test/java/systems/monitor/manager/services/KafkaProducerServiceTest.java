package systems.monitor.manager.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.javafaker.Faker;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.HashMap;
import java.util.Map;

// Unit tests for the {@link KafkaProducerService} class.
// The tests examine just the successful scenario.
// @author Antonio Scardace
// @version 1.0

@ExtendWith(MockitoExtension.class)
class KafkaProducerServiceTest {

    private final Faker faker = new Faker();

    @Mock
    KafkaTemplate<String, String> kafkaTemplate;

    @InjectMocks
    KafkaProducerService kafkaProducerService;

    @Test
    void testSendMessage_Text_Successful() {
        String topicName = faker.lorem().word();
        String message = faker.lorem().word();
        kafkaProducerService.sendMessage(topicName, message);
        verify(kafkaTemplate, times(1)).send(topicName, message);
    }

    @Test
    void testSendMessage_Json_Successful() throws JsonProcessingException {
        Map<String, String> object = new HashMap<>();
        object.put("key1", "value1");
        object.put("key2", "value2");

        String expected_message = "{\"key1\":\"value1\",\"key2\":\"value2\"}";
        String topicName = faker.lorem().word();
        kafkaProducerService.sendMessage(topicName, object);
        verify(kafkaTemplate, times(1)).send(topicName, expected_message);
    }
}