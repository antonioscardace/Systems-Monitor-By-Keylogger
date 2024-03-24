package systems.monitor.server.services;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import systems.monitor.server.repositories.DeviceRepository;
import systems.monitor.server.requests.DeviceRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

// Unit tests for the {@link DeviceService} class.
// The tests examine both successful and unsuccessful scenarios.
// @author Antonio Scardace
// @version 1.0

@SpringBootTest
class DeviceServiceTest {

    @Mock
    DeviceRepository deviceRepository;

    @InjectMocks
    DeviceService deviceService;

    DeviceRequest deviceRequest = new DeviceRequest(
        AesCryptService.encrypt("xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx"),
        AesCryptService.encrypt("dummy cpu description"),
        AesCryptService.encrypt("dummy device name"),
        AesCryptService.encrypt("dummy device type"),
        AesCryptService.encrypt("IT"),
        AesCryptService.encrypt("dummy operating system name"),
        AesCryptService.encrypt("dummy username"),
        AesCryptService.encrypt("2024-01-01 12:00:00")
    );

    @Test
    void testInsertDevice_Successful() {
        when(deviceRepository.existsById("xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx")).thenReturn(false);
        assertTrue(deviceService.insertDevice(deviceRequest));
        verify(deviceRepository).save(any());
    }

    @Test
    void testInsertDevice_Unsuccessful() {
        when(deviceRepository.existsById("xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx")).thenReturn(true);
        assertFalse(deviceService.insertDevice(deviceRequest));
        verify(deviceRepository, never()).save(any());
    }
}