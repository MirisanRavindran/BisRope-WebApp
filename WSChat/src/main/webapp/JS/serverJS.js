
//Function adds any new rooms in the server to the list of rooms in the HTML file
window.onload = function() {
    refreshRoomList();
    displayServerID();
    refreshUserList();
};

function displayServerID(){
    let name = localStorage.getItem("serverName");
    let messageContainer = document.querySelector(".displayName");
    let el = document.createElement("div");
    el.setAttribute("class", "title-name")
    el.innerHTML = `
                            <div>
                                <p>${name}</p>
                            </div>
                    `;
    messageContainer.appendChild(el);
}
function refreshRoomList(){
    var serverName = localStorage.getItem("serverName");
    var username = localStorage.getItem("username");
    const url = "http://localhost:8080/BisRopeServer-1.0-SNAPSHOT/api/bisrope-server/get-server-rooms/"+ serverName;
    fetch(url, {
        method: 'GET',
    })
        .then(response => {
            if (response.status === 401) {
                // If the response status is "ok", return the response text
                console.log("you done fucked up");
            }
            else{
                return response.text();
            }
        })
        .then(data => {
            // If the response was successful, clear the existing table and append the retrieved chat rooms to the table
            const table = document.getElementById("serverRoomTable");
            while (table.rows.length > 0) {
                table.deleteRow(0);
            }

            // Split the retrieved chat room list into an array
            console.log(data);
            roomArray = data.slice(1, -1).split(", ");
            console.log(roomArray);
            for (let i = 0; i < roomArray.length; i++) {
                // Create a new row in the chat room list table
                const row = table.insertRow();
                // Create a new cell in the row and add a link to the chat room
                const cell = row.insertCell();
                const linkText = document.createTextNode(roomArray[i]);
                const link = document.createElement("a");
                link.appendChild(linkText);
                link.href = "#"; // Set href to # so that the link doesn't redirect the page
                // When the link is clicked, call the enterRoom() function for the selected chat room
                link.onclick = function() {
                    joinRoom(roomArray[i]);
                    return false; // Prevent the link from redirecting the page
                }
                cell.appendChild(link);
            }
        })
}

function refreshUserList(){
    var serverName = localStorage.getItem("serverName");
    var username = localStorage.getItem("username");
    const url = "http://localhost:8080/BisRopeServer-1.0-SNAPSHOT/api/bisrope-server/get-server-users/"+ serverName;
    fetch(url, {
        method: 'GET',
    })
        .then(response => {
            if (response.status === 401) {
                // If the response status is "ok", return the response text
                console.log("you done fucked up");
            }
            else{
                return response.text();
            }
        })
        .then(data => {
            // If the response was successful, clear the existing table and append the retrieved chat rooms to the table
            const table = document.getElementById("UserTable");
            while (table.rows.length > 0) {
                table.deleteRow(0);
            }

            // Split the retrieved chat room list into an array
            console.log(data);
            roomArray = data.slice(1, -1).split(", ");
            console.log(roomArray);
            for (let i = 0; i < roomArray.length; i++) {
                // Create a new row in the chat room list table
                const row = table.insertRow();
                // Create a new cell in the row and add a link to the chat room
                const cell = row.insertCell();
                const linkText = document.createTextNode(roomArray[i]);
                const link = document.createElement("a");
                link.appendChild(linkText);
                cell.appendChild(link);
            }
        })
}

//Function call to create a new room


//Function call to join an existing room
function joinRoom(roomID){
    localStorage.setItem("roomID",roomID);
    window.location.href = "dmpage.html";
}


