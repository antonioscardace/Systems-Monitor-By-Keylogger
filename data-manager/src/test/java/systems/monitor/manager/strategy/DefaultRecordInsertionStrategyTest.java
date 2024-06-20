package systems.monitor.manager.strategy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.github.javafaker.Faker;

import systems.monitor.manager.crypt.AesCrypt;
import systems.monitor.manager.entities.Device;
import systems.monitor.manager.repositories.RecordRepository;
import systems.monitor.manager.requests.RecordRequest;
import systems.monitor.manager.services.DeviceService;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

// Unit tests for the {@link DefaultRecordInsertionStrategy} class.
// The tests examine just successful scenarios.
// @author Antonio Scardace
// @version 1.0

@ExtendWith(MockitoExtension.class)
class DefaultRecordInsertionStrategyTest {

    private final Faker faker = new Faker();

    @Mock
    private RecordRepository recordRepository;

    @Mock
    private DeviceService deviceService;

    @InjectMocks
    DefaultRecordInsertionStrategy defaultRecordInsertionStrategy;

    RecordRequest recordRequest = new RecordRequest(
        AesCrypt.encrypt(faker.internet().uuid()),
        AesCrypt.encrypt(faker.internet().publicIpV4Address()),
        AesCrypt.encrypt(faker.lorem().sentence()),
        AesCrypt.encrypt(faker.lorem().word()),
        AesCrypt.encrypt("2024-01-01 12:30:00"),
        AesCrypt.encrypt("2024-01-01 12:30:30")
    );

    @Test
    void testInsertRecord_Successful() throws IllegalArgumentException {
        when(deviceService.getByUuid(recordRequest.getUuid())).thenReturn(new Device());
        assertDoesNotThrow(() -> defaultRecordInsertionStrategy.insertRecord(recordRequest));
        verify(recordRepository, times(1)).save(any());
    }
}