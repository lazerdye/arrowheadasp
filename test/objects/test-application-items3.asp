Test storing application objects using different methods.

Application.Contents.Item("A") = 1 <% Application.Contents.Item("A") = 1 %><br>
Application.Contents("B") = 2 <% Application.Contents("B") = 2 %><br>
Application("C") = 3 <% Application("C") = 3 %><br>
Application("A") = <%= Application("A") %><br>
Application("B") = <%= Application("B") %><br>
Application("C") = <%= Application("C") %><br>
