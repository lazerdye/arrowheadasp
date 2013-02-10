Test Do Procedure:
<HR>

Dim i, j <% Dim i, j  %><br>

i = 1 <% i = 1 %><br>
Do While i < 3 <% Do While i < 3 %><br>
  i = <%= i %><br>
  i = i + 1 <% i = i + 1 %><br>
Loop <% Loop %><br>

i = 3 <% i = 3 %><br>
Do Until i < 1 <% Do Until i < 1 %><br>
  i = <%= i %><br>
  i = i - 1 <% i = i - 1 %><br>
Loop <% Loop %><br>

i = 1 <% i = 1 %><br>
Do <% Do %><br>
  i = <%= i %><br>
  i = i + 1 <% i = i + 1 %><br>
Loop While i < 3 <% Loop While i < 3 %><br>

i = 3 <% i = 3 %><br>
Do <% Do %><br>
  i = <%= i %><br>
  i = i - 1 <% i = i - 1 %><br>
Loop Until i < 1 <% Loop Until i < 1 %><br>

i = 1 <% i = 1 %><br>
Do While i < 2 <% Do while i < 2 %><br>
  j = 1 <% j = 1 %><br>
  Do While j < 10 <% Do while j < 10 %><br>
    if j &gt; 2 then <% if j > 2 then %><br>
      Exit Loop <% Exit Do %><br>
    end if <% end if %><br>
    j = j + 1 <% j = j + 1 %><br>
  Loop <% Loop %><br>
  i = i + 1 <% i = i + 1 %><br>
  i = <%= i %><br>
  j = <%= j %><br>
Loop <% Loop %><br>
