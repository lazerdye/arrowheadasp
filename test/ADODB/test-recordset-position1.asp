<!--#include file="../config/ADODB.inc"-->
Here is the test of RecordSet AbsolutePosition.<br>
<%
    Dim CN, RS

    Set CN = Server.CreateObject("ADODB.Connection")
    CN.Open Connect_URL, Connect_Username, Connect_Password
    Set RS = Server.CreateObject("ADODB.RecordSet")
    RS.CursorLocation = 3
    RS.Open "SELECT StringField, IntField FROM Test ORDER BY IntField", CN

    %>Position: <%=RS.AbsolutePosition%>
<%
    While not RS.Eof
        %>Value: <%=RS("StringField")%> / <%=RS("IntField")%><br>
        Position: <%=RS.AbsolutePosition%><br />
<%
        RS.MoveNext
    Wend

    %>Position: <%=RS.AbsolutePosition%><br />
<%

    CN.Close

    %>State: <%=CN.State%><br>
    <%
%>

The End.
