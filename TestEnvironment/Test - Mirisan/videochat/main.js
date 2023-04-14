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

    client = await A.createInstance(APP_ID)

    //Ask user for permission for their video and audio to be used
    localStream = await navigator.mediaDevices.getUserMedia({video:true, audio:false})
    document.getElementById('user-1').srcObject = localStream
    createOffer()
}

//Stores information and connect to peer
let createOffer = async () => {
    peerConnection = new RTCPeerConnection(servers)

    remoteStream = new MediaStream()
    document.getElementById('user-2').srcObject = remoteStream

    localStream.getTracks().forEach((track)=>  {
        peerConnection.addTrack(track, localStream)
    })

    peerConnection.ontrack=(event)=>{
        event.streams[0].getTracks().forEach((track)=> {
            remoteStream.addTrack();
        })
    }

    peerConnection.onicecandidate = async (event) => {
        if(event.candidate){
            console.log('NEW ICE candidate: ', event.candidate)
        }
    }



    let offer = await peerConnection.createOffer()
    await peerConnection.setLocalDescription(offer) //Every peerConnection would have an offer based on the local description which is user 1

    console.log('Offer: ', offer) //Checks to see if it works
}

init()
