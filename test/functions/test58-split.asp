<!-- Test Split function.
-->


Dim Arr <% Dim Arr %><br>
Join(Split("This is a test"), ",") =
	<%= Join(Split("This is a test"), ",") %><br>
Join(Split("This is a test", "s"), ",") =
	<%= Join(Split("This is a test", "s"), ",") %><br>
Join(Split("This is a test", "s", -1), ",") =
	<%= Join(Split("This is a test", "s", -1), ",") %><br>
Join(Split("This is a test", "s", 0), ",") =
	<%= Join(Split("This is a test", "s", 0), ",") %><br>
Join(Split("This is a test", "s", 1), ",") =
	<%= Join(Split("This is a test", "s", 1), ",") %><br>
Join(Split("This is a test", "s", 2), ",") =
	<%= Join(Split("This is a test", "s", 2), ",") %><br>
Join(Split("This is a test", "s", 5), ",") =
	<%= Join(Split("This is a test", "s", 5), ",") %><br>
Join(Split("ThIs iS a TeSt", "s", -1), ",") =
	<%= Join(Split("ThIs iS a TeSt", "s", -1), ",") %><br>
Join(Split("ThIs iS a TeSt", "s", -1, vbBinaryCompare), ",") =
	<%= Join(Split("ThIs iS a TeSt", "s", -1, vbBinaryCompare), ",") %><br>
Join(Split("ThIs iS a TeSt", "s", -1, vbTextCompare), ",") =
	<%= Join(Split("ThIs iS a TeSt", "s", -1, vbTextCompare), ",") %><br>
