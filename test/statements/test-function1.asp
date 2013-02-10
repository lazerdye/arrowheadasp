Test functions, normal use.

Function MyFunc(Var1, ByVal Var2) <% Function MyFunc(Var1, ByVal Var2) %><br>
    Var1 = "New Var1"       <% Var1 = "New Var1" %><br>
    Var2 = "New Var2"       <% Var2 = "New Var2" %><br>
    MyFunc = "Return"       <% MyFunc = "Return" %><br>
    Inside Function<br>
End Function                <% End Function%><br>
<br>
Var1 = "Orig Var1"          <% Var1 = "Orig Var1" %><br>
Var2 = "Orig Var2"          <% Var2 = "Orig Var2" %><br>
Var1 =                      <%= Var1 %><br>
Var2 =                      <%= Var2 %><br>
MyFunc(Var1, Var2) =        <%= MyFunc(Var1, Var2) %><br>
Var1 =                      <%= Var1 %><br>
Var2 =                      <%= Var2 %><br>
Var1 = "Orig Var1"          <% Var1 = "Orig Var1" %><br>
Var2 = "Orig Var2"          <% Var2 = "Orig Var2" %><br>
MyFunc("Blah", "Cha")       <%= MyFunc("Blah", "Cha") %><br>
Var1 =                      <%= Var1 %><br>
Var2 =                      <%= Var2 %><br>
