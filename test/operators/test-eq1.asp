Test Equality Operator
<br>

1 = 1 = <%= 1 = 1 %><br>
1 = 2 = <%= 1 = 2 %><br>
2 = 1 = <%= 2 = 1 %><br>
"0" = 0 = <%= "0" = 0 %><br>
"1" = 1 = <%= "1" = 1 %><br>
1 = "1" = <%= 1 = "1" %><br>
"a" = "a" = <%= "a" = "a" %><br>
"a" = "b" = <%= "a" = "b" %><br>
"b" = "a" = <%= "b" = "a" %><br>
"abba" = "ab" = <%= "abba" = "ab" %><br>
"ab" = "abba" = <%= "ab" = "abba" %><br>
0 = "0" = <%= 0 = "0" %><br>
Empty = "a" = <%= Empty = "a" %><br>
"a" = Empty = <%= "a" = Empty %><br>
Empty = "" = <%= Empty = "" %><br>
"" = Empty = <%= "" = Empty %><br>
Empty = 0 = <%= Empty = 0 %><br>
0 = Empty = <%= "" = Empty %><br>
Empty = Empty = <%= Empty = Empty %><br>
IsNull(Null = "a") = <%= IsNull(Null = "a") %><br>
IsNull("a" = Null) = <%= IsNull("a" = Null) %><br>
IsNull(Null = Null) = <%= IsNull(Null = Null) %><br>
IsNull(Null = "") = <%= IsNull(Null = "") %><br>
IsNull("" = Null) = <%= IsNull("" = Null) %><br>
IsNull(Null = 0) = <%= IsNull(Null = 0) %><br>
IsNull(0 = Null) = <%= IsNull(0 = Null) %><br>
