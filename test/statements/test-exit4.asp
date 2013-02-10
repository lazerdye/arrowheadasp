Test Exit Function outside of Function

Sub MySub           <% Sub MySub %><br>
    Exit Function   <%  Exit Function %><br>
End Sub             <% End Sub %><br>
<br>
MySub <% MySub %><br>
