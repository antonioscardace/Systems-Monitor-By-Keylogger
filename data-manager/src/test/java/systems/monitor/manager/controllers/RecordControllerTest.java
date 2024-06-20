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
import systems.monitor.manager.entities.Record;
import systems.monitor.manager.requests.RecordRequest;
import systems.monitor.manager.services.RecordService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

// Unit tests for the {@link RecordController} class.
// The tests examine both successful and unsuccessful scenarios.
// @author Antonio Scardace
// @version 1.0

@ExtendWith(MockitoExtension.class)
class RecordControllerTest {

    private final Faker faker = new Faker();

    @Mock
    private RecordService recordService;

    @InjectMocks
    private RecordController recordController;

    RecordRequest recordRequest = new RecordRequest(
        AesCrypt.encrypt(faker.internet().uuid()),
        AesCrypt.encrypt(faker.internet().publicIpV4Address()),
        AesCrypt.encrypt(faker.lorem().sentence()),
        AesCrypt.encrypt(faker.lorem().word()),
        AesCrypt.encrypt("2024-01-01 12:30:00"),
        AesCrypt.encrypt("2024-01-01 12:30:30")
    );

    @Test
    void testDeleteDeviceByUuid_Successful() {
        String uuid = faker.internet().uuid();
        recordController.deleteAllRecordsByDeviceUuid(uuid);
        verify(recordService, times(1)).deleteAllByUuid(uuid);
    }

    @Test
    void testCheckIfDeviceHasRecordsByUuid_Successful() {
        String uuid = faker.internet().uuid();
        when(recordService.checkIfDeviceHasRecordsByUuid(uuid)).thenReturn(true);
        assertTrue(recordController.checkIfDeviceHasRecordsByUuid(uuid));
        verify(recordService, times(1)).checkIfDeviceHasRecordsByUuid(uuid);
    }
    
    @Test
    void testGetAllRecords_Successful_WithUuidEqualsToNull() {
        List<Record> records = List.of(new Record(), new Record());
        when(recordService.getAll()).thenReturn(records);
        assertEquals(2, recordController.getAllRecords(null).size());
        verify(recordService, times(1)).getAll();
        verify(recordService, never()).getAllByUuid(anyString());
    }

    @Test
    void testGetAllRecordsByUuid_Successful_WithValidUuid() {
        String uuid = faker.internet().uuid();
        List<Record> records = List.of(new Record(), new Record());
        when(recordService.getAllByUuid(uuid)).thenReturn(records);
        assertEquals(2, recordController.getAllRecords(uuid).size());
        verify(recordService, never()).getAll();
        verify(recordService, times(1)).getAllByUuid(anyString());
    }

    @Test
    void testInsertRecord_Successful() {
        when(recordService.process(recordRequest)).thenReturn(true);
        ResponseEntity<String> response = recordController.insertRecord(recordRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Record saved successfully.", response.getBody());
    }

    @Test
    void testInsertRecord_Unsuccessful() {
        when(recordService.process(recordRequest)).thenReturn(false);
        ResponseEntity<String> response = recordController.insertRecord(recordRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Error in saving the record.", response.getBody());
    }
}
