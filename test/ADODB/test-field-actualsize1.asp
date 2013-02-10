<!--#include file="../config/ADODB.inc"-->
Here is the test of Field ActualSize.<br>
<%
    Dim CN, RS

    Set CN = Server.CreateObject("ADODB.Connection")
    CN.Open Connect_URL, Connect_Username, Connect_Password
    Set RS = CN.Execute("SELECT StringField, IntField FROM Test ORDER BY IntField")

    %>ActualSize("StringField"): <%=RS("StringField").ActualSize%><br>
    ActualSize("IntField"): <%=RS("IntField").ActualSize%><br>
<%

    CN.Close
%>

The End.
