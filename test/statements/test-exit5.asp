Test Exit Sub outside of Sub

Function MyFunc     <% Function MyFunc %><br>
    Exit Sub        <%  Exit Sub %><br>
End Function        <% End Function %><br>
<br>
MyFunc <% MyFunc %><br>
