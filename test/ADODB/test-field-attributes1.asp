<!--#include file="../config/ADODB.inc"-->
Here is the test of Field Attributes.<br>
<%
    Dim CN, RS

    Set CN = Server.CreateObject("ADODB.Connection")
    CN.Open Connect_URL, Connect_Username, Connect_Password
    Set RS = CN.Execute("SELECT StringField, IntField FROM Test ORDER BY IntField")

    %>Attributes("StringField"): <%=RS("StringField").Attributes%><br>
    Attributes("IntField"): <%=RS("IntField").Attributes%><br>
<%

    CN.Close
%>

The End.
