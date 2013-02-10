<!-- Test LBound function.
-->

Dim A1(2), A2(3, 4), A3(5, 6, 7)
	<% Dim A1(2), A2(3, 4), A3(5, 6, 7) %><br>

LBound(A1) = <%= LBound(A1) %><br>
LBound(A2) = <%= LBound(A2) %><br>
LBound(A3) = <%= LBound(A3) %><br>
LBound(A1, 1) = <%= LBound(A1, 1) %><br>
LBound(A2, 1) = <%= LBound(A2, 1) %><br>
LBound(A3, 1) = <%= LBound(A3, 1) %><br>
LBound(A2, 2) = <%= LBound(A2, 2) %><br>
LBound(A3, 2) = <%= LBound(A3, 2) %><br>
LBound(A3, 3) = <%= LBound(A3, 3) %><br>
