const express = require('express');
const router = express.Router();
const LogsController = require('../controllers/logController');

// Returns all the logs of a specific device.
router.get('/:uuid', async (req, res) => {
    const { uuid } = req.params;
    res.json(await LogsController.getLogsByUUID(uuid) || []);
});

// Deletes all the logs of a specific device.
router.delete('/:uuid', async (req, res) => {
    const { uuid } = req.params;
    res.json(await LogsController.cleanLogsByUUID(uuid));
});

module.exports = router;