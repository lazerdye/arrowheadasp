<!--#include file="../config/ADODB.inc"-->
Here is the test of Errors collection<br>
<%
    Dim CN, RS

    Set CN = Server.CreateObject("ADODB.Connection")

    %>Error.Count = <%=CN.Errors.Count%><br>
    <%
    On Error Resume Next
    CN.Open "DSN=NonExistantDSN"

    %>Error.Count = <%=CN.Errors.Count%><br>
    <%

    CN.Close
%>

The End.
