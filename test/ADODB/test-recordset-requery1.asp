<!--#include file="../config/ADODB.inc"-->
Here is the test of RecordSet Requery.<br>
<%
    Dim CN, RS, RS2

    Set CN = Server.CreateObject("ADODB.Connection")
    CN.Open Connect_URL, Connect_Username, Connect_Password
    Set RS = Server.CreateObject("ADODB.RecordSet")
    RS.Open "SELECT StringField, IntField FROM Test ORDER BY IntField", CN
    Call Output_Table(RS)

    Set RS2 = Server.CreateObject("ADODB.RecordSet")
    RS2.Open "SELECT StringField, IntField FROM Test ORDER BY IntField", CN, 1, 2

    RS2.Move(5)
    RS2.Fields("StringField") = "New Value"
    RS2.Update

    RS2.Close
    
    RS.Requery
    Call Output_Table(RS)
    RS.Close

    CN.Execute("UPDATE Test SET StringField = 'Sixth Row' WHERE IntField = 6")


    CN.Close

%>

The End.
