Test Application.Contents.Remove

<%
Application.Contents.RemoveAll

Application("A") = "One"
Application("B") = "Two"
Application("C") = "Three"

for each item in Application.Contents
    Response.Write item & "&nbsp;nbsp;" & Application.Contents(item) & "<br>"
next

Application.Contents.Remove 2

for each item in Application.Contents
    Response.Write item & "&nbsp;nbsp;" & Application.Contents(item) & "<br>"
next
%>
