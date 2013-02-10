Test getting a cookie.

Hello = <%= Request.Cookies("Hello") %><br>
Hello("Test") = <%= Request.Cookies("Hello")("Test") %><br>
Hello("There") = <%= Request.Cookies("Hello")("There") %><br>
