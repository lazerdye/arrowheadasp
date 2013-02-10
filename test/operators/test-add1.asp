Test Addition operator:
<hr>

1 + 1 = <%= 1 + 1 %><br>
"1" + 1 = <%= "1" + 1 %><br>
1 + "1" = <%= 1 + "1" %><br>
"1" + "1" = <%= "1" + "1" %><br>
1.5 + 1.5 = <%= 1.5 + 1.5 %><br>
IsNull(Null + 1) = <%= IsNull(Null + 1) %><br>
IsNull(1 + Null) = <%= IsNull(1 + Null) %><br>
IsNull(Null + Null) = <%= IsNull(Null + Null) %><br>
CDate("12/10/99") + 1 = <%= CDate("12/10/99") + 1 %><br>
CDate("12/10/99") + 22 = <%= CDate("12/10/99") + 22 %><br>
1 + CDate("12/31/02") = <%= 1 + CDate("12/31/02") %><br>
Empty + 1 = <%= Empty + 1 %><br>
1 + Empty = <%= 1 + Empty %><br>
Empty + Empty = <%= Empty + Empty %><br>


