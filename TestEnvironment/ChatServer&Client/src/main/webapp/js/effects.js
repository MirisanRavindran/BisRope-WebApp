//The effects.js file contains all the Javascript code that is necessary for the various animations on the website to function.



//This section contains all the constant variables that will be used by Javascript functions to actuate front-end features

const lines = [ //The lines array contains the message that will greet new users when they access the website. Each line is a separate string to actuate the typewriter effect.
    "BisRope is a unique website like no other, with its many features and quick accessibility.",
    "BisRope is truly one of a kind. But due to its recent popularity, BisRope has been facing some controversy from people stating that our app is very similar to Discord.",
    "Many people are even stating that our logo is just the Discord icon but upside down, and while we do admit that there are similarities ",
    "between the two, our logo is actually just the head of Galactus. Despite everything, people fail to see what BisRope is actually meant for. ",
    "BisRope is unique in the sense that it allows people to talk to each other in the comfort of their own homes. ",
    "It lets people share stories, and form bonds, what will you say first?"
  ];

//Each line from the lines array has to be stored in it's own constant variable to be used by the typeLine function.
const l1 = document.getElementById("line1");
const l2 = document.getElementById("line2");
const l3 = document.getElementById("line3");
const l4 = document.getElementById("line4");
const l5 = document.getElementById("line5");
const l6 = document.getElementById("line6");
const l7 = document.getElementById("line7");
const l8 = document.getElementById("line8");

//The aboutCards constant variable is used to store all the elements with the class name of about-card. This is used by the document.addEventListener function to actuate the parallax effect.
let aboutCards = document.querySelectorAll('.about-card');

//The gradient constant variable is used to store all the colors that will be used by the gradientActuate function to actuate the gradient effect.
var gradient = ['#29B6F6', '26C6DA', '#26A69A', '#42A5F5', '#5C6BC0', '#7E57C2', '#AB47BC'] 



//this section of the file contains all the functions that are used to actuate the various animations on the website.


//the typeLine function is used to actuate the typewriter effect. It takes in three parameters, the line variable, the text variable, and the delay variable.
function typeLine(line, text, delay){
    let i = 0;

    let interval = setInterval(function(){ //the setInterval function is used to actuate the typewriter effect. It takes in two parameters, the function that will be executed, and the delay between each execution.

        if(i < text.length){ //this if statement checks if the i variable is less than the length of the text variable. If it is, then the function will continue to execute.
            line.innerHTML += text.charAt(i); //the line.innerHTML variable is used to store the text that will be displayed on the website. The += operator is used to add the next character in the text variable to the line.innerHTML variable.
            i++;
        } else {
            clearInterval(interval); //if the i variable is no longer less than the length of the text variable, then the setInterval function will be cleared.
        }
    }, delay);
}


//the typeLine function is called 6 times, each time with a different parameter. The first parameter is the line variable, which is the element that will display the text. The second parameter is the text variable, which is the text that will be displayed on the website. 
//The third parameter is the delay variable, which is the delay between each character being displayed.
setTimeout(() => typeLine(line1, lines[0], 50), 1000); 
setTimeout(() => typeLine(line2, lines[1], 50), 2000);
setTimeout(() => typeLine(line3, lines[2], 50), 3000);
setTimeout(() => typeLine(line4, lines[3], 50), 4000);
setTimeout(() => typeLine(line5, lines[4], 50), 5000);
setTimeout(() => typeLine(line6, lines[5], 50), 6000);



document.body.style.backgroundColor = gradient[0]; //Used to store the background colour of the website initially

//The gradientActuate function is used to actuate the gradient effect.
function gradientActuate(){

    var nextColor = gradient[Math.floor(Math.random() * gradient.length)]; //The nextColor variable is used to store the next colour that will be used by the gradient, calculated by taking a random index from the gradient array

    document.body.style.backgroundColor = nextColor; //sets the background colour to the next selected colour

}

setInterval(gradientActuate, 3000); //sets the interval for the selection of the next colour


//below code actuates the parralax effect for info cards by using the mousemove event listener.
document.addEventListener('mousemove', function(e){ //activates when the mouse moves over the infocards
    let x = e.clientX / window.innerWidth; //stores the x and y coordinates of the mouse
    let y = e.clientY / window.innerHeight;

    aboutCards.forEach(function(card){ //loops through each card and applies the parallax effect
        card.style.transform = `translate(-${x * 30}px, -${y * 30}px)`; //the translate function is used to move the card in the opposite direction of the mouse movement
    });
});


