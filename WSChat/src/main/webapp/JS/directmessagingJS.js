
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
        let messageContainer = document.querySelector(".textArea");
        let el = document.createElement("div");
        if (message.type === "chat")
        {
            el.setAttribute("class", "text-bubble")
            el.innerHTML = `
                        <div class="Message">
                            <div>
                                <div><p>${message.message}</p><div>
                            </div>
                        </div>
                    `;
            messageContainer.appendChild(el);
        }
        else if (message.type === "image") {
            console.log(message)
            el.setAttribute("class", "image-container")
            el.innerHTML = `
                        <div>
                            <img src='${message.message}' class="img-fluid"/>
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
                let request = {"type":"image", "msg": message};
                ws.send(JSON.stringify(request));

                //document.getElementById("image").innerHTML += "[" + timestamp() + "] " + message + "\n";
            }
            , false);
        if (file) {
            reader.readAsDataURL(file);
        }
    }
}
function goBack(){
    window.location.href = "serverpage.html";
}

function timestamp() {
    var d = new Date(), minutes = d.getMinutes();
    if (minutes < 10) minutes = '0' + minutes;
    return d.getHours() + ':' + minutes;
}

const container = document.getElementById("log");

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

function showEmojiPanel() {
    document.querySelector(".emoji-container").removeAttribute("style");
}
function hideEmojiPanel() {
    document.querySelector(".emoji-container").setAttribute("style", "display:none;");
}
function getEmoji(code) {
    document.getElementById("inputText").value += code.textContent;
}