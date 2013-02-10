<!--#include file="../config/ADODB.inc"-->
Here is the test of Field Name.<br>
<%
    Dim CN, RS

    Set CN = Server.CreateObject("ADODB.Connection")
    CN.Open Connect_URL, Connect_Username, Connect_Password
    Set RS = CN.Execute("SELECT StringField, IntField FROM Test ORDER BY IntField")

    %>Name("StringField"): <%=RS("StringField").Name%><br>
    Name("IntField"): <%=RS("IntField").Name%><br>
<%

    CN.Close
%>

The End.
