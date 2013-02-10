Test Application.Contents.Count

<%
Application.Contents.RemoveAll

Application("A") = "One"
Application("B") = "Two"
Application(3) = "Three"

for item = 1 to Application.Contents.Count
    Response.Write item 
    Response.Write "&nbsp;nbsp;" & Application.Contents(item) & "<br>"
next
%>
