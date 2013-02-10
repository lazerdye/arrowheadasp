<!-- Test ABS function.
   If input is number: return abs(number)
   If input is NULL: return NULL
   If input is undefined: return 0
   If input is string or other type: coerce to number, return abs(number)
-->

abs(10) = <%=abs(10)%><br>
abs(0) = <%=abs(0)%><br>
abs(-1) = <%=abs(-1)%><br>
abs(0.5) = <%=abs(0.5)%><br>
abs(-0.5) = <%=abs(-0.5)%><br>
abs(NULL) = <%=abs(NULL)%><br>
abs(UndefinedVar) = <%=abs(UndefinedVar)%><br>
abs("-1.0") = <%=abs("1.0")%><br>
