<!--#include file="../config/ADODB.inc"-->
Test of Connection.Execute<br>
<%
    Dim RecordsEffected, RS

    Set CN = OpenConnection()

    CN.BeginTrans()

    RecordsEffected = 0

    %>RecordsEffected(before) = <%=RecordsEffected%><br>
    <%

    Call CN.Execute("UPDATE Test SET StringField = 'Hi'", RecordsEffected)

    %>RecordsEffected(after) = <%=RecordsEffected%><br>
    <%

    CN.RollbackTrans()
    
    CN.Close
%>

The End.
