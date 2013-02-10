Test IS operator.
<hr>

<%
Dim DictA, DictB, DictC, NothingA
Set DictA = Server.CreateObject("Scripting.Dictionary")
Set DictB = Server.CreateObject("Scripting.Dictionary")
Set DictC = DictA
Set NothingA = Nothing

%>

DictA is DictA          <%= DictA is DictA %><br>
DictA is DictB          <%= DictA is DictB %><br>
DictA is DictC          <%= DictA is DictC %><br>
DictA is Nothing        <%= DictA is Nothing %><br>
DictA is NothingA       <%= DictA is NothingA %><br>
DictB is DictA          <%= DictB is DictA %><br>
DictB is DictB          <%= DictB is DictB %><br>
DictB is DictC          <%= DictB is DictC %><br>
DictB is Nothing        <%= DictB is Nothing %><br>
DictB is NothingA       <%= DictB is NothingA %><br>
DictC is DictA          <%= DictC is DictA %><br>
DictC is DictB          <%= DictC is DictB %><br>
DictC is DictC          <%= DictC is DictC %><br>
DictC is Nothing        <%= DictC is Nothing %><br>
DictC is NothingA       <%= DictC is NothingA %><br>
Nothing is DictA        <%= Nothing is DictA %><br>
Nothing is DictB        <%= Nothing is DictB %><br>
Nothing is DictC        <%= Nothing is DictC %><br>
Nothing is Nothing      <%= Nothing is Nothing %><br>
Nothing is NothingA     <%= Nothing is NothingA %><br>
NothingA is DictA       <%= NothingA is DictA %><br>
NothingA is DictB       <%= NothingA is DictB %><br>
NothingA is DictC       <%= NothingA is DictC %><br>
NothingA is Nothing     <%= NothingA is Nothing %><br>
NothingA is NothingA    <%= NothingA is NothingA %><br>

