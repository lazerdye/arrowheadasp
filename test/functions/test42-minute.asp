<!-- Test Minute function.
-->

Minute("12:23:01") = 
	<%= Minute("12:23:01") %><br>
Minute("12:00:01") = 
	<%= Minute("12:00:01") %><br>
Minute("12:59:01") = 
	<%= Minute("12:59:01") %><br>
IsNull(Minute(NULL)) = 
	<%= IsNull(Minute(NULL)) %><br>
