Test Erase function

<%
Dim Arr(3)

Arr(1) = 1
Arr(2) = 2
Arr(3) = 3
%>

Arr(1) = <%= Arr(1) %><br>
Arr(2) = <%= Arr(2) %><br>
Arr(3) = <%= Arr(3) %><br>
Erase Arr <% Erase Arr %><br>
Arr(1) = <%= Arr(1) %><br>
Arr(2) = <%= Arr(2) %><br>
Arr(3) = <%= Arr(3) %><br>

<%
Dim DynArr()

ReDim DynArr(3)

DynArr(1) = 1
DynArr(2) = 2
DynArr(3) = 3
%>

DynArr(1) = <%= DynArr(1) %><br>
DynArr(2) = <%= DynArr(2) %><br>
DynArr(3) = <%= DynArr(3) %><br>
Erase DynArr <% Erase DynArr %><br>
IsArray(DynArr) = <%= IsArray(DynArr) %><br>
