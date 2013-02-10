Test proper call syntax.

<%
Sub No_Param_Sub
    Response.Write("In No_Param_Sub")
End Sub

Sub One_Param_Sub(Param)
    Response.Write("In One_Param_Sub(" & Param & ")")
End Sub

Sub Two_Param_Sub(Param1, Param2)
    Response.Write("In Two_Param_Sub(" & Param1 & "," & Param2 & ")")
End Sub

Function No_Param_Func
    Response.Write ("In No_Param_Func ")
    No_Param_Func = "Return"
End Function

Function One_Param_Func(Param)
    Response.Write ("In One_Param_Func(" & Param & ") ")
    One_Param_Func = "Return"
End Function

Function Two_Param_Func(Param1, Param2)
    Response.Write ("In Two_Param_Func(" & Param1 & "," & Param2 & ") ")
    Two_Param_Func = "Return"
End Function
%>

No_Param_Sub <% No_Param_Sub %><br>
One_Param_Sub <% One_Param_Sub "One" %><br>
Two_Param_Sub <% Two_Param_Sub "One", "Two" %><br>

No_Param_Func <% No_Param_Func %><br>
One_Param_Func <% One_Param_Func "One"%><br>
Two_Param_Func <% Two_Param_Func "One", "Two"%><br>

No_Param_Func = <%= No_Param_Func %><br>
One_Param_Func = <%= One_Param_Func("One") %><br>
Two_Param_Func = <%= Two_Param_Func("One", "Two") %><br>
