Test ForEach on Application.Contents

<%
Application.Contents.RemoveAll

Application("A") = "One"
Application("B") = "Two"
Application(3) = "Three"

for each item in Application.Contents
    Response.Write item & "&nbsp;nbsp;" & Application.Contents(item) & "<br>"
next
%>
