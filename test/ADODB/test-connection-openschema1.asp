<!--#include file="../config/ADODB.inc"-->
Here is the test of opening a schema.<br>
<%
    Dim CN, RS, adSchemaColumns
    
    adSchemaColumns = 4

    Set CN = Server.CreateObject("ADODB.Connection")
    CN.Open Connect_URL, Connect_Username, Connect_Password
    Set RS = CN.OpenSchema(adSchemaColumns)

    While not RS.Eof
        if (RS("TABLE_NAME") = "Test") then
            %>Column Name: <%=RS("COLUMN_NAME")%><br>
            <%
        end if
        RS.MoveNext
    Wend

    CN.Close
%>

The End.
