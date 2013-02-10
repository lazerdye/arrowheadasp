<!-- Test IsEmpty function.
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

IsEmpty(VarInt) = <%= IsEmpty(VarInt) %><br>
IsEmpty(VarDouble) = <%= IsEmpty(VarDouble) %><br>
IsEmpty(VarString) = <%= IsEmpty(VarString) %><br>
IsEmpty(VarEmpty) = <%= IsEmpty(VarDate) %><br>
IsEmpty(VarObj) = <%= IsEmpty(VarObj) %><br>
IsEmpty(VarArray) = <%= IsEmpty(VarArray) %><br>
IsEmpty(VarNull) = <%= IsEmpty(VarNull) %><br>
IsEmpty(VarUninitialized) = <%= IsEmpty(VarUninitialized) %><br>
IsEmpty(VarUndefined) = <%= IsEmpty(VarUndefined) %><br>
IsEmpty(Empty) = <%= IsEmpty(Empty) %><br>
IsEmpty(Empty + Empty) = <%= IsEmpty(Empty + Empty) %><br>
