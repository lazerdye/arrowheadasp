<!-- Test UBound function.
-->

Dim A1(2), A2(3, 4), A3(5, 6, 7)
	<% Dim A1(2), A2(3, 4), A3(5, 6, 7) %><br>

UBound(A1) = <%= UBound(A1) %><br>
UBound(A2) = <%= UBound(A2) %><br>
UBound(A3) = <%= UBound(A3) %><br>
UBound(A1, 1) = <%= UBound(A1, 1) %><br>
UBound(A2, 1) = <%= UBound(A2, 1) %><br>
UBound(A3, 1) = <%= UBound(A3, 1) %><br>
UBound(A2, 2) = <%= UBound(A2, 2) %><br>
UBound(A3, 2) = <%= UBound(A3, 2) %><br>
UBound(A3, 3) = <%= UBound(A3, 3) %><br>
