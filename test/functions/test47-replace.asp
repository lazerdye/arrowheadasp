<!-- Test Replace function.
-->

Replace("this is a test", "is a", "was a") =
	<%= Replace("this is a test", "is a", "was a") %><br>
Replace("this is a test", "is an", "was a") =
	<%= Replace("this is a test", "is an", "was a") %><br>
Replace("this is a test", "t", "w") =
	<%= Replace("this is a test", "t", "w") %><br>
Replace("this is a test", "t", "w", 1) =
	<%= Replace("this is a test", "t", "w", 1) %><br>
Replace("this is a test", "t", "w", 2) =
	<%= Replace("this is a test", "t", "w", 2) %><br>
Replace("this is a test", "t", "w", 20) =
	<%= Replace("this is a test", "t", "w", 20) %><br>
Replace("this is a test", "t", "w", 1, -1) =
	<%= Replace("this is a test", "t", "w", 1, -1) %><br>
Replace("this is a test", "t", "w", 1, 0) =
	<%= Replace("this is a test", "t", "w", 1, 0) %><br>
Replace("this is a test", "t", "w", 1, 1) =
	<%= Replace("this is a test", "t", "w", 1, 1) %><br>
Replace("this is a test", "t", "w", 1, 2) =
	<%= Replace("this is a test", "t", "w", 1, 2) %><br>
Replace("this is a test", "t", "w", 1, 3) =
	<%= Replace("this is a test", "t", "w", 1, 3) %><br>
Replace("this is a test", "t", "w", 1, 4) =
	<%= Replace("this is a test", "t", "w", 1, 4) %><br>
Replace("tHiS iS a TeSt", "t", "w", 1, -1, vbBinaryCompare) =
	<%= Replace("tHiS iS a TeSt", "t", "w", 1, -1, vbBinaryCompare) %><br>
Replace("tHiS iS a TeSt", "t", "w", 1, -1, vbTextCompare) =
	<%= Replace("tHiS iS a TeSt", "t", "w", 1, -1, vbTextCompare) %><br>
Replace("", "t", "w") =
	<%= Replace("", "t", "w") %><br>
Replace("This is a test", "", "w") =
	<%= Replace("This is a test", "", "w") %><br>
Replace("This is a test", "t", "") =
	<%= Replace("This is a test", "t", "") %><br>
