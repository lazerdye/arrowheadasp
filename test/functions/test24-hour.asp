<!-- Test Hour function.
-->

Hour("12:23:01") = 
	<%= Hour("12:23:01") %><br>
Hour("18:23:01") = 
	<%= Hour("18:23:01") %><br>
Hour("3:23:01pm") = 
	<%= Hour("3:23:01pm") %><br>
IsNull(Hour(NULL)) = 
	<%= IsNull(Hour(NULL)) %><br>
