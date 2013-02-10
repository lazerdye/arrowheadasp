<!--#include file="../config/ADODB.inc"-->
Test of Connection.Execute, returning ResultSet<br>
<%
    Dim RS

    Set CN = OpenConnection()

    Set RS = CN.Execute("SELECT StringField, IntField FROM Test ORDER BY IntField")

    while not RS.EOF
        Response.Write("StringField = " & RS("StringField") & "<br>" & VBCrlf)
        Response.Write("IntField = " & RS("IntField") & "<br>" & VBCrlf)
        RS.MoveNext
    wend
    
    CN.Close
%>

The End.
