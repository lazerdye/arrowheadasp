Test calling multi-parameter procedure

<%
Function Two_Param_Func(Param1, Param2)
    Response.Write("In Two_Param_Func(" & Param1 & "," & Param2 & ")")
End Function
%>

Two_Param_Func("One", "Two") = <%= Two_Param_Func "One", "Two" %><br>
