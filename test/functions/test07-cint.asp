<!-- Test CInt function.
	Rounds to nearest number. If fractional part is exactly 0.5,
	rounds to the nearest even number.
-->

CINT("12") = <%=CINT(12)%><br>
CINT(0.1) = <%=CINT(0.1)%><br>
CINT(0.5) = <%=CINT(0.5)%><br>
CINT(0.7) = <%=CINT(0.7)%><br>
CINT(1.0) = <%=CINT(1.0)%><br>
CINT(1.5) = <%=CINT(1.5)%><br>
CINT(1.7) = <%=CINT(1.7)%><br>
CINT(2.5) = <%=CINT(2.5)%><br>
CINT(3.5) = <%=CINT(3.5)%><br>
CINT(-0.5) = <%=CINT(-0.5)%><br>
CINT(-1.5) = <%=CINT(-1.5)%><br>
CINT(-2.5) = <%=CINT(-2.5)%><br>
CINT(TRUE) = <%=CINT(TRUE)%><br>
CINT(FALSE) = <%=CINT(FALSE)%><br>
