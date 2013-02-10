<%

Sub OneParamSub(ParamA)
	%>Inside OneParam<br>
	<%
End Sub

Sub TwoParamSub(ParamA, ParamB)
	%>Inside TwoParam<br>
	<%
End Sub

Call OneParamSub("a")

Call TwoParamSub("a", "b")

OneParamSub "a"

OneParamSub("a")

TwoParamSub "a", "b"

TwoParamSub("a", "b")

%>
