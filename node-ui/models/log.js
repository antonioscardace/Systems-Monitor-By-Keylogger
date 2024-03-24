const { DataTypes } = require('sequelize');
const sequelize = require('../config/database');
const Devices = require('./device');

// This class represents the "logs" entity in the database.
// Authors: Antonio Scardace

const Log = sequelize.define('logs', {
    uuid: {
        type: DataTypes.STRING(36),
        allowNull: false,
        primaryKey: true,
        references: {
            model: Devices,
            key: 'uuid',
        }
    },
    timestamp_begin: {
        type: DataTypes.DATE,
        allowNull: false,
        primaryKey: true
    },
    timestamp_end: {
        type: DataTypes.DATE,
        allowNull: false
    },
    window_name: {
        type: DataTypes.STRING(512),
        allowNull: false
    },
    log_text: {
        type: DataTypes.TEXT,
        allowNull: false
    },
    ip_address: {
        type: DataTypes.STRING(16),
        allowNull: false
    }
});

Log.belongsTo(Devices, {foreignKey: 'uuid'});

module.exports = Log