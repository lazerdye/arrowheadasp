Test Select Statement

<pre>
Sub Test_Select(Value)
    Select Case Value %&gt;
        &lt;%Case "A"
            %&gt;The Value is A
        &lt;%Case "B"
            %&gt;The Value is B
        &lt;%Case "C", "D", "E"
            %&gt;The Value is C, D, or E
        &lt;%Case Else
            %&gt;The Value is not A, B, C, D, or E
    &lt;%End Select
End Sub

<%
Sub Test_Select(Value)
    Select Case Value %>
        <%Case "A"
            %>The Value is A
        <%Case "B"
            %>The Value is B
        <%Case "C", "D", "E"
            %>The Value is C, D, or E
        <%Case Else
            %>The Value is not A, B, C, D, or E
    <%End Select
End Sub
%>

Test_Select("A") <% Test_Select("A") %>
Test_Select("B") <% Test_Select("B") %>
Test_Select("C") <% Test_Select("C") %>
Test_Select("D") <% Test_Select("D") %>
Test_Select("E") <% Test_Select("E") %>
Test_Select("F") <% Test_Select("F") %>
</pre>
