package systems.monitor.server.services;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import systems.monitor.server.entities.Device;
import systems.monitor.server.repositories.DeviceRepository;
import systems.monitor.server.requests.DeviceRequest;

// This class is a service component responsible for managing operations related to devices.
// @author Antonio Scardace
// @version 1.0

@Service
public class DeviceService {
    
    private final DeviceRepository deviceRepository;

    @Autowired
    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public Optional<Device> getDevice(String uuid) {
        return this.deviceRepository.findById(uuid);
    }

    // Inserts the new device in the database.
    // The given device must not already exist in the database.
    // If it can be added, the method returns true, false otherwise.

    public Boolean insertDevice(DeviceRequest data) {
        if (Boolean.TRUE.equals(this.deviceRepository.existsById(data.getUuid())))
            return false;

        Device device = new Device(
            data.getUuid(),
            data.getCpuDesc(),
            data.getDeviceName(),
            data.getDeviceType(),
            data.getKeyboardLayout(),
            data.getOsName(),
            data.getUsername(),
            data.getRegisteredOn(),
            LocalDateTime.now()
        );

        this.deviceRepository.save(device);
        return true;
    }
}