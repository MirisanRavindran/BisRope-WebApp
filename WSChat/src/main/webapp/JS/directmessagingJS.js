// The primary JS file, which contains the main functions for communicating with the server

// The web socket
let ws;

window.onload = function() {
    getRooms();
    console.log(localStorage.getItem("roomID"));
};
// The getRooms function is called when the page is loaded.
// It retrieves the list of chat rooms from the server and displays them in the list of chat rooms.
function getRooms(){
    const id = localStorage.getItem("roomID");
    if (!id) return; // If the id is null, skip creating the link element
    console.log(id);
    const table = document.getElementById("Chat-list-table");
    const row = table.insertRow();
    const cell = row.insertCell();
    const link = document.createElement("a");
    link.appendChild(document.createTextNode(id));
    link.href = "#"; // Set href to # so that the link doesn't redirect the page
    // When the link is clicked, call the enterRoom() function for the selected chat room
    link.onclick = function() {
        enterRoom(localStorage.getItem("roomID"));
        return false; // Prevent the link from redirecting the page
    }
    cell.appendChild(link);
    // Define the URL for the API endpoint that returns the list of chat rooms
}


// The newRoom function creates a new chat room by making a GET request to the server
function enterRoom(code) {
    const body = code;

    ws = new WebSocket("ws://localhost:8080/WSChat-1.0-SNAPSHOT/ws/" + code + "/" + code);

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


function timestamp() {
    var d = new Date(), minutes = d.getMinutes();
    if (minutes < 10) minutes = '0' + minutes;
    return d.getHours() + ':' + minutes;
}



