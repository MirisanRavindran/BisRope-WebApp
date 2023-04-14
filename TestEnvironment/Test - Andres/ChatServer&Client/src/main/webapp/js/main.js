// The primary JS file, which contains the main functions for communicating with the server

// The web socket
let ws;

// The getRooms function is called when the page is loaded.
// It retrieves the list of chat rooms from the server and displays them in the list of chat rooms.
function getRooms(){
    // Define the URL for the API endpoint that returns the list of chat rooms
    const url = 'http://localhost:8080/GetRoomList-1.0-SNAPSHOT/api/rooms/roomlist';
    // Initialize an array to hold the retrieved list of chat rooms
    var roomArray = [];
    // Make a GET request to the API endpoint using the fetch() function
    fetch(url, {
        method: 'GET'
    })
        .then(response => {
            if (response.ok) {
                // If the response status is "ok", return the response text
                return response.text();
            }
            throw new Error('Network response was not ok.');
        })
        .then(data => {
            // If the response was successful, clear the existing table and append the retrieved chat rooms to the table
            const table = document.getElementById("Chat-list-table");
            while (table.rows.length > 0) {
                table.deleteRow(0);
            }

            // Split the retrieved chat room list into an array
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
                    enterRoom(roomArray[i]);
                    return false; // Prevent the link from redirecting the page
                }
                cell.appendChild(link);
            }
        })
        .catch(error => {
            // If an error occurs, handle it here
        });
}

// The newRoom function creates a new chat room by making a GET request to the server
function newRoom(){
    // Define the URL for the API endpoint that creates a new chat room
    const url = 'http://localhost:8080/GetRoomList-1.0-SNAPSHOT/api/rooms/random';

    // Make a GET request to the API endpoint using the fetch() function
    fetch(url, {
        method: 'GET'
    })
        .then(response => {
            if (response.ok) {
                // If the response status is "ok", return the response text
                return response.text();
            }
            throw new Error('Network response was not ok.');
        })
        .then(data => {
            // If the response was successful, create a new WebSocket connection to the chat server
            ws = new WebSocket("ws://localhost:8080/WSChatServer-1.0-SNAPSHOT/ws/" + data);

            // Set up an event listener for incoming messages
            ws.onmessage = function (event) {
                console.log(event.data);
                // Parse the incoming message and append it to the chat log on the web page
                let message = JSON.parse(event.data);
                console.log(message.type === "chat");
                // document.getElementById("log").value += "[" + timestamp() + "] " + message.message + "\n";
                let messageContainer = document.querySelector(".textArea");
                let el = document.createElement("div");
                if (message.type === "chat")
                {
                    el.setAttribute("class", "text-bubble")
                    el.innerHTML = `
                        <div class="Messages">
                            <div>
                                <p id="log">${message.message}</p>
                            </div>
                        </div>
                    `;
                    messageContainer.appendChild(el);
                }
                else if (message.type === "Server")
                {
                    el.setAttribute("class", "Message")
                    el.innerHTML = `
                        <div>
                            <h3 id="ServerMessage">${message.message}</h3>
                        </div>
                    `;
                    messageContainer.appendChild(el);
                }
            }
            document.getElementById("inputText").addEventListener("keyup", function (event) {
                if (event.keyCode === 13) {
                    let request = {"type":"chat", "msg":event.target.value};
                    ws.send(JSON.stringify(request));
                    event.target.value = "";
                }
            });
        })
        .catch(error => {
            // Handle the error here
        });


}
function enterRoom(code) {
    const url = 'http://localhost:8080/GetRoomList-1.0-SNAPSHOT/api/rooms/random';
    const body = code;

    ws = new WebSocket("ws://localhost:8080/WSChatServer-1.0-SNAPSHOT/ws/" + code);

    ws.onmessage = function (event) {
        console.log(event.data);
        let message = JSON.parse(event.data);
        // document.getElementById("log").value += "[" + timestamp() + "] " + message.message + "\n";
        console.log(message.type === "Server");
        let messageContainer = document.querySelector(".textArea");
        let el = document.createElement("div");
        if (message.type === "chat")
        {
            el.setAttribute("class", "text-bubble")
            el.innerHTML = `
                        <div class="Messages">
                            <div>
                                <p id="log">${message.message}</p>
                            </div>
                        </div>
                    `;
            messageContainer.appendChild(el);
        }
        else if (message.type === "Server")
        {
            el.setAttribute("class", "Message")
            el.innerHTML = `
                        <div>
                            <h3 id="ServerMessage">${message.message}</h3>
                        </div>
                    `;
            messageContainer.appendChild(el);
        }

    }
    document.getElementById("inputText").addEventListener("keyup", function (event) {
        if (event.keyCode === 13) {
            let request = {"type":"Server", "msg":event.target.value};
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


function ChooseImage() {
    document.getElementById('imageFile').click();
}
function SendImage(event) {
    var file = event.files[0];

    if (!file.type.match("image.*")) {
        alert("Please select image only.");
    }
    else {
        var reader = new FileReader();

        reader.addEventListener("load", function(){
                let message = reader.result;
                message = `<img src='${message}' class="img-fluid"/>`;
                let messageContainer = document.querySelector(".textArea");
                let el = document.createElement("div");
                el.setAttribute("class", "image-container")
                el.innerHTML = `
                        <div>
                            <img ${message} 
                        </div>
                    `;
                messageContainer.appendChild(el);
                //document.getElementById("image").innerHTML += "[" + timestamp() + "] " + message + "\n";
            }
            , false);
        if (file) {
            reader.readAsDataURL(file);
        }
    }
}

const container = document.querySelector('.ChatSpace');

container.addEventListener('wheel', (e) => {
    e.preventDefault();
    container.scrollBy(0, e.deltaY);

    const scrollHeight = container.scrollHeight;
    const height = container.clientHeight;
    const maxScrollTop = scrollHeight - height;
    const currentScrollTop = container.scrollTop;

    if (currentScrollTop === maxScrollTop && e.deltaY > 0) {
        e.preventDefault();
    }

    if (currentScrollTop === 0 && e.deltaY < 0) {
        e.preventDefault();
    }
});



