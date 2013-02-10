<!--#include file="../config/ADODB.inc"-->
Here is the test of basic ADODB functions.<br>
<%
    Dim CN, RS

    Set CN = Server.CreateObject("ADODB.Connection")
    CN.Open Connect_URL, Connect_Username, Connect_Password
    Set RS = CN.Execute("SELECT StringField, IntField FROM Test ORDER BY IntField")

    %>ConnectionString: <%=CN.ConnectionString%><br>
    <%

    While not RS.Eof
        %>Value: <%=RS("StringField")%> / <%=RS("IntField")%><br>
        <%
        Call RS.MoveNext
    Wend

    CN.Close
%>

The End.
