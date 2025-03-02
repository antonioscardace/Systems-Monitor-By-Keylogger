package systems.monitor.manager.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.java.Log;
import systems.monitor.manager.entities.Device;
import systems.monitor.manager.repositories.DeviceRepository;
import systems.monitor.manager.requests.DeviceRequest;

// This class is a service component responsible for device-related logic.
// @author Antonio Scardace
// @version 1.0

@Log
@Service
@Transactional
public class DeviceService {
    
    private final DeviceRepository deviceRepository;
    private final RecordService recordService;

    @Autowired
    public DeviceService(DeviceRepository deviceRepository, @Lazy RecordService recordService) {
        this.deviceRepository = deviceRepository;
        this.recordService = recordService;
    }

    public void deleteById(String uuid) {
        this.deviceRepository.deleteById(uuid);
    }

    public Boolean checkIfExistsByUuid(String uuid) {
        return this.deviceRepository.existsById(uuid);
    }

    public Device getByUuid(String uuid) {
        return this.deviceRepository.findByUuid(uuid);
    }

    public List<Device> getAll() {
        return this.deviceRepository.findAll();
    }

    public List<Device> getAllByType(String type) {
        return this.deviceRepository.findByDeviceType(type);
    }

    public List<Device> getAllByKeyboardLayout(String keyboardLayout) {
        return this.deviceRepository.findByKeyboardLayout(keyboardLayout);
    }

    public List<Device> getAllWithRecords() {
        return this.deviceRepository.findAll().stream()
            .filter(device -> this.recordService.checkIfDeviceHasRecordsByUuid(device.getUuid()))
            .toList();
    }

    // Upserts (Inserts or Updates) the device in the database.
    // If the device already exists in the database, it is updated, inserted otherwise.
    // The method returns true if the device is added or updated without errors, false otherwise.

    public Boolean upsert(DeviceRequest data) {
        String uuid = data.getUuid();
        Device device = Optional.ofNullable(this.getByUuid(uuid)).orElse(new Device());

        device.setUuid(data.getUuid());
        device.setCpuDesc(data.getCpuDesc());
        device.setDeviceName(data.getDeviceName());
        device.setDeviceType(data.getDeviceType());
        device.setKeyboardLayout(data.getKeyboardLayout());
        device.setOsName(data.getOsName());
        device.setUsername(data.getUsername());
        device.setRegisteredOn(data.getRegisteredOn());
        device.setLastAccess(data.getRegisteredOn());

        try {
            this.deviceRepository.save(device);
            return true;
        }
        catch (IllegalArgumentException e) {
            log.warning("Failed to upsert device: " + e.getMessage());
            return false;
        }
    }
}