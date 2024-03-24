package systems.monitor.server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import systems.monitor.server.requests.DeviceRequest;
import systems.monitor.server.services.DeviceService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

// This class is a REST controller that handles HTTP requests related to the {@link Device} entity.
// It maps endpoints to perform CRUD operations on the {@link Device} entity in the "/api/devices" context path.
// @author Antonio Scardace
// @version 1.0

@RestController
@RequestMapping("/api/devices")
public class DeviceController {
    
    private final DeviceService deviceService;

    @Autowired
    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @PostMapping(path="/")
    public ResponseEntity<Void> insertDevice(@RequestBody DeviceRequest req) {
        return Boolean.TRUE.equals(this.deviceService.insertDevice(req))
            ? ResponseEntity.ok().build()
            : ResponseEntity.status(400).build();
    }
}