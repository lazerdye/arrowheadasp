<%
Dim Connect_URL, Connect_Username, Connect_Password

Connect_URL = "DRIVER=org.postgresql.Driver;URL=jdbc:postgresql://localhost/asptest"
Connect_Username = "test"
Connect_Password = "test"
Connect_FullDSN = Connect_URL & ";UID=" & Connect_Username &_
        ";PWD=" & Connect_Password

Function OpenConnection
    Dim CN

    Set CN = Server.CreateObject("ADODB.Connection")
    CN.Open Connect_URL, Connect_Username, Connect_Password

    Set OpenConnection = CN
End Function

Sub Output_Table(RS)
    While Not RS.EOF
        %>Value: <%=RS("StringField")%> / <%=RS("IntField")%><br>
        <%
        RS.MoveNext
    Wend
End Sub
%>
