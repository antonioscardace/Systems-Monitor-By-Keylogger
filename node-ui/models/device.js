const { DataTypes } = require('sequelize');
const sequelize = require('../config/database');

// This class represents the "devices" entity in the database.
// Authors: Antonio Scardace

const Device = sequelize.define('devices', {
    uuid: {
        type: DataTypes.STRING(36),
        allowNull: false,
        primaryKey: true
    },
    registered_on: {
        type: DataTypes.DATE,
        allowNull: false,
    },
    last_access: {
        type: DataTypes.DATE,
        allowNull: false
    },
    cpu_desc: {
        type: DataTypes.STRING(256),
        allowNull: false
    },
    device_name: {
        type: DataTypes.STRING(256),
        allowNull: false
    },
    username: {
        type: DataTypes.STRING(256),
        allowNull: false
    },
    device_type: {
        type: DataTypes.STRING(16),
        allowNull: false
    },
    os_name: {
        type: DataTypes.STRING(32),
        allowNull: false
    },
    keyboard_layout: {
        type: DataTypes.STRING(3),
        allowNull: false
    }
});

module.exports = Device