let username = document.querySelector("#username");

function sendMessage()
{
    let messageInput = document.querySelector("#message-input");
    let message = messageInput.value;
    if (message.length == 0) {
        return;
    }
    renderMessage("my", {
        username: username,
        text: message
    });
    messageInput.value = "";
}

function renderMessage(type, message)
{
    let messageContainer = document.querySelector(".messages");
    if (type == "my")
    {
        let el = document.createElement("div");
        el.setAttribute("class", "message my-message");
        el.innerHTML = `
            <div>
                <div class = "name">You</div>
                <div class="text">${message.text}</div>
            </div>
        `;
        messageContainer.appendChild(el);
    }
}

function join() {
    const joinScreen = document.querySelector('.join-screen');
    const chatScreen = document.querySelector('.chat-screen');

    let update = document.querySelector("#update-user");
    if (username.value.length == 0)
    {
        return;
    }
    joinScreen.classList.remove('active');
    chatScreen.classList.add('active');

    update.innerHTML = username.value + " has joined the chat room.";
}

function joinNewRoom() {
    const joinScreen = document.querySelector('.chat-screen');
    const chatScreen = document.querySelector('.join-screen');

    joinScreen.classList.remove('active');
    chatScreen.classList.add('active');
}

function scrollTextarea(event) {
    var textarea = document.getElementById("message-input");
    var textareaScrollTop = textarea.scrollTop;
    var textareaScrollHeight = textarea.scrollHeight;
    var textareaClientHeight = textarea.clientHeight;

    // Determine the direction of the scroll
    var deltaY = event.deltaY;
    var direction = deltaY > 0 ? 1 : -1;

    // Check if we're at the top or bottom of the textarea
    if ((direction == -1 && textareaScrollTop == 0) ||
        (direction == 1 && textareaScrollTop + textareaClientHeight == textareaScrollHeight)) {
        // If we're at the top or bottom, prevent the default scroll behavior
        event.preventDefault();
    }
}