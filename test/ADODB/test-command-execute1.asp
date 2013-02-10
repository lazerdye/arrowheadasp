<!--#include file="../config/ADODB.inc"-->
Test Execute 1<br>
<%
    Dim CN, CMD, RecordsEffected

    Set CN = Server.CreateObject("ADODB.Connection")
    CN.ConnectionString = Connect_FullDSN
    CN.Open
    Set CMD = Server.CreateObject("ADODB.Command")
    Set CMD.ActiveConnection = CN
    CMD.CommandText = "UPDATE Test SET IntField = 2 WHERE StringField = 'Second Row'"
    RecordsEffected = 0
    Call CMD.Execute (RecordsEffected)
    %>Records Effected: <%=RecordsEffected%><br>
    <%
%>
