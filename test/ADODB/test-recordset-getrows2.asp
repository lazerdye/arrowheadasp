<!--#include file="../config/ADODB.inc"-->
Here is the test of RecordSet GetRows.<br>
<%
    Dim CN, RS, Rows

    Set CN = Server.CreateObject("ADODB.Connection")
    CN.Open Connect_URL, Connect_Username, Connect_Password
    Set RS = Server.CreateObject("ADODB.RecordSet")
    RS.Open "SELECT StringField, IntField FROM Test ORDER BY IntField", CN

    RS.Move(3)

    Rows = RS.GetRows(5)
    for j = 0 to UBOUND(Rows, 2)
        for i = 0 to UBOUND(Rows, 1)
            %><%=i%>,<%=j%>=<%=Rows(i, j)%><br />
<%
        next
    next

    RS.Close

    CN.Close

%>

The End.
