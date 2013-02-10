Test CDate function

CDate("1/12/10") = 
    <%= CDate("1/12/10") %><br>
CDate("1/12/90") = 
    <%= CDate("1/12/90") %><br>
CDate("1:30") = 
    <%= CDate("1:30") %><br>
CDate("1:30:15") = 
    <%= CDate("1:30:15") %><br>
CDate("15:30:15") = 
    <%= CDate("15:30:15") %><br>
CDate("January 12 1:30:15") = 
    <%= CDate("January 12 1:30:15") %><br>
CDate("January 12 15:30:15") = 
    <%= CDate("January 12 15:30:15") %><br>
CDate("January 12, 1920 1:30:15") = 
    <%= CDate("January 12, 1920 1:30:15") %><br>
CDate("Jun 25, 1920 2:30 pm") = 
    <%= CDate("Jun 25, 1920 2:30 pm") %><br>
