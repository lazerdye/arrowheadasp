Test calling multi-parameter procedure

<%
Sub Two_Param_Sub(Param1, Param2)
    Response.Write("In Two_Param_Sub(" & Param1 & "," & Param2 & ")")
End Sub
%>

Two_Param_Sub("One", "Two") <% Two_Param_Sub("One", "Two") %><br>
