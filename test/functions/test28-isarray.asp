<!-- Test IsArray function.
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

IsArray(VarInt) = <%= IsArray(VarInt) %><br>
IsArray(VarDouble) = <%= IsArray(VarDouble) %><br>
IsArray(VarString) = <%= IsArray(VarString) %><br>
IsArray(VarDate) = <%= IsArray(VarDate) %><br>
IsArray(VarObj) = <%= IsArray(VarObj) %><br>
IsArray(VarArray) = <%= IsArray(VarArray) %><br>
IsArray(VarNull) = <%= IsArray(VarNull) %><br>
IsArray(VarUninitialized) = <%= IsArray(VarUninitialized) %><br>
