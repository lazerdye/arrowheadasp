Test Set Statement

<pre>
Dim A, B <% Dim A, B %>

Set A = Server.CreateObject("Scripting.Dictionary") <%
    Set A = Server.CreateObject("Scripting.Dictionary") %>

A.Add "Hello", "There" <% A.Add "Hello", "There" %>

Set B = A("Hello") <% Set B = A("Hello") %>

B = <%= B %>

</pre>
