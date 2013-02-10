<!-- Test FormatDateTime function.
-->

Dim TheDate <% Dim TheDate %><br>

TheDate = "12/31/05 13:34:56" <% TheDate = "12/31/05 13:34:56" %><br>

FormatDateTime(TheDate) =
	<%= FormatDateTime(TheDate) %><br>

FormatDateTime(TheDate, vbGeneralDate) =
	<%= FormatDateTime(TheDate, vbGeneralDate) %><br>

FormatDateTime(TheDate, vbShortDate) =
	<%= FormatDateTime(TheDate, vbShortDate) %><br>

FormatDateTime(TheDate, vbLongDate) =
	<%= FormatDateTime(TheDate, vbLongDate) %><br>

FormatDateTime(TheDate, vbShortTime) =
	<%= FormatDateTime(TheDate, vbShortTime) %><br>

FormatDateTime(TheDate, vbLongTime) =
	<%= FormatDateTime(TheDate, vbLongTime) %><br>
