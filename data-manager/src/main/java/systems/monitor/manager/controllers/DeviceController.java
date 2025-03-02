package systems.monitor.manager.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import systems.monitor.manager.entities.Device;
import systems.monitor.manager.requests.DeviceRequest;
import systems.monitor.manager.services.DeviceService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

// This class is a REST controller that handles HTTP requests related to the {@link Device} entity.
// It maps endpoints to perform CRUD operations on the {@link Device} entity in the "/api/v1/devices" context path.
// @author Antonio Scardace
// @version 1.0

@RestController
@RequestMapping("/api/v1/devices")
public class DeviceController {
    
    private final DeviceService deviceService;

    @Autowired
    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @DeleteMapping(path="/{uuid}")
    public void deleteDeviceByUuid(@PathVariable String uuid) {
        this.deviceService.deleteById(uuid);
    }

    @GetMapping(path="/check/{uuid}")
    public Boolean checkIfDeviceExistsByUuid(@PathVariable String uuid) {
        return this.deviceService.checkIfExistsByUuid(uuid);
    }
    
    @GetMapping(path="/{uuid}")
    public Device getDeviceByUuid(@PathVariable String uuid) {
        return this.deviceService.getByUuid(uuid);
    }

    @GetMapping(path="/")
    public List<Device> getAllDevices(@RequestParam(value="withRecords", required=false) Boolean withRecords) {
        return Boolean.TRUE.equals(withRecords) 
            ? this.deviceService.getAllWithRecords()
            : this.deviceService.getAll();
    }

    @PostMapping(path="/")
    public ResponseEntity<String> upsertDevice(@RequestBody DeviceRequest req) {
        return Boolean.TRUE.equals(this.deviceService.upsert(req))
            ? ResponseEntity.ok().body("Device upserted successfully.")
            : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error in upserting the device.");
    }
}