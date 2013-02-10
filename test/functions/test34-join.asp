<!-- Test Join function.
-->

Dim A1, A2, A3
	<% Dim A1, A2, A3 %><br>

A1 = Array(1, 2, 3)
	<% A1 = Array(1, 2, 3) %><br>

A2 = Array("Hello", "There")
	<% A2 = Array("Hello", "There") %><br>

A3 = Array()
	<% A3 = Array() %><br>

Join(A1) = <%= Join(A1) %><br>
Join(A2) = <%= Join(A2) %><br>
Join(A3) = <%= Join(A3) %><br>
Join(A1, ",") = <%= Join(A1, ",") %><br>
Join(A2, ",") = <%= Join(A2, ",") %><br>
Join(A3, ",") = <%= Join(A3, ",") %><br>
Join(A1, "cha") = <%= Join(A1, "cha") %><br>
Join(A2, "cha") = <%= Join(A2, "cha") %><br>
Join(A3, "cha") = <%= Join(A3, "cha") %><br>
