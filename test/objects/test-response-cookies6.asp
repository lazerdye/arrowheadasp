Test Response.Cookies, simple cookies

Response.Cookies("First") = "Value"
    <% Response.Cookies("First") = "Value" %><br>
Response.Cookies("First").Expires = CDate("June 25, 2005")
    <% Response.Cookies("First").Expires = CDate("June 25, 2005") %><br>
