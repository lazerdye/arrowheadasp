<!--#include file="../config/ADODB.inc"-->
Test Active Connection 2<br>
<%
    Dim CN, CMD

    Set CMD = Server.CreateObject("ADODB.Command")
    CMD.ActiveConnection = Connect_FullDSN
    CMD.CommandText = "SELECT StringField, IntField FROM Test ORDER BY IntField"
    Set RS = CMD.Execute
    Call Output_Table(RS)
%>
