const remoteFeed = document.getElementById('remoteFeed');
const ws = new WebSocket('wss://2892-129-62-66-43.ngrok-free.app');
let peerConnection;

ws.onopen = () => {
    console.log('WebSocket connected (viewer)');
    ws.send(JSON.stringify({ type: 'role', role: 'viewer' }));
};

ws.onmessage = async (event) => {
    const data = JSON.parse(event.data);

    if (data.type === 'offer') {
        console.log('Viewer received offer, creating PeerConnection...');

        peerConnection = new RTCPeerConnection({
            iceServers: [{ urls: 'stun:stun.l.google.com:19302' }]
        });

        peerConnection.onicecandidate = (event) => {
            if (event.candidate) {
                ws.send(JSON.stringify({ type: 'candidate', candidate: event.candidate }));
            }
        };

        peerConnection.oniceconnectionstatechange = () => {
            console.log('Viewer ICE state:', peerConnection.iceConnectionState);
        };

        peerConnection.ontrack = (event) => {
            if (remoteFeed.srcObject !== event.streams[0]) {
                remoteFeed.srcObject = event.streams[0];
                console.log('Viewer received remote stream');
            }
        };

        await peerConnection.setRemoteDescription(new RTCSessionDescription(data.offer));
        const answer = await peerConnection.createAnswer();
        await peerConnection.setLocalDescription(answer);
        ws.send(JSON.stringify({ type: 'answer', answer }));
        console.log('Viewer sent answer');

    } else if (data.type === 'candidate') {
        if (peerConnection) {
            await peerConnection.addIceCandidate(new RTCIceCandidate(data.candidate));
            console.log('Viewer added ICE candidate');
        }
    }
};