package systems.monitor.server.services;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import systems.monitor.server.entities.Log;
import systems.monitor.server.entities.LogId;
import systems.monitor.server.entities.Device;
import systems.monitor.server.repositories.LogRepository;
import systems.monitor.server.requests.LogRequest;
import systems.monitor.server.utils.Utils;

// This class is a service component responsible for managing operations related to logs.
// @author Antonio Scardace
// @version 1.0

@Service
public class LogService {
    
    private final LogRepository logRepository;
    private final DeviceService deviceService;
    private final KafkaProducerService kafkaProducerService;

    @Autowired
    public LogService(LogRepository logRepository, DeviceService deviceService, KafkaProducerService kafkaProducerService) {
        this.logRepository = logRepository;
        this.deviceService = deviceService;
        this.kafkaProducerService = kafkaProducerService;
    }

    // Send the log metadata to a Kafka Topic named "metadata".
    // The sent message is a JSON string.
    
    public Boolean sendLogToKafka(LogRequest req) {
        Map<String, String> map = new HashMap<>();
        map.put("uuid", req.getUuid());
        map.put("windowName", req.getWindowName());
        map.put("ipAddress", req.getIpAddress());
        map.put("timestampBegin", Utils.datetimeToString(req.getTimestampBegin()));
        map.put("timestampEnd", Utils.datetimeToString(req.getTimestampEnd()));
        return this.kafkaProducerService.sendMessage("metadata", Utils.objectToJson(map));
    }

    // Inserts the new log in the database.
    // The device which sent that log must already exist in the database.
    // The given log must not already exist in the database.
    // If it can be added, the method returns true, false otherwise.

    public Boolean insertLog(LogRequest req) {
        LogId id = new LogId(req.getUuid(), req.getTimestampBegin());
        Optional<Device> device = this.deviceService.getDevice(req.getUuid());

        if (device.isEmpty() ||
            Boolean.TRUE.equals(this.logRepository.existsById(id)))
            return false;

        Log log = new Log(
            id,
            device.get(),
            req.getWindowName(),
            req.getLogText(),
            req.getIpAddress(),
            req.getTimestampEnd()
        );
        
        this.logRepository.save(log);
        return true;
    }
}