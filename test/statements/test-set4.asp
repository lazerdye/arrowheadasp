Test Set Statement

<pre>
Set A = Server.CreateObject("Scripting.Dictionary") <%
    Set A = Server.CreateObject("Scripting.Dictionary") %>

IsObject(A) = <%= IsObject(A) %>

Set A = Nothing <% Set A = Nothing %>

IsObject(A) = <%= IsObject(A) %>
</pre>
