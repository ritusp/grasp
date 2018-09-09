// Set the date we're counting down to
var countDownSecond  = 0;
var star = " ";
// Update the count down every 1 second
var x = setInterval(function() {

    // Get todays date and time
    var now = new Date().getTime();

    // Find the distance between now and the count down date
    var distance = countDownSecond ;

    var minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
    var seconds = Math.floor((distance % (1000 * 60)) / 1000);

    // Output the result in an element with id="demo"
    document.getElementById("clocktimer").innerHTML = minutes + "m " + seconds + "s " + star;

    star = star + " * ";
	countDownSecond = countDownSecond + 1000;
}, 1000);