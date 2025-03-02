package systems.monitor.manager.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// This class represents the "devices" entity in the database.
// The Primary Key is the "uuid" field.
// @author Antonio Scardace
// @version 1.0

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "devices")
public class Device {

    @Id
    @Column(name = "uuid", nullable = false)
    private String uuid;

    @Column(name = "cpu_desc", nullable = false)
    private String cpuDesc;

    @Column(name = "device_name", nullable = false)
    private String deviceName;

    @Column(name = "device_type", nullable = false)
    private String deviceType;

    @Column(name = "keyboard_layout", nullable = false)
    private String keyboardLayout;

    @Column(name = "os_name", nullable = false)
    private String osName;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "registered_on", nullable = false)
    private LocalDateTime registeredOn;

    @Column(name = "last_access", nullable = false)
    private LocalDateTime lastAccess;
}