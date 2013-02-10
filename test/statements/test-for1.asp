Test For statement:
<HR>

Dim i, j <% Dim i, j  %><br>

for i = 0 to 2 <% for i = 0 to 2 %><br>
  i = <%= i %><br>
next <% next %><br>

for i = 2 to 0 step -1 <% for i = 2 to 0 step -1 %><br>
  i = <%= i %><br>
next <% next %><br>

for i = 0 to 2 step 2 <% for i = 0 to 2 step 2 %><br>
  i = <%= i %><br>
next <% next %><br>

for i = 0 to 2 step 3 <% for i = 0 to 2 step 3 %><br>
  i = <%= i %><br>
next <% next %><br>

for i = 0 to 10 <% for i = 0 to 10 %><br>
  i = <%= i %><br>
  if i &gt; 2 then <% if i > 2 then %><br>
    Exit For <% Exit For %><br>
  end if <% end if %><br>
next <% next %><br>
i = <%= i %><br>

for i = 0 to 1 <% for i = 0 to 1 %><br>
  for j = 0 to 1 <% for j = 0 to 1 %><br>
    i = <%= i %><br>
    j = <%= j %><br>
  next <% next %><br>
next <% next %><br>
i = <%= i %><br>
j = <%= j %><br>
