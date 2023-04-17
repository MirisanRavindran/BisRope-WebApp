const callURL="http://localhost:8080/lab5-1.0/api/students/json";
console.log("Loaded data from " + callURL);

window.onload = function requestData() {
    fetch(callURL, {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
        },
    })
        .then(response => response.json())
        .then(response => add_records(response))
        .catch((err) => {
            console.log("something went wrong: " + err);
        });
}

function add_records(data) {
    let tableRef = document.getElementById("chart-body");

    for (let i = 0; i < data.students.length; i++) {
        let newRow = tableRef.insertRow();
        let nameCell = newRow.insertCell();
        let idCell = newRow.insertCell();
        let gpaCell = newRow.insertCell();

        nameCell.textContent = data.students[i].name;
        idCell.textContent = data.students[i].id;
        gpaCell.textContent = data.students[i].gpa;
    }
}

function manual_add_record() {
    const name = document.getElementById("name").value;
    const id = document.getElementById("id").value;
    const gpa = document.getElementById("gpa").value;

    // Check if any of the input values are empty
    if (name === "" || id === "" || gpa === "") {
        return; // Do nothing
    }
    else {
        const row = document.createElement("tr");

        const nameCell = document.createElement("td");
        nameCell.textContent = name;
        row.appendChild(nameCell);

        const idCell = document.createElement("td");
        idCell.textContent = id;
        row.appendChild(idCell);

        const gpaCell = document.createElement("td");
        gpaCell.textContent = gpa;
        row.appendChild(gpaCell);

        const tableBody = document.querySelector("table tbody");
        tableBody.appendChild(row);
    }
}

//Video Call
let APP_ID = "6b6d1acb7e8e4f47a801ebbf12e0a153"
let token = null;
let uid = String(Math.floor(Math.random()*10000)) //Gives random number id to user to show who is who

let client;
let channel; //Sends messages to this channel

let localStream;
let remoteStream;
let peerConnection;

const servers = {
    iceServers: [
        {
            urls:['stun:stun1.1.google.com:19302', 'stun:stun2.1.google.com:19302']
        }
    ]
}

let init = async () => {
    client = await AgoraRTM.createInstance(APP_ID)
    await client.login({uid, token})

    //Room ID
    channel = client.createChannel('main')
    await channel.join()

    channel.on('MemberJoined', handleUserJoined);

    client.on('MessageFromPeer', handleMessageFromPeer)

    //Ask user for permission for their video and audio to be used
    localStream = await navigator.mediaDevices.getUserMedia({video:true, audio:true})
    document.getElementById('user-1').srcObject = localStream
}


let handleUserJoined = async (MemberID) => {
    console.log('A new user joined the channel:', MemberID)
    createOffer(MemberID)
}

let handleMessageFromPeer = async (message, MemberID) => {
    message = JSON.parse(message.text)
    if (message.type == 'offer') {
        createAnswer(MemberID, message.offer)
    }

    if (message.type == 'answer') {
        addAnswer(message.answer)
    }

    if (message.type == 'candidate') {
        if(peerConnection){
            peerConnection.addIceCandidate(message.candidate)
        }
    }
}


let createPeerConnection = async (MemberID) => {
    peerConnection = new RTCPeerConnection(servers)

    remoteStream = new MediaStream()
    document.getElementById('user-2').srcObject=remoteStream

    if(!localStream){
        localStream = await navigator.mediaDevices.getUserMedia({video:true, audio:true})
        document.getElementById('user-1').srcObject = localStream
    }

    localStream.getTracks().forEach((track) => {
        peerConnection.addTrack(track, localStream)
    })

    peerConnection.ontrack = (event) => {
        event.streams[0].getTracks().forEach((track) => {
            remoteStream.addTrack(track)
        })
    }

    peerConnection.onicecandidate = async (event) => {
        if(event.candidate){
            client.sendMessageToPeer({text:JSON.stringify({'type':'candidate', 'candidate':event.candidate})}, MemberID)
        }
    }
}


//Stores information and connect to peer
let createOffer = async (MemberID) => {

    await createPeerConnection(MemberID)

    let offer = await peerConnection.createOffer()
    await peerConnection.setLocalDescription(offer)

    client.sendMessageToPeer({text:JSON.stringify({'type':'offer', 'offer':offer})}, MemberID)

}

let createAnswer = async (MemberID, offer)=> {
    await createPeerConnection(MemberID)

    await peerConnection.setRemoteDescription(offer)

    let answer = await peerConnection.createAnswer()
    await peerConnection.setLocalDescription(answer)

    client.sendMessageToPeer({text:JSON.stringify({'type':'answer', 'answer':answer})}, MemberID)




}


let addAnswer = async (answer) => {
    if (!peerConnection.currentRemoteDescription){
        peerConnection.setRemoteDescription(answer)

    }
}

let toggleCamera = async()=> {
    let videoTrack = localStream.getTracks().find(track=>track.kind=='video')

    if(videoTrack.enabled){
        videoTrack.enabled = false
        document.getElementById('camera-btn').style.backgroundColor='rgb(255,80,80)'

    }
    else {
        videoTrack.enabled = true
        document.getElementById('camera-btn').style.backgroundColor='rgb(179,102,249)'
    }
}

let toggleMic = async()=> {
    let audioTrack = localStream.getTracks().find(track=>track.kind=='audio')

    if(audioTrack.enabled){
        audioTrack.enabled = false
        document.getElementById('mic-btn').style.backgroundColor='rgb(255,80,80)'

    }
    else {
        audioTrack.enabled = true
        document.getElementById('mic-btn').style.backgroundColor='rgb(179,102,249)'
    }
}

document.getElementById('camera-btn').addEventListener('click', toggleCamera)
document.getElementById('mic-btn').addEventListener('click', toggleMic)
init()
