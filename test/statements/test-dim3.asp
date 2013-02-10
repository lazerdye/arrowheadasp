Test incorrect Dim statements:<br>

Parameter redefined with a DIM statement.<br>

<%
    Sub TheSub(ParA)
        %>ParA: <%=ParA%><br>
        <%
        
        Dim ParA

        %>Should not get here.<br>
        <%
    End Sub

    Dim ParA

    Response.Write("Output some stuff<br>" & VBCrlf)

    ParA = "Hello"

    %>ParA: <%=ParA%><br>
    <%

    Call TheSub(ParA)

    Response.Write("Should never get here.<br>" & VBcrlf)
%>
