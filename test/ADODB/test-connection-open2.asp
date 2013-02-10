<!--#include file="../config/ADODB.inc"-->
Here is the test of basic ADODB functions.<br>
<%
    Dim CN, RS

    Set CN = Server.CreateObject("ADODB.Connection")
    CN.Open Connect_URL & ";UID=" & Connect_Username & ";PWD=" & Connect_Password
    Set RS = CN.Execute("SELECT StringField, IntField FROM Test ORDER BY IntField")

    While not RS.Eof
        %>Value: <%=RS("StringField")%> / <%=RS("IntField")%><br>
        <%
        RS.MoveNext
    Wend

    CN.Close
%>

The End.
