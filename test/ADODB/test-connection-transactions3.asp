<!--#include file="../config/ADODB.inc"-->
<%
Dim CN
Sub List_Table_Contents()
    Set RS = CN.Execute("SELECT StringField, IntField FROM Test ORDER BY IntField")
    while not RS.EOF
        %>StringField: "<%=RS("StringField")%>", IntField: "<%=RS("IntField")%>"<br>
<%
        RS.MoveNext
    wend
End Sub
%>

Testing nested transactions...

Opening DB...
<%
Set CN = OpenConnection()
%>

List what's in the table initially...
<%
Call List_Table_Contents()
%>

Begin a transaction...
ID: <%= CN.BeginTrans() %>

Add some elements to table...

<%
CN.Execute("INSERT INTO Test(StringField, IntField) VALUES ('A', 1);")
CN.Execute("INSERT INTO Test(StringField, IntField) VALUES ('B', 2);")
%>

Begin another transaction...
ID: <%= CN.BeginTrans() %>

Add some more elements to the table...

<%
CN.Execute("INSERT INTO Test(StringField, IntField) VALUES ('C', 1);")
CN.Execute("INSERT INTO Test(StringField, IntField) VALUES ('D', 2);")
%>

Begin a third transaction...
ID: <%= CN.BeginTrans() %>

Add even more elements to the table...

<%
CN.Execute("INSERT INTO Test(StringField, IntField) VALUES ('E', 1);")
CN.Execute("INSERT INTO Test(StringField, IntField) VALUES ('F', 2);")
%>

List what's in the table now...
<%
Call List_Table_Contents()
%>

Rollback 1...

<%
CN.RollbackTrans()
%>

List what's in the table after rollback...
<%
Call List_Table_Contents()
%>

Rollback 2...

<%
CN.RollbackTrans()
%>

List what's in the table after rollback...
<%
Call List_Table_Contents()
%>

Rollback 3...

<%
CN.RollbackTrans()
%>

List what's in the table after rollback...
<%
Call List_Table_Contents()
%>

