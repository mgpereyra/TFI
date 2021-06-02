
// Modal Image Gallery
function onClick(element) {
    document.getElementById("img01").src = element.src;
    document.getElementById("modal01").style.display = "block";
    var captionText = document.getElementById("caption");
    captionText.innerHTML = element.alt;
  }
  
  
  // Toggle between showing and hiding the sidebar when clicking the menu icon
  var mySidebar = document.getElementById("mySidebar");
  
  function w3_open() {
    if (mySidebar.style.display === 'block') {
      mySidebar.style.display = 'none';
    } else {
      mySidebar.style.display = 'block';
    }
  }
  
  // Close the sidebar with the close button
  function w3_close() {
      mySidebar.style.display = "none";
  }
  
  
  var end = new Date('05/17/2022 9:30 AM');
  
      var _second = 1000;
      var _minute = _second * 60;
      var _hour = _minute * 60;
      var _day = _hour * 24;
      var timer;
  
      function showRemaining() {
          var now = new Date();
          var distance = end - now;
          if (distance < 0) {
  
              clearInterval(timer);
              document.getElementById('countdown').innerHTML = 'YA ESTÁ DISPONIBLE!';
  
              return;
          }
          var days = Math.floor(distance / _day);
          var hours = Math.floor((distance % _day) / _hour);
          var minutes = Math.floor((distance % _hour) / _minute);
          var seconds = Math.floor((distance % _minute) / _second);
  
          document.getElementById('dias').innerHTML = days ;
          document.getElementById('horas').innerHTML = hours ;
          document.getElementById('minutos').innerHTML = minutes;
          document.getElementById('segundos').innerHTML = seconds ;
      }
  
      timer = setInterval(showRemaining, 1000);