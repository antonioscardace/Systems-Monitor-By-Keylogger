package systems.monitor.manager.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.github.javafaker.Faker;

import systems.monitor.manager.crypt.AesCrypt;
import systems.monitor.manager.entities.Device;
import systems.monitor.manager.repositories.DeviceRepository;
import systems.monitor.manager.requests.DeviceRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

// Unit tests for the {@link DeviceService} class.
// The tests examine both successful and unsuccessful scenarios.
// @author Antonio Scardace
// @version 1.0

@ExtendWith(MockitoExtension.class)
class DeviceServiceTest {

    private final Faker faker = new Faker();

    @Mock
    DeviceRepository deviceRepository;

    @Mock
    RecordService recordService;

    @InjectMocks
    DeviceService deviceService;

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
    void testDeleteById_Successful() {
        String uuid = faker.internet().uuid();
        doNothing().when(deviceRepository).deleteById(uuid);
        deviceService.deleteById(uuid);
        verify(deviceRepository, times(1)).deleteById(uuid);
    }

    @Test
    void testCheckIfExistsByUuid_Successful() {
        String uuid = faker.internet().uuid();
        when(deviceRepository.existsById(uuid)).thenReturn(true);
        assertTrue(deviceService.checkIfExistsByUuid(uuid));
        verify(deviceRepository, times(1)).existsById(uuid);
    }

    @Test
    void testGetByUuid_Successful() {
        String uuid = faker.internet().uuid();
        Device device = new Device();
        device.setUuid(uuid);
        when(deviceRepository.findByUuid(uuid)).thenReturn(device);
        assertEquals(device, deviceService.getByUuid(uuid));
        verify(deviceRepository, times(1)).findByUuid(uuid);
    }

    @Test
    void testGetAll_Successful() {
        List<Device> devices = List.of(new Device(), new Device());
        when(deviceRepository.findAll()).thenReturn(devices);
        assertEquals(devices, deviceService.getAll());
        verify(deviceRepository, times(1)).findAll();
    }

    @Test
    void testGetAllByType_Successful() {
        String type = faker.options().option("Laptop", "Desktop", "Mobile");
        List<Device> devices = List.of(new Device(), new Device());
        when(deviceRepository.findByDeviceType(type)).thenReturn(devices);
        assertEquals(devices, deviceService.getAllByType(type));
        verify(deviceRepository, times(1)).findByDeviceType(type);
    }

    @Test
    void testGetAllByKeyboardLayout_Successful() {
        String keyboardLayout = faker.options().option("IT", "EN", "FR");
        List<Device> devices = List.of(new Device(), new Device());
        when(deviceRepository.findByKeyboardLayout(keyboardLayout)).thenReturn(devices);
        assertEquals(devices, deviceService.getAllByKeyboardLayout(keyboardLayout));
        verify(deviceRepository, times(1)).findByKeyboardLayout(keyboardLayout);
    }

    @Test
    void testGetAllWithRecords_Successful() {
        Device[] devices = { new Device(), new Device() };
        devices[0].setUuid(faker.internet().uuid());
        devices[1].setUuid(faker.internet().uuid());
        when(deviceRepository.findAll()).thenReturn(Arrays.asList(devices));
        when(recordService.checkIfDeviceHasRecordsByUuid(devices[0].getUuid())).thenReturn(true);
        when(recordService.checkIfDeviceHasRecordsByUuid(devices[1].getUuid())).thenReturn(false);
        assertEquals(1, deviceService.getAllWithRecords().size());
        verify(deviceRepository, times(1)).findAll();
        verify(recordService, times(2)).checkIfDeviceHasRecordsByUuid(any());
    }

    @Test
    void testUpsertDevice_Successful_Exists() {
        when(deviceService.getByUuid(deviceRequest.getUuid())).thenReturn(new Device());
        assertTrue(deviceService.upsert(deviceRequest));
        verify(deviceRepository, times(1)).save(any());
    }

    @Test
    void testUpsertDevice_Successful_NotExists() {
        when(deviceService.getByUuid(deviceRequest.getUuid())).thenReturn(null);
        assertTrue(deviceService.upsert(deviceRequest));
        verify(deviceRepository, times(1)).save(any());
    }

    @Test
    void testUpsertDevice_Unsuccessful_Exception() {
        when(deviceService.getByUuid(deviceRequest.getUuid())).thenReturn(new Device());
        doThrow(new IllegalArgumentException("")).when(deviceRepository).save(any(Device.class));
        assertFalse(deviceService.upsert(deviceRequest));
        verify(deviceRepository, times(1)).save(any());
    }
}