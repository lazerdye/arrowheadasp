<!-- Test CBOOL function.
	If expression is zero, return FALSE.
    If expression is the string "TRUE" (case insensitive) return TRUE
    If expression is the string "FALSE" (case insensitive) return FALSE
	If expression is non-zero, return TRUE
-->

CBOOL(0) = <%=CBOOL(0)%><br>
CBOOL(1) = <%=CBOOL(1)%><br>
CBOOL("TRUE") = <%=CBOOL("TRUE")%><br>
CBOOL("true") = <%=CBOOL("true")%><br>
CBOOL("FALSE") = <%=CBOOL("FALSE")%><br>
CBOOL("false") = <%=CBOOL("false")%><br>
CBOOL(2-2) = <%=CBOOL(2-2)%><br>
CBOOL("12") = <%=CBOOL("12")%><br>
