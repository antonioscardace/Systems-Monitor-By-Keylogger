const Logs = require('../models/log');

// This class implements the methods to interact with the "logs" entity.
// Authors: Antonio Scardace

class LogController {

    static async cleanLogsByUUID(uuid) {
        return await Logs.destroy({
            where: { uuid: uuid },
        });
    }

    static async getLogsByUUID(uuid) {
        return await Logs.findAll({
            where: { uuid: uuid },
            order: [['timestamp_begin', 'DESC']]
        });
    }
}

module.exports = LogController