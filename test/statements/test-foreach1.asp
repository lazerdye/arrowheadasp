Test For Each statement:
<HR>

Dim A <% Dim A %><br>

A = Array(1, 2, 3) <% A = Array(1, 2, 3) %><br>

For Each Val In A <% For Each Val In A %><br>
  Val =           <%= Val %><br>
Next              <% Next %><br>

Set A = Server.CreateObject("Scripting.Dictionary")
	<% Set A = Server.CreateObject("Scripting.Dictionary") %><br>

Call A.Add("One", 1) <% Call A.Add("One", 1) %><br>
Call A.Add("Two", 2) <% Call A.Add("Two", 2) %><br>
Call A.Add("Three", 3) <% Call A.Add("Three", 3) %><br>

For Each Val In A <% For Each Val In A %><br>
  Val =           <%= Val %><br>
Next              <% Next %><br>

For Each Val In A <% For Each Val In A %><br>
  Val = <%= Val %><br>
  If A(Val) &gt; 1 then <% If A(Val) > 1 then %><br>
    Exit For <% Exit For %><br>
  End If <% End If %><br>
Next <% Next %><br>
