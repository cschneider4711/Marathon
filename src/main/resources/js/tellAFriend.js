var div = document.createElement("div");
div.innerHTML='<a href="mailto:?subject=Marathon infos&body=Noteworthy marathon information: '+document.title+'"><img src="/marathon/images/tell.png" width="15" border="0" alt="Tell a friend"/></a>';
document.getElementById("tellAFriend").appendChild(div);
