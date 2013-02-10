<!--#include file="../config/ADODB.inc"-->
Here is the test of basic ADODB functions.<br>
<%
    Set CN = OpenConnection()

    %>Attributes: <%=CN.Attributes%><br>
    CommandTimeout: <%=CN.CommandTimeout%><br>
    ConnectionString: <%=CN.ConnectionString%><br>
    ConnectionTimeout: <%=CN.ConnectionTimeout%><br>
    CursorLocation: <%=CN.CursorLocation%><br>
    DefaultDatabase: <%=CN.DefaultDatabase%><br>
    IsolationLevel: <%=CN.IsolationLevel%><br>
    Mode: <%=CN.Mode%><br>
    Provider: <%=CN.Provider%><br>
    State: <%=CN.State%><br>
    Version: <%=CN.Version%><br>
    <%
    CN.Close
%>

The End.
