<!--#include file="../config/ADODB.inc"-->
Here is a test of connection properties.<br>
<%
    Set CN = OpenConnection()

    for i = 0 to CN.Properties.Count - 1
        %>CN.Property("<%=CN.Properties(i).Name%>")[<%
        %><%=CN.Properties(i).Type%>]="<%=CN.Properties(i).Value%>"<br>
        <%
    next

    CN.Close
%>

The End.
