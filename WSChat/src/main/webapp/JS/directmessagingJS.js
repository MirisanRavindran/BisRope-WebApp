// The primary JS file, which contains the main functions for communicating with the server

// The web socket
let ws;

window.onload = function() {
    const roomID = localStorage.getItem("roomID");
    console.log(roomID);
    enterRoom(roomID);
};

// The newRoom function creates a new chat room by making a GET request to the server
function enterRoom(code) {
    const user = localStorage.getItem("username");


    ws = new WebSocket("ws://localhost:8080/WSChat-1.0-SNAPSHOT/ws/" + code + "/" + user);

    ws.onmessage = function (event) {
        console.log(event.data);
        let message = JSON.parse(event.data);
        document.getElementById("log").value += "[" + timestamp() + "] " + message.message + "\n";
    }
    document.getElementById("input").addEventListener("keyup", function (event) {
        if (event.keyCode === 13) {
            let request = {"type":"chat", "msg":event.target.value};
            ws.send(JSON.stringify(request));
            event.target.value = "";
        }
    });
}
function goBack(){
    window.location.href = "serverpage.html";
}

function timestamp() {
    var d = new Date(), minutes = d.getMinutes();
    if (minutes < 10) minutes = '0' + minutes;
    return d.getHours() + ':' + minutes;
}



