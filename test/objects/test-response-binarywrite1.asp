Test binary write.

Request.TotalBytes = <%=Request.TotalBytes%><br>
Result = Request.BinaryRead(Request.TotalBytes)
    <% Result = Request.BinaryRead(Request.TotalBytes) %><br>
Response.BinaryWrite(Result)
    <% Response.BinaryWrite(Result) %><br>
