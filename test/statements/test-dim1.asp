Test Dim statement:

Dim VarA, VarB(2), VarC(3, 4), VarD(5, 6, 7)
	<% Dim VarA, VarB(2), VarC(3, 4), VarD(5, 6, 7) %><br>

VarA = <%= VarA %><br>

UBound(VarB) = <%= UBound(VarB) %><br>

UBound(VarC, 1) = <%= UBound(VarC, 1) %><br>
UBound(VarC, 2) = <%= UBound(VarC, 2) %><br>

UBound(VarD, 1) = <%= UBound(VarD, 1) %><br>
UBound(VarD, 2) = <%= UBound(VarD, 2) %><br>
UBound(VarD, 3) = <%= UBound(VarD, 3) %><br>

Sub TestSetANoDim <% Sub TestSetANoDim %><br>
  VarA = "Hello" <% VarA = "Hello" %><br>
  VarA = <%= VarA %><br>
End Sub <% End Sub %><br>

TestSetANoDim <% TestSetANoDim %><br>

VarA = <%= VarA %><br>

Sub TestSetADim <% Sub TestSetADim %><br>
  Dim VarA <% Dim VarA %><br>
  VarA = "Greetings" <% VarA = "Greetings" %><br>
  VarA = <%= VarA %><br>
End Sub <% End Sub %><br>

TestSetADim <% TestSetADim %><br>

VarA = <%= VarA %><br>
