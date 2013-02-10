<!-- Test UCase function.
-->

UCase("HeLlO tHeRe") = <%= UCase("HeLlO tHeRe") %><br>
UCase("this is a test") = <%= UCase("this is a test") %><br>
UCase("THIS IS A TEST") = <%= UCase("THIS IS A TEST") %><br>
IsNull(UCase(NULL)) = <%= IsNull(UCase(NULL)) %><br>
