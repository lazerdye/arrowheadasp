<!--#include file="../config/ADODB.inc"-->
Here is the test of Field Type.<br>
<%
    Dim CN, RS

    Set CN = Server.CreateObject("ADODB.Connection")
    CN.Open Connect_URL, Connect_Username, Connect_Password
    Set RS = CN.Execute("SELECT StringField, IntField FROM Test ORDER BY IntField")

    %>Type("StringField"): <%=RS("StringField").Type%><br>
    Type("IntField"): <%=RS("IntField").Type%><br>
<%

    CN.Close
%>

The End.
