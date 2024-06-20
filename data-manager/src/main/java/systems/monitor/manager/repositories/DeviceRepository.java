package systems.monitor.manager.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import systems.monitor.manager.entities.Device;

// This interface defines the "DeviceRepository", which extends "JpaRepository" interface.
// JpaRepository is a Spring Data interface that provides CRUD operations for the {@link Device} entity.
// It helps in defining auto-generated SQL queries by the names of the methods.
// @author Antonio Scardace
// @version 1.0

public interface DeviceRepository extends JpaRepository<Device, String> {
    Device findByUuid(String uuid);
    List<Device> findByDeviceType(String deviceType);
    List<Device> findByKeyboardLayout(String keyboardLayout);
}