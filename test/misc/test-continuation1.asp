Here is a test of different kinds of continuations.

<%
Response.Write _
  ("Hello there.<br>")

REM Response. _
REM    Write("How are you?<br>")

Response _
    .Write("I am fine, what about you?<br>")

REM Response _
REM     . _
REM     Write("I could be better.<br>")

Response.Write _

    ("Oh, why is that?<br>")

Response.Write("My house " &_
    "fell down today.<br>")

Response.Write("Oh " _
    
    & _

    "that is sad.<br>")

%>
