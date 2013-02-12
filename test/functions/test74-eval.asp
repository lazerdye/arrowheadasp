<!-- Test Eval function.
-->
<%
Function One
  One = 1
End Function
%>
Eval("2+2") = <%= Eval("2+2") %> <br>
Eval("0.5+2") = <%= Eval("0.5+2") %> <br>
Eval("2+Eval(""2"")") = <%= Eval("2+Eval(""2"")") %> <br>
Eval("One()") = <%= Eval("One()") %> <br>
Eval("Response.Write(1)") = <% Eval("Response.Write(1)") %>
