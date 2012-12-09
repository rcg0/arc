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
  <script src="js/script01.js" type="text/javascript" language="Javascript"> </script>
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
      <ul class="menu">
	       <li><a href="profile">Perfil</a></li>
         <li><a href="#" >Mis tablones</a>
              <ul id="submenu_tablones">
                  <li><a href="showModifyTablones">Nuevo tablon</a>
                  </li>
                  
              </ul>

         </li>
	       <li><a href="#" class="current">Mensajes</a>

                <ul id="submenu_mensajes">
                </ul>
         </li>
      </ul>
   </nav>
</header>

<!-- a partir de aqui es lo que varía de una sección a otra -->

<%
Tablon tablon = (Tablon)request.getAttribute("tablon");

if(tablon== null){
  System.out.println("tablon es null");
}
Vector<Message> msgs = tablon.getAllMsg();

%>


   
   <section id="left">

   
	 <p class="blanco"><b>Identificador:</b> <%= tablon.getId()%> <b>Nombre de tablón:</b> <%= tablon.getName()%></p>
  
  
   <img id= "moreMessages" src="IMG/moreIcon.png" onClick="getMoreMessages()">
	 
	
<%if(msgs != null){%>
	 <% for(int j=msgs.size()-1;j>=0; j--){ %>

<%System.out.println("msgs.size()="+msgs.size());%>

	 <div id="<%=msgs.elementAt(j).getId()%>" class="mensaje"><p><b><%=msgs.elementAt(j).getCreator().getName()  + " " +  msgs.elementAt(j).getCreator().getSurName1()  + " " +  msgs.elementAt(j).getCreator().getSurName2()%></b>:
	  <p><%=msgs.elementAt(j).getMsg()%></p>
	<a ><img class="delete" src="IMG/delete.jpg" onClick="removeMessage(<%=msgs.elementAt(j).getId()%>)"></a>
	</div>
<p></p>
<%}%>
<%}%>
	

  </section>

  <section id ="right" >
     
      <form method="post" action="javascript:sendMessage()">

        <input type = "radio" id="privado" name="visibility" value="1" checked>
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
