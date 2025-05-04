const express = require('express');
const http = require('http');
const WebSocket = require('ws');
const path = require('path');

const app = express();
const server = http.createServer(app);
const wss = new WebSocket.Server({ server });

let streamer = null;
let viewer = null;

app.use(express.static(path.join(__dirname, 'public')));

wss.on('connection', (ws) => {
    console.log('Client connected');

    ws.on('message', (message) => {
        const data = JSON.parse(message);

        if (data.type === 'role') {
            if (data.role === 'streamer') {
                streamer = ws;
                console.log('Registered streamer');
                if (viewer && viewer.readyState === WebSocket.OPEN) {
                    streamer.send(JSON.stringify({ type: 'viewer-ready' }));
                }
            } else if (data.role === 'viewer') {
                viewer = ws;
                console.log('Registered viewer');
                if (streamer && streamer.readyState === WebSocket.OPEN) {
                    streamer.send(JSON.stringify({ type: 'viewer-ready' }));
                }
            }
        } else if (data.type === 'offer' && viewer) {
            viewer.send(JSON.stringify(data));
        } else if (data.type === 'answer' && streamer) {
            streamer.send(JSON.stringify(data));
        } else if (data.type === 'candidate') {
            if (ws === streamer && viewer) {
                viewer.send(JSON.stringify(data));
            } else if (ws === viewer && streamer) {
                streamer.send(JSON.stringify(data));
            }
        }
    });

    ws.on('close', () => {
        if (ws === streamer) streamer = null;
        if (ws === viewer) viewer = null;
        console.log('Client disconnected');
    });
});

server.listen(8080, () => {
    console.log('Server running on http://localhost:8080');
});
