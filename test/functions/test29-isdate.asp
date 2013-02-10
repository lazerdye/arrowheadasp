<!-- Test IsDate function.
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

IsDate(VarInt) = <%= IsDate(VarInt) %><br>
IsDate(VarDouble) = <%= IsDate(VarDouble) %><br>
IsDate(VarString) = <%= IsDate(VarString) %><br>
IsDate(VarDate) = <%= IsDate(VarDate) %><br>
IsDate(VarObj) = <%= IsDate(VarObj) %><br>
IsDate(VarArray) = <%= IsDate(VarArray) %><br>
IsDate(VarNull) = <%= IsDate(VarNull) %><br>
IsDate(VarUninitialized) = <%= IsDate(VarUninitialized) %><br>
IsDate("2/23/01") = <%= IsDate("2/23/01") %><br>
IsDate("July 45, 01") = <%= IsDate("July 45, 01") %><br>
