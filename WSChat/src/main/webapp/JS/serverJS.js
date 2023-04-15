
//Function adds any new rooms in the server to the list of rooms in the HTML file
function refreshRoomList(){
    var serverId = localStorage.getItem("serverId");
    var username = localStorage.getItem("username");
    const url = "http://localhost:8080/BisRopeServer-1.0-SNAPSHOT/api/bisrope-server/get-room-list/"+ serverId +"/" + username;
    fetch(url, {
        method: 'GET',
    })
        .then(response => {
            if (response.ok) {
                // If the response status is "ok", return the response text
                console.log(response.text);
                return response.text();
            }
            throw new Error('Network response was not ok.');
        })
        .then(data => {
            var roomList = JSON.parse(data);
            var roomListHTML = "";
            for (var i = 0; i < roomList.length; i++) {
                roomListHTML += "<li><a href=\"#\" onclick=\"joinRoom(" + roomList[i].roomId + ")\">" + roomList[i].roomName + "</a></li>";
            }
            document.getElementById("roomList").innerHTML = roomListHTML;
        })
}

//Function call to create a new room
function createNewRoom(){
    var serverId = localStorage.getItem("serverId");
    var username = localStorage.getItem("username");
    var roomName = document.getElementById("roomName").value;
    const url = "http://localhost:8080/BisRopeServer-1.0-SNAPSHOT/api/bisrope-server/create-room/"+ serverId +"/" + username + "/" + roomName;
    fetch(url, {
        method: 'GET',
    })
        .then(response => {
            if (response.ok) {
                // If the response status is "ok", return the response text
                console.log(response.text);
                //add room to the room list
                addRoomToServerList(response.text, roomName);


                refreshRoomList();
            }
            throw new Error('Network response was not ok.');
        })
}

function addRoomToServerList(roomId, roomName){
    var roomListHTML = document.getElementById("roomList").innerHTML;
    roomListHTML += "<li><a href=\"#\" onclick=\"joinRoom(" + roomId + ")\">" + roomName + "</a></li>";
    document.getElementById("roomList").innerHTML = roomListHTML;
}

//Function call to join an existing room
function joinRoom(){
    var roomId = document.getElementById("roomId").value;
    var username = localStorage.getItem("username");
    const url = "http://localhost:8080/BisRopeServer-1.0-SNAPSHOT/api/bisrope-server/join-room/"+ roomId +"/" + username;
    fetch(url, {
        method: 'GET',
    })
        .then(response => {
            if (response.ok) {
                // If the response status is "ok", return the response text
                console.log(response.text);
                alert("Room joined successfully");
            }
            if (response.status === 401){
                console.log("Room name does not exist. Please try again.");
                alert("Room name does not exist. Please try again.");
            }
            throw new Error('Network response was not ok.');
        })

}


