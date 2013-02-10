Test Set Statement

<pre>
Dim A, B <% Dim A, B %>

Set A = Server.CreateObject("Scripting.Dictionary") <%
    Set A = Server.CreateObject("Scripting.Dictionary") %>

A.Add "Hello", "There" <% A.Add "Hello", "There" %>

B = A("Hello") <% B = A("Hello") %>

B = <%= B %>

</pre>
