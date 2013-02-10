Test If Statements
<br>
Single Line Form, Simple:<br>
<br>
if 1 = 1 then Response.Write("Yes")
    <% if 1 = 1 then Response.Write("Yes") %><br>
<br>
if 1 = 1 then Response.Write("Yes") else Response.Write("No")
    <% if 1 = 1 then Response.Write("Yes") else Response.Write("No") %><br>
<br>
Single Line Form, multiple statements:<br>
<br>
if 1 = 1 then Response.Write("Yes ") : Response.Write("Sir") else Response.Write("No")
    <% if 1 = 1 then Response.Write("Yes ") : Response.Write("Sir") else Response.Write("No") %><br>
<br>
Block Form, Simple:<br>
<br>
if "a" = "a" then           <% if "a" = "a" then %><br>
    Response.Write("Yes")   <%  Response.Write("Yes") %><br>
end if                      <% end if %><br>
<br>
Block Form, If/Else:<br>
<br>
if "a" = "b" then           <% if "a" = "b" then %><br>
    Response.Write("Yes")   <%  Response.Write("Yes") %><br>
else                        <% else %><br>
    Response.Write("No")    <%  Response.Write("No") %><br>
end if                      <% end if %><br>
<br>
Block Form, If/Elseif/Else:<br>
<br>
if "a" = "b" then           <% if "a" = "b" then %><br>
    Response.Write("Yes")   <%  Response.Write("Yes") %><br>
elseif 1 = 1 then           <% elseif 1 = 1 then %><br>
    Response.Write("Yes 2") <%  Response.Write("Yes 2 ") %><br>
else                        <% else %><br>
    Response.Write("No")    <%  Response.Write("No") %><br>
end if                      <% end if %><br>
<br>
Block Form, missing NL on Else:<br>
<br>
If "a" = "b" then           <% if "a" = "b" then %><br>
    Response.Write("Yes")   <%  Response.Write("Yes") %><br>
elseif 1 = 1 then Response.Write("Yes 2")
                            <% elseif 1 = 1 then Response.Write("Yes 2") %><br>
else Response.Write("No")   <% else Response.Write("No") %><br>
end if                      <% end if %><br>
<br>
