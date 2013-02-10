<!-- Test ASC function.
  Returns the character code of the first character.
  ASCB() returns the value of the first byte of the string.
  ASCW() returns the value of the first unicode character of the string.
  TODO: Test Unicode characters
-->

ASC("This is a test") = <%=ASC("This is a test")%><br>
ASC(20+30) = <%=ASC(20+30)%><br>
ASCB("This is a test") = <%=ASCB("This is a test")%><br>
ASCB(20+30) = <%=ASCB(20+30)%><br>
ASCW("This is a test") = <%=ASCW("This is a test")%><br>
ASCW(20+30) = <%=ASCW(20+30)%><br>
