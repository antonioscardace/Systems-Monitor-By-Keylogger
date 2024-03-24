package systems.monitor.server.services;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import systems.monitor.server.entities.Device;
import systems.monitor.server.entities.Log;
import systems.monitor.server.entities.LogId;
import systems.monitor.server.repositories.LogRepository;
import systems.monitor.server.requests.LogRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

// Unit tests for the {@link LogService} class.
// The tests examine both successful and unsuccessful scenarios.
// @author Antonio Scardace
// @version 1.0

@SpringBootTest
class LogServiceTest {

    @Mock
    LogRepository logRepository;

    @Mock
    DeviceService deviceService;

    @Mock
    KafkaProducerService kafkaProducerService;

    @InjectMocks
    LogService logService;

    LogRequest logRequest = new LogRequest(
        AesCryptService.encrypt("xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx"),
        AesCryptService.encrypt("8.8.8.8"),
        AesCryptService.encrypt("dummy text"),
        AesCryptService.encrypt("dummy window name"),
        AesCryptService.encrypt("2024-01-01 12:30:00"),
        AesCryptService.encrypt("2024-01-01 12:30:30")
    );

    @Test
    void testInsertLog_Successful() {
        when(deviceService.getDevice("xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx")).thenReturn(Optional.of(new Device()));
        when(logRepository.existsById(any(LogId.class))).thenReturn(false);
        assertTrue(logService.insertLog(logRequest));
        verify(logRepository).save(any(Log.class));
    }

    @Test
    void testInsertLog_Unsuccessful_DeviceNotFound() {
        when(deviceService.getDevice("xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx")).thenReturn(Optional.empty());
        assertFalse(logService.insertLog(logRequest));
        verify(logRepository, never()).save(any(Log.class));
    }

    @Test
    void testInsertLog_Unsuccessful_LogAlreadyExists() {
        when(deviceService.getDevice("xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx")).thenReturn(Optional.of(new Device()));
        when(logRepository.existsById(any(LogId.class))).thenReturn(true);
        assertFalse(logService.insertLog(logRequest));
        verify(logRepository, never()).save(any(Log.class));
    }
}