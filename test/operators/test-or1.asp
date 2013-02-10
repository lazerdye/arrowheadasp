Test logical OR operator:
<hr>

True or True =         <%= True or True %><br>
True or False =        <%= True or False %><br>
True or Null =         <%= True or Null %><br>
False or True =        <%= False or True %><br>
False or False =       <%= False or False %><br>
IsNull(False or Null) =<%= IsNull(False or Null) %><br>
Null or True =         <%= Null or True %><br>
IsNull(Null or False) =<%= IsNull(Null or False) %><br>
IsNull(Null or Null) = <%= IsNull(Null or Null) %><br>
1 or 1 =               <%= 1 or 1 %><br>
&amp;HFF or &amp;H10 = <%= &HFF or &H10 %><br>
&amp;H33 or &amp;H11 = <%= &H33 or &H11 %><br>
Null or &amp;HFF =     <%= NULL or &HFF %><br>
Null or &amp;H01 =     <%= NULL or &H01 %><br>
IsNull(Null or &amp;H00) =     <%= IsNull(NULL or &H00) %><br>
TRUE or &amp;H11 = <%= TRUE or &H11 %><br>
FALSE or &amp;H11 = <%= FALSE or &H11 %><br>
&amp;H11 or TRUE = <%= &H11 or TRUE %><br>
&amp;H11 or FALSE = <%= &H11 or FALSE %><br>

