<!--#include file="../config/ADODB.inc"-->
Here is the test of Field DefinedSize.<br>
<%
    Dim CN, RS

    Set CN = Server.CreateObject("ADODB.Connection")
    CN.Open Connect_URL, Connect_Username, Connect_Password
    Set RS = CN.Execute("SELECT StringField, IntField FROM Test ORDER BY IntField")

    %>DefinedSize("StringField"): <%=RS("StringField").DefinedSize%><br>
    DefinedSize("IntField"): <%=RS("IntField").DefinedSize%><br>
<%

    CN.Close
%>

The End.
