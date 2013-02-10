<!--#include file="../config/ADODB.inc"-->
Here is the test of Fields.Count method.<br>
<%
    Dim CN, RS

    Set CN = Server.CreateObject("ADODB.Connection")
    CN.Open Connect_URL, Connect_Username, Connect_Password
    Set RS = CN.Execute("SELECT IntField, StringField, " &_
            "NullableStringField, NullableIntField " &_
            "FROM test WHERE IntField = 2")

    %>Count = <%=RS.Fields.Count%><br />
<%  for i = 0 to RS.Fields.Count - 1
        %>Field: <%=LCASE(RS.Fields(i).Name)%><br />
        Value: <%=Rs.Fields(i)%><br />
<%  next

    CN.Close
%>

