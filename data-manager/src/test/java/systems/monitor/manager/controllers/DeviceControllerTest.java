package systems.monitor.manager.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.github.javafaker.Faker;

import systems.monitor.manager.crypt.AesCrypt;
import systems.monitor.manager.entities.Device;
import systems.monitor.manager.requests.DeviceRequest;
import systems.monitor.manager.services.DeviceService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

// Unit tests for the {@link DeviceController} class.
// The tests examine both successful and unsuccessful scenarios.
// @author Antonio Scardace
// @version 1.0

@ExtendWith(MockitoExtension.class)
class DeviceControllerTest {

    private final Faker faker = new Faker();

    @Mock
    private DeviceService deviceService;

    @InjectMocks
    private DeviceController deviceController;

    DeviceRequest deviceRequest = new DeviceRequest(
        AesCrypt.encrypt(faker.internet().uuid()),
        AesCrypt.encrypt(faker.lorem().word()),
        AesCrypt.encrypt(faker.lorem().word()),
        AesCrypt.encrypt(faker.options().option("Laptop", "Desktop", "Mobile")),
        AesCrypt.encrypt(faker.options().option("IT", "EN", "FR")),
        AesCrypt.encrypt(faker.lorem().word()),
        AesCrypt.encrypt(faker.name().username()),
        AesCrypt.encrypt("2024-01-01 12:00:00")
    );

    @Test
    void testDeleteDeviceByUuid_Successful() {
        String uuid = faker.internet().uuid();
        deviceController.deleteDeviceByUuid(uuid);
        verify(deviceService, times(1)).deleteById(uuid);
    }

    @Test
    void testCheckIfDeviceExists_Successful() {
        String uuid = faker.internet().uuid();
        when(deviceService.checkIfExistsByUuid(uuid)).thenReturn(true);
        assertTrue(deviceController.checkIfDeviceExistsByUuid(uuid));
        verify(deviceService, times(1)).checkIfExistsByUuid(uuid);
    }

    @Test
    void testGetDeviceByUuid_Successful() {
        String uuid = faker.internet().uuid();
        Device device = new Device();
        device.setUuid(uuid);
        when(deviceService.getByUuid(uuid)).thenReturn(device);
        assertEquals(uuid, deviceController.getDeviceByUuid(uuid).getUuid());
        verify(deviceService, times(1)).getByUuid(uuid);
    }

    @Test
    void testGetAllDevices_Successful_WithRecordsEqualsToTrue() {
        List<Device> devices = List.of(new Device(), new Device());
        when(deviceService.getAllWithRecords()).thenReturn(devices);
        assertEquals(2, deviceController.getAllDevices(true).size());
        verify(deviceService, never()).getAll();
        verify(deviceService, times(1)).getAllWithRecords();
    }

    @Test
    void testGetAllDevices_Successful_WithRecordsEqualsToFalse() {
        List<Device> devices = List.of(new Device(), new Device());
        when(deviceService.getAll()).thenReturn(devices);
        assertEquals(2, deviceController.getAllDevices(false).size());
        verify(deviceService, times(1)).getAll();
        verify(deviceService, never()).getAllWithRecords();
    }

    @Test
    void testGetAllDevices_Successful_WithRecordsEqualsToNull() {
        List<Device> devices = List.of(new Device(), new Device());
        when(deviceService.getAll()).thenReturn(devices);
        assertEquals(2, deviceController.getAllDevices(null).size());
        verify(deviceService, times(1)).getAll();
        verify(deviceService, never()).getAllWithRecords();
    }

    @Test
    void testUpsertDevice_Successful() {
        when(deviceService.upsert(deviceRequest)).thenReturn(true);
        ResponseEntity<String> response = deviceController.upsertDevice(deviceRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Device upserted successfully.", response.getBody());
    }

    @Test
    void testUpsertDevice_Unsuccessful() {
        when(deviceService.upsert(deviceRequest)).thenReturn(false);
        ResponseEntity<String> response = deviceController.upsertDevice(deviceRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Error in upserting the device.", response.getBody());
    }
}