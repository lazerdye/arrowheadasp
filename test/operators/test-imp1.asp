Test logical implication operator:
<hr>

True imp True =         <%= True imp True %><br>
True imp False =        <%= True imp False %><br>
IsNull(True imp Null) =	<%= IsNull(True imp Null) %><br>
False imp True =        <%= False imp True %><br>
False imp False =       <%= False imp False %><br>
False imp Null =        <%= False imp Null %><br>
Null imp True =         <%= Null imp True %><br>
IsNull(Null imp False) =<%= IsNull(Null imp False) %><br>
IsNull(Null imp Null) = <%= IsNull(Null imp Null) %><br>

