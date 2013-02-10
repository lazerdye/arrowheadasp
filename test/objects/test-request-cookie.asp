Test setting of cookies
<hr>

<%
for each Cookie in Request.Cookies
    %>Cookies("<%=Cookie%>") = <%=Request.Cookies(Cookie)%><br>
<%
    for each SubCookie in Request.Cookies(Cookie) 
        %>Cookies("<%=Cookie%>")("<%=SubCookie%>") = <%
            %><%= Request.Cookies(Cookies)(SubCookie) %><br>
<%
    next
next
%>
