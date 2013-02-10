<!-- Test RTrim function.
-->

RTrim("nospaces") = "<%= RTrim("nospaces") %>"<br>
RTrim("one space") = "<%= RTrim("one space") %>"<br>
RTrim("   ") = "<%= RTrim("   ") %>"<br>
RTrim(" space") = "<%= RTrim(" space") %>"<br>
RTrim("  space") = "<%= RTrim("  space") %>"<br>
RTrim("   space") = "<%= RTrim("   space") %>"<br>
RTrim("   one space") = "<%= RTrim("   one space") %>"<br>
RTrim("space ") = "<%= RTrim("space ") %>"<br>
RTrim("space  ") = "<%= RTrim("space  ") %>"<br>
RTrim("space   ") = "<%= RTrim("space   ") %>"<br>
RTrim(" space ") = "<%= RTrim(" space ") %>"<br>
RTrim("  space  ") = "<%= RTrim("  space  ") %>"<br>
RTrim("   space   ") = "<%= RTrim("   space   ") %>"<br>
IsNull(RTrim(Null)) = <%= IsNull(RTrim(Null)) %><br>
