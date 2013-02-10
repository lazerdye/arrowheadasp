Test incorrect Dim statements:<br>

Name redefined simply.<br>

<%
    Dim VarA, VarB, VarC

    Response.Write("Output some stuff<br>" & VBCrlf)

    Dim VarD, VarE, VarF

    Dim VarA

    Response.Write("Should never get here.<br>" & VBcrlf)
%>
