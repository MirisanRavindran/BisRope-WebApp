

window.onload = function() {
    enterRoom();
};
function enterRoom() {
    const code = localStorage.getItem("roomID")
    const user = localStorage.getItem("username")

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


function timestamp() {
    var d = new Date(), minutes = d.getMinutes();
    if (minutes < 10) minutes = '0' + minutes;
    return d.getHours() + ':' + minutes;
}