<!--#include file="../config/ADODB.inc"-->
Here is the test of the RecordSet paging features.<br>
<%
    Dim RS

    Set RS = Server.CreateObject("ADODB.RecordSet")
    RS.CursorLocation = 3
    RS.CursorType = 3
    RS.PageSize = 5
    RS.CacheSize = 5
    RS.Open "SELECT StringField, IntField FROM Test ORDER BY IntField", _
        Connect_FullDSN

    %>AbsolutePosition: <%=RS.AbsolutePosition%><br />
    AbsolutePage: <%=RS.AbsolutePage%><br />
    PageCount: <%=RS.PageCount%><br />
<%
    While not RS.Eof
        %>Value: <%=RS("StringField")%> / <%=RS("IntField")%><br>
        AbsolutePosition: <%=RS.AbsolutePosition%><br />
        AbsolutePage: <%=RS.AbsolutePage%><br />
        PageCount: <%=RS.PageCount%><br />
<%
        RS.MoveNext
    Wend

    %>AbsolutePosition: <%=RS.AbsolutePosition%><br />
    AbsolutePage: <%=RS.AbsolutePage%><br />
    PageCount: <%=RS.PageCount%><br />
<%

    RS.Close
%>

The End.
