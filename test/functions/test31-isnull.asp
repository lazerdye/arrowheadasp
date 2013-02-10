<!-- Test IsNull function.
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

IsNull(VarInt) = <%= IsNull(VarInt) %><br>
IsNull(VarDouble) = <%= IsNull(VarDouble) %><br>
IsNull(VarString) = <%= IsNull(VarString) %><br>
IsNull(VarEmpty) = <%= IsNull(VarDate) %><br>
IsNull(VarObj) = <%= IsNull(VarObj) %><br>
IsNull(VarArray) = <%= IsNull(VarArray) %><br>
IsNull(VarNull) = <%= IsNull(VarNull) %><br>
IsNull(VarUninitialized) = <%= IsNull(VarUninitialized) %><br>
IsNull(VarUndefined) = <%= IsNull(VarUndefined) %><br>
IsNull(Empty) = <%= IsNull(Empty) %><br>
IsNull(Null <> 1) = <%= IsNull(Null <> 1) %><br>
