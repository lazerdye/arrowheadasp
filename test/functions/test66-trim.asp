<!-- Test Trim function.
-->

Trim("nospaces") = "<%= Trim("nospaces") %>"<br>
Trim("one space") = "<%= Trim("one space") %>"<br>
Trim("   ") = "<%= Trim("   ") %>"<br>
Trim(" space") = "<%= Trim(" space") %>"<br>
Trim("  space") = "<%= Trim("  space") %>"<br>
Trim("   space") = "<%= Trim("   space") %>"<br>
Trim("   one space") = "<%= Trim("   one space") %>"<br>
Trim("space ") = "<%= Trim("space ") %>"<br>
Trim("space  ") = "<%= Trim("space  ") %>"<br>
Trim("space   ") = "<%= Trim("space   ") %>"<br>
Trim(" space ") = "<%= Trim(" space ") %>"<br>
Trim("  space  ") = "<%= Trim("  space  ") %>"<br>
Trim("   space   ") = "<%= Trim("   space   ") %>"<br>
IsNull(Trim(Null)) = <%= IsNull(Trim(Null)) %><br>
