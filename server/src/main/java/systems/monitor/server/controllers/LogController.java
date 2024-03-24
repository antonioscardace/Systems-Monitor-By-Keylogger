package systems.monitor.server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import systems.monitor.server.requests.LogRequest;
import systems.monitor.server.services.LogService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

// This class is a REST controller that handles HTTP requests related to the {@link Log} entity.
// It maps endpoints to perform CRUD operations on the {@link Log} entity in the "/api/logs" context path.
// @author Antonio Scardace
// @version 1.0

@RestController
@RequestMapping("/api/logs")
public class LogController {
    
    private final LogService logService;

    @Autowired
    public LogController(LogService logService) {
        this.logService = logService;
    }

    @PostMapping(path="/")
    public ResponseEntity<Void> insertLog(@RequestBody LogRequest req) {
        return Boolean.TRUE.equals(this.logService.insertLog(req)) && Boolean.TRUE.equals(this.logService.sendLogToKafka(req))
            ? ResponseEntity.ok().build()
            : ResponseEntity.status(400).build();
    }
}