package systems.monitor.manager.strategy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.javafaker.Faker;

import systems.monitor.manager.crypt.AesCrypt;
import systems.monitor.manager.requests.RecordRequest;
import systems.monitor.manager.services.KafkaProducerService;

import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

// Unit tests for the {@link KafkaMessageSenderStrategy} class.
// The tests examine just successful scenarios.
// @author Antonio Scardace
// @version 1.0

@ExtendWith(MockitoExtension.class)
class KafkaMessageSenderStrategyTest {

    private final Faker faker = new Faker();

    @Mock
    private KafkaProducerService kafkaProducerService;

    @InjectMocks
    KafkaMessageSenderStrategy kafkaMessageSenderStrategy;

    RecordRequest recordRequest = new RecordRequest(
        AesCrypt.encrypt(faker.internet().uuid()),
        AesCrypt.encrypt(faker.internet().publicIpV4Address()),
        AesCrypt.encrypt(faker.lorem().sentence()),
        AesCrypt.encrypt(faker.lorem().word()),
        AesCrypt.encrypt("2024-01-01 12:30:00"),
        AesCrypt.encrypt("2024-01-01 12:30:30")
    );

    @Test
    void testSendMessage_Successful() throws JsonProcessingException {
        doNothing().when(kafkaProducerService).sendMessage(anyString(), anyMap());
        kafkaMessageSenderStrategy.sendMessage(recordRequest);
        verify(kafkaProducerService, times(1)).sendMessage(anyString(), anyMap());
    }
}