Test string concat operator:
<hr>

"a" &amp; "b" =           <%= "a" & "b" %><br>
"a" &amp; Null =          <%= "a" & Null %><br>
Null &amp; "b" =          <%= Null & "b" %><br>
IsNull(Null &amp; Null) = <%= IsNull(Null & Null) %><br>
"a" &amp; Empty =         <%= "a" & Empty %><br>
Empty &amp; "b" =         <%= Empty & "b" %><br>
Empty &amp; Empty =       <%= Empty & Empty %><br>

