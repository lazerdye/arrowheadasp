<!-- Test CLng function.
	Rounds to nearest number. If fractional part is exactly 0.5,
	rounds to the nearest even number.
-->

CLNG("12") = <%=CLNG(12)%><br>
CLNG(0.1) = <%=CLNG(0.1)%><br>
CLNG(0.5) = <%=CLNG(0.5)%><br>
CLNG(0.7) = <%=CLNG(0.7)%><br>
CLNG(1.0) = <%=CLNG(1.0)%><br>
CLNG(1.5) = <%=CLNG(1.5)%><br>
CLNG(1.7) = <%=CLNG(1.7)%><br>
CLNG(2.5) = <%=CLNG(2.5)%><br>
CLNG(3.5) = <%=CLNG(3.5)%><br>
CLNG(-0.5) = <%=CLNG(-0.5)%><br>
CLNG(-1.5) = <%=CLNG(-1.5)%><br>
CLNG(-2.5) = <%=CLNG(-2.5)%><br>
CLNG(TRUE) = <%=CLNG(TRUE)%><br>
CLNG(FALSE) = <%=CLNG(FALSE)%><br>
