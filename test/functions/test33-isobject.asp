<!-- Test IsObject function.
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

IsObject(VarInt) = <%= IsObject(VarInt) %><br>
IsObject(VarDouble) = <%= IsObject(VarDouble) %><br>
IsObject(VarString) = <%= IsObject(VarString) %><br>
IsObject(VarEmpty) = <%= IsObject(VarDate) %><br>
IsObject(VarObj) = <%= IsObject(VarObj) %><br>
IsObject(VarArray) = <%= IsObject(VarArray) %><br>
IsObject(VarNull) = <%= IsObject(VarNull) %><br>
IsObject(VarUninitialized) = <%= IsObject(VarUninitialized) %><br>
IsObject(VarUndefined) = <%= IsObject(VarUndefined) %><br>
IsObject(Empty) = <%= IsObject(Empty) %><br>
