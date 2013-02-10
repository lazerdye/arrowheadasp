Test functions, normal use.

Sub MySub(Var1, ByVal Var2) <% Sub MySub(Var1, ByVal Var2) %><br>
    Var1 = "New Var1"       <%  Var1 = "New Var1" %><br>
    Var2 = "New Var2"       <%  Var2 = "New Var2" %><br>
    Inside Sub<br>
End Sub                     <% End Sub%><br>
<br>
Var1 = "Orig Var1"          <% Var1 = "Orig Var1" %><br>
Var2 = "Orig Var2"          <% Var2 = "Orig Var2" %><br>
Var1 =                      <%= Var1 %><br>
Var2 =                      <%= Var2 %><br>
Call MySub(Var1, Var2)      <% Call MySub(Var1, Var2) %><br>
Var1 =                      <%= Var1 %><br>
Var2 =                      <%= Var2 %><br>
Var1 = "Orig Var1"          <% Var1 = "Orig Var1" %><br>
Var2 = "Orig Var2"          <% Var2 = "Orig Var2" %><br>
Call MySub("Blah", "Cha")   <% Call MySub("Blah", "Cha") %><br>
Var1 =                      <%= Var1 %><br>
Var2 =                      <%= Var2 %><br>
