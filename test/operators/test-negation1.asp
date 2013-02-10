Test Subtraction operator:
<hr>

1 - 1 = <%= 1 - 1 %><br>
"1" - 1 = <%= "1" - 1 %><br>
1 - "1" = <%= 1 - "1" %><br>
"1" - "1" = <%= "1" - "1" %><br>
1.5 - 1.5 = <%= 1.5 - 1.5 %><br>
- 2 = <%= - 2 %><br>
- "3" = <%= - "3" %><br>
- 4.5 = <%= - 4.5 %><br>
CDate("1/1/01") - 10 = <%= CDate("1/1/01") - 10 %><br>
CDate("1/10/01") - CDate("1/1/01") =
	<%= CDate("1/10/01") - CDate("1/1/01") %><br>
CDate("1/10/01") - CDate("1/20/01") =
	<%= CDate("1/10/01") - CDate("1/20/01") %><br>
IsNull(Null - 1) = <%= IsNull(Null - 1) %><br>
IsNull(1 - Null) = <%= IsNull(1 - Null) %><br>
IsNull(Null - Null) = <%= IsNull(Null - Null) %><br>
IsNull(- Null) = <%= IsNull(- Null) %><br>
Empty - 1 = <%= Empty - 1 %><br>
1 - Empty = <%= 1 - Empty %><br>
Empty - Empty = <%= Empty - Empty %><br>
- Empty = <%= - Empty %><br>


