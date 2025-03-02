package systems.monitor.manager.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.javafaker.Faker;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import systems.monitor.manager.crypt.AesCrypt;
import systems.monitor.manager.entities.Record;
import systems.monitor.manager.entities.RecordId;
import systems.monitor.manager.repositories.RecordRepository;
import systems.monitor.manager.requests.RecordRequest;
import systems.monitor.manager.strategy.MessageSenderStrategy;
import systems.monitor.manager.strategy.RecordInsertionStrategy;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

// Unit tests for the {@link RecordService} class.
// The tests examine both successful and unsuccessful scenarios.
// @author Antonio Scardace
// @version 1.0

@ExtendWith(MockitoExtension.class)
class RecordServiceTest {

    private final Faker faker = new Faker();

    @Mock
    RecordRepository recordRepository;

    @Mock
    DeviceService deviceService;

    @Mock
    MessageSenderStrategy messageSenderStrategy;

    @Mock
    RecordInsertionStrategy recordInsertionStrategy;

    @InjectMocks
    RecordService recordService;

    RecordRequest recordRequest = new RecordRequest(
        AesCrypt.encrypt(faker.internet().uuid()),
        AesCrypt.encrypt(faker.internet().publicIpV4Address()),
        AesCrypt.encrypt(faker.lorem().sentence()),
        AesCrypt.encrypt(faker.lorem().word()),
        AesCrypt.encrypt("2024-01-01 12:30:00"),
        AesCrypt.encrypt("2024-01-01 12:30:30")
    );

    @Test
    void testDeleteAllByUuid_Successful() {
        String uuid = faker.internet().uuid();
        doNothing().when(recordRepository).deleteAllByIdUuid(uuid);
        recordService.deleteAllByUuid(uuid);
        verify(recordRepository, times(1)).deleteAllByIdUuid(uuid);
    }

    @Test
    void testCheckIfExistsById_Successful() {
        String uuid = faker.internet().uuid();
        LocalDateTime timestampBegin = LocalDateTime.now();
        RecordId id = new RecordId(uuid, timestampBegin);
        when(recordRepository.existsById(id)).thenReturn(true);
        assertTrue(recordService.checkIfExistsById(id));
        verify(recordRepository, times(1)).existsById(id);
    }

    @Test
    void testCheckIfDeviceHasRecordsByUuid_Successful_True() {
        String uuid = faker.internet().uuid();
        when(recordRepository.findByIdUuid(uuid)).thenReturn(List.of(new Record()));
        assertTrue(recordService.checkIfDeviceHasRecordsByUuid(uuid));
    }

    @Test
    void testCheckIfDeviceHasRecordsByUuid_Successful_False() {
        String uuid = faker.internet().uuid();
        when(recordRepository.findByIdUuid(uuid)).thenReturn(Collections.emptyList());
        assertFalse(recordService.checkIfDeviceHasRecordsByUuid(uuid));
    }
    
    @Test
    void testGetAll_Successful() {
        List<Record> records = List.of(new Record(), new Record());
        when(recordRepository.findAll()).thenReturn(records);
        assertEquals(2, recordService.getAll().size());
        verify(recordRepository, times(1)).findAll();
    }

    @Test
    void testGetAllByUuid_Successful() {
        String uuid = faker.internet().uuid();
        List<Record> records = List.of(new Record());
        when(recordRepository.findByIdUuid(uuid)).thenReturn(records);
        assertEquals(1, recordService.getAllByUuid(uuid).size());
        verify(recordRepository, times(1)).findByIdUuid(uuid);
    }

    @Test
    void testGetAllByIpAddress_Successful() {
        String ipAddress = faker.internet().publicIpV4Address();
        List<Record> records = List.of(new Record());
        when(recordRepository.findByIpAddress(ipAddress)).thenReturn(records);
        assertEquals(1, recordService.getAllByIpAddress(ipAddress).size());
        verify(recordRepository, times(1)).findByIpAddress(ipAddress);
    }

    @Test
    void testProcessRecord_Successful() throws JsonProcessingException {
        when(deviceService.checkIfExistsByUuid(recordRequest.getUuid())).thenReturn(true);
        when(recordService.checkIfExistsById(any())).thenReturn(false);
        doNothing().when(messageSenderStrategy).sendMessage(recordRequest);
        doNothing().when(recordInsertionStrategy).insertRecord(recordRequest);
        assertTrue(recordService.process(recordRequest));
        verify(messageSenderStrategy, times(1)).sendMessage(recordRequest);
        verify(recordInsertionStrategy, times(1)).insertRecord(recordRequest);
    }

    @Test
    void testProcessRecord_Unsuccessful_DeviceNotExists() {
        when(deviceService.checkIfExistsByUuid(recordRequest.getUuid())).thenReturn(false);
        assertFalse(recordService.process(recordRequest));
        verify(recordRepository, never()).save(any(Record.class));
    }

    @Test
    void testProcessRecord_Unsuccessful_RecordAlreadyExists() {
        when(deviceService.checkIfExistsByUuid(recordRequest.getUuid())).thenReturn(true);
        when(recordService.checkIfExistsById(any())).thenReturn(true);
        assertFalse(recordService.process(recordRequest));
        verify(recordRepository, never()).save(any(Record.class));
    }
}