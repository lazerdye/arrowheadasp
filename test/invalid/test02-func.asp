<%

Function OneParamFunc(ParamA)
	%>Inside OneParam<br>
	<%
End Function

Function TwoParamFunc(ParamA, ParamB)
	%>Inside TwoParam<br>
	<%
End Function

Call OneParamFunc("a")

Call TwoParamFunc("a", "b")

OneParamFunc "a"

OneParamFunc("a")

TwoParamFunc "a", "b"

TwoParamFunc("a", "b")

%>
