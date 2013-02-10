Test Select Statement

<pre>
Sub Test_Select(Value)
    Select Case Value
        Case "A"
            Response.Write("The Value is A")
        Case "B"
            Response.Write("The Value is B")
        Case "C", "D", "E"
            Response.Write("The Value is C, D, or E")
        Case Else
            Response.Write("The Value is not A, B, C, D, or E")
    End Select
End Sub

<%
Sub Test_Select(Value)
    Select Case Value
        Case "A"
            Response.Write("The Value is A")
        Case "B"
            Response.Write("The Value is B")
        Case "C", "D", "E"
            Response.Write("The Value is C, D, or E")
        Case Else
            Response.Write("The Value is not A, B, C, D, or E")
    End Select
End Sub
%>

Test_Select("A") <% Test_Select("A") %>
Test_Select("B") <% Test_Select("B") %>
Test_Select("C") <% Test_Select("C") %>
Test_Select("D") <% Test_Select("D") %>
Test_Select("E") <% Test_Select("E") %>
Test_Select("F") <% Test_Select("F") %>
</pre>
