<!--#include file="../config/ADODB.inc"-->
Here is the test of RecordSet ActiveCommand.<br>
<%
    Dim CN, CMD, RS

    Set CN = Server.CreateObject("ADODB.Connection")
    CN.ConnectionString = Connect_FullDSN
    CN.Open
    Set CMD = Server.CreateObject("ADODB.Command")
    Set CMD.ActiveConnection = CN
    CMD.CommandText = "SELECT StringField, IntField FROM Test ORDER BY IntField"
    Set RS = CMD.Execute

    %>ActiveCommand.CommandText: <%=RS.ActiveCommand.CommandText %><br>
<%

    Call Output_Table(RS)

    CN.Close
%>

The End.
