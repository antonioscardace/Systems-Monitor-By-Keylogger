package systems.monitor.manager.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import systems.monitor.manager.entities.Record;
import systems.monitor.manager.requests.RecordRequest;
import systems.monitor.manager.services.RecordService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

// This class is a REST controller that handles HTTP requests related to the {@link Record} entity.
// It maps endpoints to perform CRUD operations on the {@link Record} entity in the "/api/v1/records" context path.
// @author Antonio Scardace
// @version 1.0

@RestController
@RequestMapping("/api/v1/records")
public class RecordController {
    
    private final RecordService recordService;

    @Autowired
    public RecordController(RecordService recordService) {
        this.recordService = recordService;
    }

    @DeleteMapping(path="/{uuid}")
    public void deleteAllRecordsByDeviceUuid(@PathVariable String uuid) {
        this.recordService.deleteAllByUuid(uuid);
    }

    @GetMapping(path="/check/{uuid}")
    public Boolean checkIfDeviceHasRecordsByUuid(@PathVariable String uuid) {
        return this.recordService.checkIfDeviceHasRecordsByUuid(uuid);
    }

    @GetMapping(path="/")
    public List<Record> getAllRecords(@RequestParam(value="uuid", required=false) String uuid) {
        return uuid == null
            ? this.recordService.getAll()
            : this.recordService.getAllByUuid(uuid);
    }

    @PostMapping(path="/")
    public ResponseEntity<String> insertRecord(@RequestBody RecordRequest req) {
        return Boolean.TRUE.equals(this.recordService.process(req))
            ? ResponseEntity.ok().body("Record saved successfully.")
            : ResponseEntity.status(400).body("Error in saving the record.");
    }
}