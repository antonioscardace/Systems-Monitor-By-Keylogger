const express = require('express');
const router = express.Router();
const DevicesController = require('../controllers/deviceController');

// Returns the list of devices with at least one registered log.
router.get('/', async (req, res) => {
    res.json(await DevicesController.getMainInfoList());
});

// Returns the info of a specific device.
router.get('/:uuid', async (req, res) => {
    const { uuid } = req.params;
    res.json(await DevicesController.getInfoByUUID(uuid) || {});
});

// Deletes a specific device.
router.delete('/:uuid', async (req, res) => {
    const { uuid } = req.params;
    res.json(await DevicesController.deleteDeviceByUUID(uuid));
});

module.exports = router;