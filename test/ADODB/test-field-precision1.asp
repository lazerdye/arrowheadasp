<!--#include file="../config/ADODB.inc"-->
Here is the test of Field Precision.<br>
<%
    Dim CN, RS

    Set CN = Server.CreateObject("ADODB.Connection")
    CN.Open Connect_URL, Connect_Username, Connect_Password
    Set RS = CN.Execute("SELECT StringField, IntField FROM Test ORDER BY IntField")

    %>Precision("StringField"): <%=RS("StringField").Precision%><br>
    Precision("IntField"): <%=RS("IntField").Precision%><br>
<%

    CN.Close
%>

The End.
