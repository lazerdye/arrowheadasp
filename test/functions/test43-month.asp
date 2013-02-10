<!-- Test Month function.
-->

Month("6/25/75") = 
	<%= Month("6/25/75") %><br>
Month("1/25/75") = 
	<%= Month("1/25/75") %><br>
Month("12/25/75") = 
	<%= Month("12/25/75") %><br>
Month("June 25, 1975") = 
	<%= Month("June 25, 1975") %><br>
IsNull(Month(NULL)) = 
	<%= IsNull(Month(NULL)) %><br>
