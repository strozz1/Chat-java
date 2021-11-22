// Wrap every letter in a span
var text = document.getElementById("text");
var values =["..", "..."];
var cont = 0;
var i = 0 //  set your counter to 1

function myLoop() { //  create a loop function
   setTimeout(function() { //  call a 3s setTimeout when the loop is called
      text.innerHTML = "App in development" + values[i] //  your code here
      i++; //  increment the counter
      myLoop(); //  ..  again which will trigger another 
      if (i >1 ) { 
          i = 0;
      } 
   }, 700)
}

myLoop(); //  start the loop
  