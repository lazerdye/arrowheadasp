<!-- Test IsNumeric function.
-->

Dim VarInt, VarDouble, VarString, VarDate, VarObj, VarArray
	<% Dim VarInt, VarDouble, VarString, VarDate, VarObj, VarArray %><br>
Dim VarNull, VarUninitialized
	<% Dim VarNull, VarUninitialized %><br>

VarInt = 1 <% VarInt = 1 %><br>
VarDouble = 0.123 <% VarDouble = 0.123 %><br>
VarString = "Hello" <% VarString = "Hello" %><br>
VarDate = DateSerial(1, 25, 1982) <% VarDate = DateSerial(1, 25, 1982) %><br>
Set VarObj = CreateObject("Scripting.Dictionary")
	<% Set VarObj = CreateObject("Scripting.Dictionary") %><br>
VarArray = Array(1, 2, 3, 4)
	<% VarArray = Array(1, 2, 3, 4) %><br>
VarNull = NULL
	<% VarNull = NULL %><br>

IsNumeric(VarInt) = <%= IsNumeric(VarInt) %><br>
IsNumeric(VarDouble) = <%= IsNumeric(VarDouble) %><br>
IsNumeric(VarString) = <%= IsNumeric(VarString) %><br>
IsNumeric(VarEmpty) = <%= IsNumeric(VarDate) %><br>
IsNumeric(VarObj) = <%= IsNumeric(VarObj) %><br>
IsNumeric(VarArray) = <%= IsNumeric(VarArray) %><br>
IsNumeric(VarNull) = <%= IsNumeric(VarNull) %><br>
IsNumeric(VarUninitialized) = <%= IsNumeric(VarUninitialized) %><br>
IsNumeric(VarUndefined) = <%= IsNumeric(VarUndefined) %><br>
IsNumeric(Empty) = <%= IsNumeric(Empty) %><br>
IsNumeric("1,230.2345") = <%= IsNumeric("1,230.2345") %><br>
IsNumeric("1.2345abc") = <%= IsNumeric("1.2345abc") %><br>
IsNumeric("aha") = <%= IsNumeric("aha") %><br>
IsNumeric("aha123") = <%= IsNumeric("aha123") %><br>
