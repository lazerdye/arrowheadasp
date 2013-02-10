<!--#include file="../config/ADODB.inc"-->
Here is the test of RecordSet AddNew.<br>
<%
    Dim CN, RS

    Set CN = Server.CreateObject("ADODB.Connection")
    CN.Open Connect_URL, Connect_Username, Connect_Password
    Set RS = Server.CreateObject("ADODB.RecordSet")
    RS.Open "SELECT StringField, IntField FROM Test ORDER BY IntField", CN, 1, 2

    RS.Move(5)
    %>Orig StringField: <%=RS("StringField")%><br>
<%
    RS.Fields("StringField") = "New Value"
    RS.Update
    
    Set RS = CN.Execute("SELECT StringField, IntField FROM Test ORDER BY IntField")
    Call Output_Table(RS)
    RS.Close

    CN.Execute("UPDATE Test SET StringField = 'Sixth Row' WHERE IntField = 6")


    CN.Close

%>

The End.
