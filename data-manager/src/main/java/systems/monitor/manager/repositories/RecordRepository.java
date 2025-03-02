package systems.monitor.manager.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import systems.monitor.manager.entities.Record;
import systems.monitor.manager.entities.RecordId;

// This interface defines the "RecordRepository", which extends "JpaRepository" interface.
// JpaRepository is a Spring Data interface that provides CRUD operations for the {@link Record} entity.
// It helps in defining auto-generated SQL queries by the names of the methods.
// @author Antonio Scardace
// @version 1.0

public interface RecordRepository extends JpaRepository<Record, RecordId> {
    List<Record> findByIdUuid(String uuid);
    List<Record> findByIpAddress(String ipAddress);
    void deleteAllByIdUuid(String uuid);
}