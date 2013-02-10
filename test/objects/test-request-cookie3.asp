Test getting a cookie.

Hello = <%= Request.Cookies("Hello") %><br>
Hello("First") = <%= Request.Cookies("Hello")("First") %><br>
Hello("Second") = <%= Request.Cookies("Hello")("Second") %><br>
Hello("Third") = <%= Request.Cookies("Hello")("Third") %><br>
