Test Redim Statement

Dim Arr()       <% Dim Arr() %><br>
ReDim Arr(2)    <% ReDim Arr(2) %><br>
Arr(1) = "One"  <% Arr(1) = "One" %><br>
Arr(2) = "Two"  <% Arr(2) = "Two" %><br>
UBOUND(Arr) =   <%= UBOUND(Arr) %><br>
ReDim Arr(3)    <% ReDim Arr(3) %><br>
Arr(1) =        <%= Arr(1) %><br>
Arr(2) =        <%= Arr(2) %><br>
Arr(3) =        <%= Arr(3) %><br>
UBOUND(Arr) =   <%= UBOUND(Arr) %><br>
Arr(1) = "One"  <% Arr(1) = "One" %><br>
Arr(2) = "Two"  <% Arr(2) = "Two" %><br>
Arr(3) = "Three"<% Arr(3) = "Three" %><br>
ReDim Preserve Arr(4) <% ReDim Preserve Arr(4) %><br>A
Arr(1) =        <%= Arr(1) %><br>
Arr(2) =        <%= Arr(2) %><br>
Arr(3) =        <%= Arr(3) %><br>
Arr(4) =        <%= Arr(4) %><br>
UBOUND(Arr) =   <%= UBOUND(Arr) %><br>
ReDim Arr(2, 3, 4) <% ReDim Arr(2, 3, 4) %><br>
UBOUND(Arr, 1) = <%= UBOUND(Arr, 1) %><br>
UBOUND(Arr, 2) = <%= UBOUND(Arr, 2) %><br>
UBOUND(Arr, 3) = <%= UBOUND(Arr, 3) %><br>
Arr(1,2,3) = "One" <% Arr(1,2,3) = "One" %><br>
Arr(2,3,4) = "Two" <% Arr(2,3,4) = "Two" %><br>
ReDim Preserve Arr(2, 3, 5) <% ReDim Preserve Arr(2,3,5) %><br>
UBOUND(Arr, 1) = <%= UBOUND(Arr, 1) %><br>
UBOUND(Arr, 2) = <%= UBOUND(Arr, 2) %><br>
UBOUND(Arr, 3) = <%= UBOUND(Arr, 3) %><br>
Arr(1,2,3) = <%= Arr(1,2,3) %><br>
Arr(2,3,4) = <%= Arr(2,3,4) %><br>
