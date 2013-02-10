Test logical XOR operator:
<hr>

True xor True =         <%= True xor True %><br>
True xor False =        <%= True xor False %><br>
IsNull(True xor Null) = <%= IsNull(True xor Null) %><br>
False xor True =        <%= False xor True %><br>
False xor False =       <%= False xor False %><br>
IsNull(False xor Null) =<%= IsNull(False xor Null) %><br>
IsNull(Null xor True) = <%= IsNull(Null xor True) %><br>
IsNull(Null xor False) =<%= IsNull(Null xor False) %><br>
IsNull(Null xor Null) = <%= IsNull(Null xor Null) %><br>
1 xor 1 =               <%= 1 xor 1 %><br>
&amp;HFF xor &amp;H10 = <%= &HFF xor &H10 %><br>
&amp;H33 xor &amp;H11 = <%= &H33 xor &H11 %><br>
IsNull(Null xor &amp;HFF) =     <%= IsNull(NULL xor &HFF) %><br>
IsNull(Null xor &amp;H01) =     <%= IsNull(NULL xor &H01) %><br>
IsNull(Null xor &amp;H00) =     <%= IsNull(NULL xor &H00) %><br>
&amp;11 xor TRUE = <%= &H11 xor TRUE %><br>
&amp;11 xor FALSE = <%= &H11 xor FALSE %><br>
TRUE xor &amp;11 = <%= TRUE xor &H11 %><br>
FALSE xor &amp;11 = <%= FALSE xor &H11 %><br>

