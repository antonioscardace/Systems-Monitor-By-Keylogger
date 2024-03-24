const Sequelize = require('sequelize');
const Devices = require('../models/device');
const sequelize = require('../config/database');

// This class implements the methods to interact with the "devices" entity.
// Authors: Antonio Scardace

class DeviceController {

    static async deleteDeviceByUUID(uuid) {
        return await Devices.destroy({
            where: { uuid: uuid },
        });
    }
    
    static async getUUIDs() {
        return await Devices.findAll({
            attributes: ['uuid']
        });
    }

    static async getInfoByUUID(uuid) {
        return await Devices.findOne({
            where: { uuid: uuid }
        });
    }

    static async getMainInfoList() {
        return await sequelize.query(
            'SELECT d.uuid, d.registered_on, d.device_name, d.username, d.last_access, l.ip_address ' +
            'FROM devices d JOIN logs l ON d.uuid = l.uuid AND d.last_access = l.timestamp_begin',
            { type: Sequelize.QueryTypes.SELECT }
        );
    }
}

module.exports = DeviceController