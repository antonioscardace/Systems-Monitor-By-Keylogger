const express = require('express');
const fs = require('fs');
const https = require('https');
const body_parser = require('body-parser');

const deviceRoutes = require('./routes/deviceRoutes');
const logRoutes = require('./routes/logRoutes');

const app = express();
app.use(body_parser.json());
app.use(body_parser.urlencoded({ extended: true }));
app.use(express.static('client'));
app.disable('x-powered-by');

let options = {
    key: fs.readFileSync('certs/key.pem'),
    cert: fs.readFileSync('certs/cert.pem')
};
https.createServer(options, app).listen(443);

app.use('/api/devices', deviceRoutes);
app.use('/api/logs', logRoutes);