<!--#include file="../config/ADODB.inc"-->
Here is the test of the state object.<br>
<%
    Dim CN, RS

    Set CN = Server.CreateObject("ADODB.Connection")
    %>State: <%=CN.State%><br>
    <%
    CN.Open Connect_URL, Connect_Username, Connect_Password
    %>State: <%=CN.State%><br>
    <%
    Set RS = CN.Execute("SELECT StringField, IntField FROM Test ORDER BY IntField")

    While not RS.Eof
        %>Value: <%=RS("StringField")%> / <%=RS("IntField")%><br>
        <%
        RS.MoveNext
    Wend

    %>State: <%=CN.State%><br>
    <%

    CN.Close

    %>State: <%=CN.State%><br>
    <%
%>

The End.
