package systems.monitor.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import systems.monitor.server.entities.Log;
import systems.monitor.server.entities.LogId;

// This interface defines the "LogRepository", which extends "JpaRepository" interface.
// JpaRepository is a Spring Data interface that provides CRUD operations for the {@link Log} entity.
// It helps in defining auto-generated SQL queries by the names of the methods.
// @author Antonio Scardace
// @version 1.0

public interface LogRepository extends JpaRepository<Log, LogId> {
    
}