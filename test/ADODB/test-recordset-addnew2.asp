<!--#include file="../config/ADODB.inc"-->
Here is the test of RecordSet AddNew.<br>
<%
    Dim CN, RS

    Set CN = Server.CreateObject("ADODB.Connection")
    CN.Open Connect_URL, Connect_Username, Connect_Password
    Set RS = Server.CreateObject("ADODB.RecordSet")
    RS.Open "SELECT StringField, IntField FROM Test WHERE IntField = 99 ORDER BY IntField", CN, 2, 2

    RS.AddNew Array("StringField", "IntField"), Array("New String Field", 99)

    RS.Close

    Set RS = CN.Execute("SELECT StringField, IntField FROM Test ORDER BY IntField")
    Call Output_Table(RS)
    RS.Close

    CN.Execute("DELETE FROM Test WHERE IntField = 99")

    CN.Close

%>

The End.
