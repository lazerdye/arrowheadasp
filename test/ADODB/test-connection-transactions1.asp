<!--#include file="../config/ADODB.inc"-->
Here is a test of transactions.<br>
<%
Dim CN
Sub List_Table_Contents(CN)
    Set RS = CN.Execute("SELECT StringField, IntField FROM Test ORDER BY IntField")
    while not RS.EOF
        %>StringField: "<%=RS("StringField")%>", IntField: "<%=RS("IntField")%>"<br>
<%
        RS.MoveNext
    wend
End Sub
%>

Testing transactions.

Opening DB...
<%
Set CN = OpenConnection()
%>

List what's in the table initially...
<%
Call List_Table_Contents(CN)
%>

Begin a transaction...<br>
TransID: <%= CN.BeginTrans() %>

Add some elements to table...

<%
CN.Execute("INSERT INTO Test(StringField, IntField) VALUES ('A', 101);")
CN.Execute("INSERT INTO Test(StringField, IntField) VALUES ('B', 102);")
%>

List what's in the table now...
<%
Call List_Table_Contents(CN)
%>

Rollback...

<%
CN.RollbackTrans()
%>

List what's in the table after rollback...
<%
Call List_Table_Contents(CN)
%>

Close the database...
<%
CN.Close()
%>

