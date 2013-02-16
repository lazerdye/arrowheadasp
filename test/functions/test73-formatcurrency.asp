<!-- Test FormatDateTime function.
-->

Dim TheDollars <% Dim TheDollars %><br>

TheDollars = "-0.9876" <% TheDollars = "-0.9876" %><br>

<%
FOR lead = 0 to -2 step -1
	FOR neg= 0 to -2 step -1
	  FOR group = -0 to -2 step -1
		Response.Write("FormatCurrencyTheDollars,4," & lead & "," & neg & "," & group &") = ")
		Response.Write(FormatCurrency(TheDollars,4,lead,neg,group) & "<BR>")
	  NEXT
	NEXT
NEXT
%>

FormatCurrency(TheDollars) =
	<%= FormatCurrency(TheDollars) %><br>

FormatCurrency(TheDollars, 2) =
	<%= FormatCurrency(TheDollars, 2) %><br>

FormatCurrency(TheDollars, 4) =
	<%= FormatCurrency(TheDollars, 4) %><br>

FormatCurrency(TheDollars, 2, true) =
	<%= FormatCurrency(TheDollars, 2, true) %><br>

FormatCurrency(TheDollars, 2, false) =
	<%= FormatCurrency(TheDollars, 2, false) %><br>

FormatCurrency(TheDollars, 2, true, true) =
	<%= FormatCurrency(TheDollars, 2, true, true) %><br>

FormatCurrency(TheDollars, 2, true, false) =
	<%= FormatCurrency(TheDollars, 2, true, false) %><br>

TheDollars = "-123456789" <% TheDollars = "-123456789" %><br>

<%
FOR lead = 0 to -2 step -1
	FOR neg= 0 to -2 step -1
	  FOR group = -0 to -2 step -1
		Response.Write("FormatCurrencyTheDollars,2," & lead & "," & neg & "," & group &") = ")
		Response.Write(FormatCurrency(TheDollars,2,lead,neg,group) & "<BR>")
	  NEXT
	NEXT
NEXT
%>

