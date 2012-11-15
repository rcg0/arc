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
</head>
<body>

  <header id="site_head">
    
    <img src="IMG/arc-logo.png" border="0" align="left" height="78px" width="148px">
    
  </header>

<section id="main">
  
  <div id="apagar">
    <a href="logout"><img id="imgApagado" src="IMG/apagado2.png"></a>
    	<% User user = (User)session.getAttribute("user"); %>
    <h3><%= user.getName() + " " + user.getSurName1() + " " + user.getSurName2() %></h3>

  </div>
  
  <header id= "header_rest">

      <nav id="left_nav">
	  <ul>
	      <li><a href="profile">Sobre ti</a></li>
	      <li><a href="getTablones" class="current">Tus tablones</a></li>
          <ul class ="children">
          <li><a href="showModifyTablones">Modificar tablones</a></li>
          <li><a href="showNewTablon">Nuevo tablón</a></li>
          </ul>
	  </ul>
      </nav>
  </header>

	<% Vector<Tablon> tablones = (Vector<Tablon>)request.getAttribute("tablones"); %>

  <nav id="tablon_name">
	  <ul>
	<%int i=0; //identificador de tablon
	  int j=0; //identificador de mensajes
		/*necesito diferenciarlos ya que necesito saber donde volver cuando elimino un mensaje*/
	
	%>
		<% for(i=0;i<tablones.size(); i++){ %>
	      <li><a href="getTablones?tablonId=<%=i%>"> <%=tablones.elementAt(i).getName()%></a></li>
	 	<%}%>
        
	  </ul>
      </nav>

	<% Vector<Message> msgs = (Vector<Message>)request.getAttribute("messages"); %>

<article id = "tablon_content">
  <div id = "mensajes">
    
	<% for(j=0;j<msgs.size(); j++){ %>
    <div class="mensaje">
	<p><%=msgs.elementAt(j).getCreator().getName()  + " " +  msgs.elementAt(j).getCreator().getSurName1()  + " " +  msgs.elementAt(j).getCreator().getSurName2()%>:</p>
	<p><%=msgs.elementAt(j).getMsg()%></p>
	<a href = "deleteMessage?messageId=<%=msgs.elementAt(j).getId()%>&tablonId=<%=i%>"><img src="IMG/delete.jpg" hspace="400px"></a>
    </div>
    
    <p></p>      
	 <%}%>
    
  </div>
  
  <div id = "escribir_mensajes">
    <p>Tu mensaje:</p>
      
      <form method="post" action="sendMessage">
      <textarea autofocus type="text" id="escribiendo_mensaje" name="mensaje">
      </textarea>
      <img id="foto" src="IMG/foto.png" hspace="50px" vspace="100px">
      <img id = "video" src="IMG/video.png" hspace="0px" vspace="100px">
      <img id = "audio" src="IMG/audio.png" hspace="40px" vspace="100px">      
      <input type="submit" value="Enviar" /> 
      <!--<p><input type ="checkbox" name="public"/>Público</p>
      <p><input type ="checkbox" name="private"/>Privado</p>
      <p><input type ="checkbox" name="group"/>Grupo</p>  -->
      </form>
  </div>
</article>



</section>


</html>
