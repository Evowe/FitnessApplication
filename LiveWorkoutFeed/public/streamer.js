const localFeed = document.getElementById('localFeed');
const ws = new WebSocket('wss://2892-129-62-66-43.ngrok-free.app');
let peerConnection;
let localStream;

ws.onopen = () => {
    ws.send(JSON.stringify({ type: 'role', role: 'streamer' }));
    console.log('Connected to signaling server (streamer)');

    startLocalVideo();
};

ws.onmessage = async (message) => {
    const data = JSON.parse(message.data);

    if (data.type === 'viewer-ready') {
        console.log('Viewer is ready, starting WebRTC connection...');
        startWebRTC(localStream);
    } else if (data.type === 'answer') {
        await peerConnection.setRemoteDescription(new RTCSessionDescription(data.answer));
        console.log('Streamer: remote description set');
    } else if (data.type === 'candidate') {
        await peerConnection.addIceCandidate(new RTCIceCandidate(data.candidate));
        console.log('Streamer: ICE candidate added');
    }
};

async function startLocalVideo() {
    try {
        localStream = await navigator.mediaDevices.getUserMedia({ video: true, audio: true });
        localFeed.srcObject = localStream;
        console.log('Local video stream started');
    } catch (error) {
        console.error('Error accessing media devices.', error);
    }
}

async function startWebRTC(stream) {
    peerConnection = new RTCPeerConnection({
        iceServers: [{ urls: 'stun:stun.l.google.com:19302' }]
    });

    peerConnection.onicecandidate = event => {
        if (event.candidate) {
            ws.send(JSON.stringify({ type: 'candidate', candidate: event.candidate }));
        }
    };

    peerConnection.oniceconnectionstatechange = () => {
        console.log('Streamer ICE state:', peerConnection.iceConnectionState);
    };

    stream.getTracks().forEach(track => {
        console.log('Adding track:', track.kind);
        peerConnection.addTrack(track, stream);
    });

    const offer = await peerConnection.createOffer();
    await peerConnection.setLocalDescription(offer);

    ws.send(JSON.stringify({ type: 'offer', offer }));
    console.log('Streamer sent offer');
}