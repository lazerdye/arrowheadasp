Test the byref statement, and calling by reference in general.

<%
    Function AllByRef(ByRef FuncVarA, ByVal FuncVarB, FuncVarC)
        FuncVarA = "VarAInFunction"
        FuncVarB = "VarBInFunction"
        FuncVarC = "VarCInFunction"

        AllByRef = "Return"
    End function

    Dim VarA, VarB, VarC, Result

    VarA = "VarAGlobal"
    VarB = "VarBGlobal"
    VarC = "VarCGlobal"
    
%>Before:
VarA: <%=VarA%><br>
VarB: <%=VarB%><br>
VarC: <%=VarC%><br>
<%
    
    Call AllByRef(VarA, VarB, VarC)

%>After execute with call:
VarA: <%=VarA%><br>
VarB: <%=VarB%><br>
VarC: <%=VarC%><br>
<%

    VarA = "VarAGlobal"
    VarB = "VarBGlobal"
    VarC = "VarCGlobal"
    
    AllByRef VarA, VarB, VarC

%>After execute without call (no parenthesis):
VarA: <%=VarA%><br>
VarB: <%=VarB%><br>
VarC: <%=VarC%><br>
<%

    VarA = "VarAGlobal"
    VarB = "VarBGlobal"
    VarC = "VarCGlobal"

    Result = AllByRef(VarA, VarB, VarC)
    
%>After function call:
VarA: <%=VarA%><br>
VarB: <%=VarB%><br>
VarC: <%=VarC%><br>
Result: <%=Result%><br>
<%

%>
