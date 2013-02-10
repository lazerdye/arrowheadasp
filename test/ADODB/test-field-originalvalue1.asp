<!--#include file="../config/ADODB.inc"-->
Here is the test of RecordSet AddNew.<br>
<%
    Dim CN, RS

    Set CN = Server.CreateObject("ADODB.Connection")
    CN.Open Connect_URL, Connect_Username, Connect_Password
    Set RS = Server.CreateObject("ADODB.RecordSet")
    RS.Open "SELECT StringField, IntField FROM Test ORDER BY IntField", CN, 1, 2

    RS.Move(5)
    RS.Fields("StringField") = "New Value"
    %>Value: <%=RS.Fields("StringField")%><br>
    OriginalValue: <%=RS.Fields("StringField").OriginalValue%><br>
<%
    RS.CancelUpdate

    CN.Close

%>

The End.
