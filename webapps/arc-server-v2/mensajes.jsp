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




   <nav id="tablon_name">
      <ul>
	
		
	      <li>
		  <a href="#"
		    
		  </a>
	      </li>
	 	
        
	  </ul>
   </nav>
   
   <section id="left">
	
	
	

	
	<div class="mensaje">
    <p></p>
	  <p></p>
	<a href = "deleteMessage?messageId="><img src="IMG/delete.jpg"></a>
	</div>
<p></p>

	

  </section>

  <section id ="right" >
     
      <form method="post" action="sendMessage">
      <input type = "radio" id="privado" name="visibility" value="1" checked> <!-- yo por hidden le mandaría los demás datos -->
      <label id="label_privado" for="privado"><span></span></label>
      <input type = "radio" id="publico" name="visibility" value="2">
      <label id="label_publico" for="publico"><span></span></label>
      <input type = "radio" id="grupo" name="visibility" value="0">
      <label id="label_grupo" for="grupo"><span></span></label>
   
      <input id="enviar" type="image" src ="IMG/enviar.png"/> 
      <textarea autofocus type="text" id="escribiendo_mensaje" name="mensaje">
      </textarea>
      <div id="multimedia">
      <input type = "radio" id="foto"  name="format" value="1">
      <label id="label_foto" for="foto"><span></span></label>
      <input type = "radio" id="video"  name="format" value="2">
      <label id="label_video" for="video"><span></span></label>
      <input type = "radio" id="audio"  name="format" value="3">
      <label id="label_audio" for="audio"><span></span></label>
      </div>
      </form>

  </section>


</div>
</html>
