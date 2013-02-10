Test storing items into the application object by key.

Application.Contents.Item("Hi") = "There" <%
   Application.Contents.Item("Hi") = "There" %><br>
Application.Contents.Item("Hi") = <%
    %><%=Application.Contents.Item("Hi") %><br>
Application.Contents("Hi") = <%
    %><%=Application.Contents("Hi") %><br>
Application("Hi") = <%
    %><%=Application("Hi") %><br>
