<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.Vector" %>
<%@ page import="arc.User" %>
<%@ page import="arc.Tablon" %>
<%@ page import="arc.Message" %>


<!DOCTYPE html>
<html lang="es">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	<title>ARC</title>
	<link href="style.css" rel="stylesheet" type="text/css" media="screen" />
	<link href="messages.css" rel="stylesheet" type="text/css" media="screen" />
</head>
<body>


<div id= wrapper> 

<header id="site_head">
  <img id="logo" src="IMG/arc-logo.png">
  <div id="logout">
    <a href="logout"><img id="imgApagado" src="IMG/apagado2.png"></a>
<% User user = (User)session.getAttribute("user"); %>

    <p><%= user.getName()%></p>
  </div>
   
   <nav id="main_nav">
      <ul>
	<li><a href="profile">Perfil</a></li>
	<li><a href="showModifyTablones" >Mis tablones</a></li>
	<li><a href="getTablones" class="current">Mensajes</a></li>
      </ul>
   </nav>
</header>


<!-- a partir de aqui es lo que varía de una sección a otra -->

<% Vector<Tablon> tablones = (Vector<Tablon>)request.getAttribute("tablones"); %>

<% Vector<Message> msgs = (Vector<Message>)request.getAttribute("messages"); %>

   <nav id="tablon_name">
      <ul>
	<%int i=0; //identificador de tablon
	  int j=0; //identificador de mensajes
		/*necesito diferenciarlos ya que necesito saber donde volver cuando elimino un mensaje*/
	
	%>
		<% for(i=0;i<tablones.size(); i++){ %>
	      <li><a href="getTablones?tablonId=<%=i%>" class="current"> <%=tablones.elementAt(i).getName()%></a></li>
	 	<%}%>
        
	  </ul>
   </nav>
   
   <section id="left">
	
	<% for(j=0;j<msgs.size(); j++){ %>
	<div class="mensaje"><p><%=msgs.elementAt(j).getCreator().getName()  + " " +  msgs.elementAt(j).getCreator().getSurName1()  + " " +  msgs.elementAt(j).getCreator().getSurName2()%>:</p>
	<p><%=msgs.elementAt(j).getMsg()%></p>
	<a href = "deleteMessage?messageId=<%=msgs.elementAt(j).getId()%>&tablonId=<%=i%>"><img src="IMG/delete.jpg" hspace="400px"></a>
	</div>
<p></p>

<%}%>

  </section>

  <section id ="right" >
     
      <form method="post" action="#">
      <img id ="privado" src="IMG/agent.png">
      <img id ="publico" src="IMG/globe.png">
      <img id ="grupo" src="IMG/multi-agents.png">
      <input id="enviar" type="image" src ="IMG/enviar.png"/> 
      <textarea autofocus type="text" id="escribiendo_mensaje" name="mensaje">
      </textarea>
      <div id="multimedia">
      <img id="foto" src="IMG/foto.png">
      <img id = "video" src="IMG/video.png">
      <img id = "audio" src="IMG/audio.png">
      </div>
      </form>

  </section>


</div>
</html>
