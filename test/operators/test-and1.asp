Test logical AND operator:
<hr>

True and True =         <%= True and True %><br>
True and False =        <%= True and False %><br>
IsNull(True and Null) =	<%= IsNull(True and Null) %><br>
False and True =        <%= False and True %><br>
False and False =       <%= False and False %><br>
False and Null =        <%= False and Null %><br>
IsNull(Null and True) = <%= IsNull(Null and True) %><br>
Null and False =        <%= Null and False %><br>
IsNull(Null and Null) = <%= IsNull(Null and Null) %><br>
1 and 1 =               <%= 1 and 1 %><br>
&amp;HFF and &amp;H10 = <%= &HFF and &H10 %><br>
&amp;H33 and &amp;H11 = <%= &H33 and &H11 %><br>
IsNull(Null and &amp;HFF) =     <%= IsNull(NULL and &HFF) %><br>
IsNull(Null and &amp;H01) =     <%= IsNull(NULL and &H01) %><br>
Null and &amp;H00 =     <%= NULL and &H00 %><br>
&amp;H33 and TRUE = <%= &H33 and TRUE %><br>
&amp;H33 and FALSE = <%= &H33 and FALSE %><br>
TRUE and &amp;H33 = <%= TRUE and &H33 %><br>
FALSE and &amp;H33 = <%= FALSE and &H33 %><br>

