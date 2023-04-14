//File defines the function calls to backend when user enters username and password, or creates a new pair




//Function to call backend with a restful fetch to create a new user
function createUser() {
    var username = document.getElementById("createusername").value;
    var password = document.getElementById("createpassword").value;
    const url = "http://localhost:8080/BisRopeServer-1.0-SNAPSHOT/bisrope-server/create-account/"+ username +"/" + password;
    

    fetch(url, {
        method: 'POST'
    })
        .then(response => {
            if (response.ok) {
                // If the response status is "ok", return the response text
                console.log(response.text);
            }
            if (response.status === 401){
                console.log("Username already exists. Please try again.");
            }
            throw new Error('Network response was not ok.');
        })
        
        .catch(error =>  { //if the username pair already exists in the hashmap of users in BisRopeResource.java, throw an error
            
            
            console.log("Something went seriously wrong with your javascript skills");
        });
 
}

