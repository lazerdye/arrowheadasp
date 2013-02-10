<% Option Explicit %>
Test Option Explicit, no option explicit.

Sub MySub       <% Sub MySub %><br>
    Var1 = "hi" <%  Var1 = "hi" %><br>
End Sub         <% End Sub %><br>
<br>
Var1 = "blah"   <% Var1 = "blah" %><br>
MySub           <% MySub %><br>
Var1 =          <%= Var1 %><br>
