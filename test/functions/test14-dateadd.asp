<!-- Test DateAdd function.
-->

DateAdd("yyyy", 1, "12-Jan-01") = <%= DateAdd("yyyy", 1, "12-Jan-01") %><br>
DateAdd("yyyy", -1, "12-Jan-00") = <%= DateAdd("yyyy", -1, "12-Jan-00") %><br>
DateAdd("q", 1, "12-Jan-01") = <%= DateAdd("q", 1, "12-Jan-01") %><br>
DateAdd("q", -1, "12-Jan-01") = <%= DateAdd("q", -1, "12-Jan-01") %><br>
DateAdd("m", 1, "12-Jan-01") = <%= DateAdd("m", 1, "12-Jan-01") %><br>
DateAdd("m", -1, "12-Jan-01") = <%= DateAdd("m", -1, "12-Jan-01") %><br>
DateAdd("y", 1, "12-Jan-01") = <%= DateAdd("y", 1, "12-Jan-01") %><br>
DateAdd("y", -1, "1-Jan-01") = <%= DateAdd("y", -1, "1-Jan-01") %><br>
DateAdd("d", 1, "12-Jan-01") = <%= DateAdd("d", 1, "12-Jan-01") %><br>
DateAdd("d", -1, "1-Jan-01") = <%= DateAdd("d", -1, "1-Jan-01") %><br>
DateAdd("w", 1, "12-Jan-01") = <%= DateAdd("w", 1, "12-Jan-01") %><br>
DateAdd("w", -1, "1-Jan-01") = <%= DateAdd("w", -1, "1-Jan-01") %><br>
DateAdd("ww", 1, "12-Jan-01") = <%= DateAdd("ww", 1, "12-Jan-01") %><br>
DateAdd("ww", -1, "1-Jan-01") = <%= DateAdd("ww", -1, "1-Jan-01") %><br>
DateAdd("h", 1, "12-Jan-01 01:23:45") = 
	<%= DateAdd("h", 1, "12-Jan-01 01:23:45") %><br>
DateAdd("h", -1, "1-Jan-01 00:23:45") = 
	<%= DateAdd("h", -1, "1-Jan-01 00:23:45") %><br>
DateAdd("n", 1, "12-Jan-01 01:23:45") = 
	<%= DateAdd("n", 1, "12-Jan-01 01:23:45") %><br>
DateAdd("n", -1, "1-Jan-01 00:00:30") = 
	<%= DateAdd("n", -1, "1-Jan-01 00:00:30") %><br>
DateAdd("s", 1, "12-Jan-01 01:23:45") = 
	<%= DateAdd("s", 1, "12-Jan-01 01:23:45") %><br>
DateAdd("s", -2, "1-Jan-01 00:00:01") = 
	<%= DateAdd("s", -2, "1-Jan-01 00:00:01") %><br>

DateAdd("m", 1, "31-Jan-95") = <%= DateAdd("m", 1, "31-Jan-95") %><br>
DateAdd("m", 1, "31-Jan-96") = <%= DateAdd("m", 1, "31-Jan-96") %><br>
