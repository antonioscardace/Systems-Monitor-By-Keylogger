package systems.monitor.manager.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.java.Log;
import systems.monitor.manager.entities.Record;
import systems.monitor.manager.entities.RecordId;
import systems.monitor.manager.repositories.RecordRepository;
import systems.monitor.manager.requests.RecordRequest;
import systems.monitor.manager.strategy.MessageSenderStrategy;
import systems.monitor.manager.strategy.RecordInsertionStrategy;

// This class is a service component responsible for record-related logic.
// It is the Context class of the Strategy Design Pattern.
// @author Antonio Scardace
// @version 1.0

@Log
@Service
@Transactional
public class RecordService {
    
    private final RecordRepository recordRepository;
    private final DeviceService deviceService;
    private final MessageSenderStrategy messageSenderStrategy;
    private final RecordInsertionStrategy recordInsertionStrategy;

    @Autowired
    public RecordService(RecordRepository recordRepository, DeviceService deviceService, MessageSenderStrategy messageSenderStrategy, RecordInsertionStrategy recordInsertionStrategy) {
        this.recordRepository = recordRepository;
        this.deviceService = deviceService;
        this.messageSenderStrategy = messageSenderStrategy;
        this.recordInsertionStrategy = recordInsertionStrategy;
    }

    public void deleteAllByUuid(String uuid) {
        this.recordRepository.deleteAllByIdUuid(uuid);
    }

    public Boolean checkIfExistsById(RecordId id) {
        return this.recordRepository.existsById(id);
    }

    public Boolean checkIfDeviceHasRecordsByUuid(String uuid) {
        return Boolean.FALSE.equals(this.getAllByUuid(uuid).isEmpty());
    }

    public List<Record> getAll() {
        return this.recordRepository.findAll();
    }

    public List<Record> getAllByUuid(String uuid) {
        return this.recordRepository.findByIdUuid(uuid);
    }

    public List<Record> getAllByIpAddress(String ipAddress) {
        return this.recordRepository.findByIpAddress(ipAddress);
    }

    // Processes the given record request.
    // The device which sent that record must already exist in the database.
    // The given record must not already exist in the database.
    // The method returns true if the record is added and sent without errors, false otherwise.

    public Boolean process(RecordRequest req) {
        String uuid = req.getUuid();
        LocalDateTime timestampBegin = req.getTimestampBegin();
        RecordId id = new RecordId(uuid, timestampBegin);
        
        if (Boolean.FALSE.equals(this.deviceService.checkIfExistsByUuid(uuid)) ||
            Boolean.TRUE.equals(this.checkIfExistsById(id)))
            return false;

        try {
            this.recordInsertionStrategy.insertRecord(req);
            this.messageSenderStrategy.sendMessage(req);
            return true;
        }
        catch (Exception e) {
            log.warning("Failed to send-save the record: " + e.getMessage());
            return false;
        }
    }
}