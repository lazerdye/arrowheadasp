<!--#include file="../config/ADODB.inc"-->
Here is the test of basic ADODB functions.<br>
<%
    Dim CN, RS

    Set CN = Server.CreateObject("ADODB.Connection")
    CN.Open Connect_URL, Connect_Username, Connect_Password
    Set RS = CN.Execute("SELECT StringField, IntField FROM Test ORDER BY IntField")

    %>CursorLocation: <%=CN.CursorLocation%><br>
    <%

    While not RS.Eof
        %>Value: <%=RS("StringField")%> / <%=RS("IntField")%><br>
        <%
        RS.MoveNext
    Wend

    CN.Close
%>

The End.
