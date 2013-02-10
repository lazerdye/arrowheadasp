<!--#include file="../config/ADODB.inc"-->
Test Active Connection 1<br>
<%
    Dim CN, CMD

    Set CN = Server.CreateObject("ADODB.Connection")
    CN.ConnectionString = Connect_FullDSN
    CN.Open
    Set CMD = Server.CreateObject("ADODB.Command")
    Set CMD.ActiveConnection = CN
    CMD.CommandText = "SELECT StringField, IntField FROM Test ORDER BY IntField"
    Set RS = CMD.Execute
    Call Output_Table(RS)
%>
