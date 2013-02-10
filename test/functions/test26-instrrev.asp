<!-- Test InStrRev function.
-->

InStrRev("", "hello") = 
	<%= InStrRev("", "hello") %><br>

InStrRev("blah", "") = 
	<%= InStrRev("blah", "") %><br>

InStrRev("thisissometext", "aba") = 
	<%= InStrRev("thisissometext", "aba") %><br>

InStrRev("thisisabanquettest", "aba") = 
	<%= InStrRev("thisisabanquettest", "aba") %><br>

InStrRev("thisisabanquettestabachacha", "aba") = 
	<%= InStrRev("thisisabanquettestabachacha", "aba") %><br>

InStrRev("quacksaidtheduck", "quack") = 
	<%= InStrRev("quacksaidtheduck", "quack") %><br>

InStrRev("quacksaidtheduck", "duck") = 
	<%= InStrRev("quacksaidtheduck", "duck") %><br>

InStrRev("thisisabanquettestabachacha", "aba", 26) = 
	<%= InStrRev("thisisabanquettestabachacha", "aba", 26) %><br>

InStrRev("thisisabanquettestabachacha", "aba", -1) = 
	<%= InStrRev("thisisabanquettestabachacha", "aba", -1) %><br>

InStrRev("thisisabanquettestabachacha", "aba", 6) = 
	<%= InStrRev("thisisabanquettestabachacha", "aba", 6) %><br>

InStrRev("thisisabanquettestabachacha", "aba", 7) = 
	<%= InStrRev("thisisabanquettestabachacha", "aba", 7) %><br>

InStrRev("thisisabanquettestabachacha", "aba", 8) = 
	<%= InStrRev("thisisabanquettestabachacha", "aba", 8) %><br>

InStrRev("hErEiSaCaSeOfThEcAsEs", "seofth", 15) =
	<%= InStrRev("hErEiSaCaSeOfThEcAsEs", "seofth", 15) %><br>

InStrRev("hErEiSaCaSeOfThEcAsEs", "seofth", 15, vbBinaryCompare) =
	<%= InStrRev("hErEiSaCaSeOfThEcAsEs", "seofth", 15, vbBinaryCompare) %><br>

InStrRev("hErEiSaCaSeOfThEcAsEs", "seofth", 15, vbTextCompare) =
	<%= InStrRev("hErEiSaCaSeOfThEcAsEs", "seofth", 15, vbTextCompare) %><br>

InStrRev("thisisabanquettest", "aba", 100) = 
	<%= InStrRev("thisisabanquettest", "aba", 100) %><br>
