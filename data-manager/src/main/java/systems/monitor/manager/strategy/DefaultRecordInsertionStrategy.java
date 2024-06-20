package systems.monitor.manager.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import systems.monitor.manager.entities.Device;
import systems.monitor.manager.entities.Record;
import systems.monitor.manager.entities.RecordId;
import systems.monitor.manager.repositories.RecordRepository;
import systems.monitor.manager.requests.RecordRequest;
import systems.monitor.manager.services.DeviceService;

// Concrete Strategy that implements the interface for inserting a record into the database.
// @author Antonio Scardace
// @version 1.0

@Service
public class DefaultRecordInsertionStrategy implements RecordInsertionStrategy {

    private final RecordRepository recordRepository;
    private final DeviceService deviceService;

    @Autowired
    public DefaultRecordInsertionStrategy(RecordRepository recordRepository, DeviceService deviceService) {
        this.recordRepository = recordRepository;
        this.deviceService = deviceService;
    }

    @Override
    public void insertRecord(RecordRequest req) throws IllegalArgumentException {
        Device device = deviceService.getByUuid(req.getUuid());
        RecordId id = new RecordId(req.getUuid(), req.getTimestampBegin());
        Record newRecord = new Record();
        
        newRecord.setId(id);
        newRecord.setDevice(device);
        newRecord.setWindowTitle(req.getWindowTitle());
        newRecord.setRecordText(req.getRecordText());
        newRecord.setIpAddress(req.getIpAddress());
        newRecord.setTimestampEnd(req.getTimestampEnd());
        recordRepository.save(newRecord);
    }
}