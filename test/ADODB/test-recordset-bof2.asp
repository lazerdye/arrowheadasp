<!--#include file="../config/ADODB.inc"-->
Here is the test of RecordSet AbsolutePosition.<br>
<%
    Dim CN, RS

    Set CN = Server.CreateObject("ADODB.Connection")
    CN.Open Connect_URL, Connect_Username, Connect_Password
    Set RS = CN.Execute("SELECT StringField, IntField FROM Test WHERE IntField = 99 ORDER BY IntField")

    %>BOF: <%=RS.BOF%><br />
<%
    While not RS.Eof
        %>Value: <%=RS("StringField")%> / <%=RS("IntField")%><br>
        BOF: <%=RS.BOF%><br />
<%
        RS.MoveNext
    Wend

    %>BOF: <%=RS.BOF%><br />
<%

    CN.Close

    %>State: <%=CN.State%><br>
    <%
%>

The End.
