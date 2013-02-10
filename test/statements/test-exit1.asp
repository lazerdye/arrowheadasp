Test Exit statements.
<br>
Exit Do<br>
<br>
Do While 1  <% Do While 1 %><br>
    In Loop, Before Exit<br>
    Exit Do <%  Exit Do %><br>
    In Loop, After Exit<br>
Loop        <% Loop %><br>
<br>
Exit For<br>
<br>
For i = 1 to 100    <% For i = 1 to 100 %><br>
    In Loop, Before Exit<br>
    Exit For        <% Exit For %><br>
    In Loop, After Exit<br>
Next                <% Next %><br>
<br>
Exit Function<br>
<br>
Function MyFunc     <% Function MyFunc %><br>
    In Function, Before Exit<br>
    Exit Function   <%  Exit Function %>
    In Function, After Text<br>
End Function        <% End Function %><br>
<br>
MyFunc <% MyFunc %><br>
<br>
Exit Sub<br>
<br>
Sub MySub      <% Sub MySub %><br>
    In Sub, Before Exit<br>
    Exit Sub    <%  Exit Sub %>
    In Sub, After Text<br>
End Sub         <% End Sub %><br>
<br>
MySub <% MySub %><br>
