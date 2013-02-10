<!-- Test CreateObject function.
-->

Dim A <% Dim A %><br>
Set A = CreateObject("Scripting.Dictionary")
    <% Set A = CreateObject("Scripting.Dictionary") %><br>
A("Hello") = "there" 
    <% A("Hello") = "there" %><br>
A("Hello") = <%= A("Hello") %><br>
