<!--#include file="../config/ADODB.inc"-->
Here is the test of RecordSet CancelUpdate.<br>
<%
    Dim CN, RS

    Set CN = Server.CreateObject("ADODB.Connection")
    CN.Open Connect_URL, Connect_Username, Connect_Password
    Set RS = Server.CreateObject("ADODB.RecordSet")
    RS.Open "SELECT StringField, IntField FROM Test WHERE IntField = 99 ORDER BY IntField", CN, 2, 2

    RS.AddNew
    RS.Fields(0) = "New String Field"
    RS.Fields(1) = 99
    RS.CancelUpdate

    RS.Close

    Set RS = CN.Execute("SELECT StringField, IntField FROM Test ORDER BY IntField")
    Call Output_Table(RS)
    RS.Close

    CN.Close

%>

The End.
