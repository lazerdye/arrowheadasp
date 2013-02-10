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

Testing transactions with commit.

Opening DB...
<%
Set CN = OpenConnection()
%>

List what's in the table initially...
<%
Call List_Table_Contents()
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
Call List_Table_Contents()
%>

Commit...

<%
CN.CommitTrans()
%>

List what's in the table after commit...
<%
Call List_Table_Contents()
%>

Clean up after ourselves...
<%
CN.Execute("DELETE FROM Test WHERE StringField IN ('A','B');")
%>
