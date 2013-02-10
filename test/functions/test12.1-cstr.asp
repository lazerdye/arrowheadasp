<!-- Test CStr function.
	If input is boolean, returns string containing "true" or "false".
	If input is a date, returns string containing short-date format
		of date.
	If input is NULL, a run-time error occurs.
	If input is empty, a zero-length string is returned.
	If input is an error, the word "Error" followed by the error number
		is returned.
	If input is any numeric value, the string representation of that numeric
		value is returned.
-->

CSTR(TRUE) = <%=CSTR(TRUE)%><br>
CSTR(FALSE) = <%=CSTR(FALSE)%><br>
CSTR(CDate("6/12/75")) = <%=CSTR(CDate("6/12/75"))%><br>
CSTR(Undefined) = <%=CSTR(Undefined)%><br>
CSTR(12+3) = <%=CSTR(12+3)%><br>
