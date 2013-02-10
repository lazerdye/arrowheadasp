<!-- Test StrComp function.
-->

StrComp("cccc", "cccc") = <%= Strcomp("cccc", "cccc") %><br>
StrComp("cccc", "bbbb") = <%= Strcomp("cccc", "bbbb") %><br>
StrComp("cccc", "dddd") = <%= Strcomp("cccc", "dddd") %><br>
StrComp("aaaa", "AAAA") = <%= Strcomp("aaaa", "AAAA") %><br>
StrComp("aaaa", "AAAA", vbTextCompare) =
	<%= Strcomp("aaaa", "AAAA", vbTextCompare) %><br>
StrComp("aaaa", "AAAA", vbBinaryCompare) =
	<%= Strcomp("aaaa", "AAAA", vbBinaryCompare) %><br>
IsNull(StrComp(NULL, "aaa")) = <%= IsNull(StrComp(NULL, "aaa")) %><br>
IsNull(StrComp("aaa", NULL)) = <%= IsNull(StrComp("aaa", NULL)) %><br>
IsNull(StrComp(NULL, NULL)) = <%= IsNull(StrComp(NULL, NULL)) %><br>
