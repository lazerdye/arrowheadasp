<!-- Test FormatDateTime function.
-->

Dim TheDollars <% Dim TheDollars %><br>

TheDollars = "-0.9876" <% TheDollars = "-0.9876" %><br>

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

FormatCurrency(TheDollars, 2, true, true, 0) =
	<%= FormatCurrency(TheDollars, 2, true, true, 0) %><br>

FormatCurrency(TheDollars, 2, true, true, 3) =
	<%= FormatCurrency(TheDollars, 2, true, true, -1) %><br>
