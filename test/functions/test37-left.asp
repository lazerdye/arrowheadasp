<!-- Test Left function.
-->

Left("abcdef", 1) = <%= Left("abcdef", 1) %><br>
Left("abcdef", 0) = <%= Left("abcdef", 0) %><br>
Left("abcdef", 8) = <%= Left("abcdef", 8) %><br>
IsNull(Left(Null, 2)) = <%= IsNull(Left(Null, 2)) %><br>
