<!-- Test LTrim function.
-->

LTrim("nospaces") = "<%= LTrim("nospaces") %>"<br>
LTrim("one space") = "<%= LTrim("one space") %>"<br>
LTrim("   ") = "<%= LTrim("   ") %>"<br>
LTrim(" space") = "<%= LTrim(" space") %>"<br>
LTrim("  space") = "<%= LTrim("  space") %>"<br>
LTrim("   space") = "<%= LTrim("   space") %>"<br>
LTrim("   one space") = "<%= LTrim("   one space") %>"<br>
LTrim("space ") = "<%= LTrim("space ") %>"<br>
LTrim("space  ") = "<%= LTrim("space  ") %>"<br>
LTrim("space   ") = "<%= LTrim("space   ") %>"<br>
LTrim(" space ") = "<%= LTrim(" space ") %>"<br>
LTrim("  space  ") = "<%= LTrim("  space  ") %>"<br>
LTrim("   space   ") = "<%= LTrim("   space   ") %>"<br>
IsNull(LTrim(Null)) = <%= IsNull(LTrim(Null)) %>
