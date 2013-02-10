<!-- Test InStr function.
-->

InStr("", "hello") = 
	<%= InStr("", "hello") %><br>

InStr(NULL, "hello") = 
	<%= InStr(NULL, "hello") %><br>

InStr("blah", "") = 
	<%= InStr("blah", "") %><br>

InStr("blah", NULL) = 
	<%= InStr("blah", NULL) %><br>

InStr("thisissometext", "aba") = 
	<%= InStr("thisissometext", "aba") %><br>

InStr("thisisabanquettest", "aba") = 
	<%= InStr("thisisabanquettest", "aba") %><br>

InStr("thisisabanquettestabachacha", "aba") = 
	<%= InStr("thisisabanquettestabachacha", "aba") %><br>

InStr("quacksaidtheduck", "quack") = 
	<%= InStr("quacksaidtheduck", "quack") %><br>

InStr("quacksaidtheduck", "duck") = 
	<%= InStr("quacksaidtheduck", "duck") %><br>

InStr(1, "thisisabanquettestabachacha", "aba") = 
	<%= InStr(1, "thisisabanquettestabachacha", "aba") %><br>

InStr(6, "thisisabanquettestabachacha", "aba") = 
	<%= InStr(6, "thisisabanquettestabachacha", "aba") %><br>

InStr(7, "thisisabanquettestabachacha", "aba") = 
	<%= InStr(7, "thisisabanquettestabachacha", "aba") %><br>

InStr(8, "thisisabanquettestabachacha", "aba") = 
	<%= InStr(8, "thisisabanquettestabachacha", "aba") %><br>

InStr(1, "hErEiSaCaSeOfThEcAsEs", "seofth") =
	<%= InStr(1, "hErEiSaCaSeOfThEcAsEs", "seofth") %><br>

InStr(1, "hErEiSaCaSeOfThEcAsEs", "seofth", vbBinaryCompare) =
	<%= InStr(1, "hErEiSaCaSeOfThEcAsEs", "seofth", vbBinaryCompare) %><br>

InStr(1, "hErEiSaCaSeOfThEcAsEs", "seofth", vbTextCompare) =
	<%= InStr(1, "hErEiSaCaSeOfThEcAsEs", "seofth", vbTextCompare) %><br>

InStr(100, "thisisabanquettest", "aba") = 
	<%= InStr(100, "thisisabanquettest", "aba") %><br>
