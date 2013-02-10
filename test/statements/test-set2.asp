Test Set Statement

<pre>
Dim A, B <% Dim A, B %>

A = Server.CreateObject("Scripting.Dictionary") <%
    A = Server.CreateObject("Scripting.Dictionary") %>

A.Add "Hello", "There" <% A.Add "Hello", "There" %>

B = A("Hello") <% B = A("Hello") %>

B = <%= B %>

</pre>
