<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.Vector" %>
<%@ page import="arc.User" %>
<%@ page import="arc.Tablon" %>

<!DOCTYPE html>
<html lang="es">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	<title>ARC</title>
	

	<script src="js/script01.js" type="text/javascript" language="Javascript"> </script> 
	<link href="style.css" rel="stylesheet" type="text/css" media="screen" />
	<link href="messages.css" rel="stylesheet" type="text/css" media="screen" />
	<link href="mis-tablones.css" rel="stylesheet" type="text/css" media="screen" />



</head>

<body>


<div id= wrapper> 

<header id="site_head">
  <img id="logo" src="IMG/arc-logo.png">
  <div id="logout">
    <a href="#"><a href="logout"><img id="imgApagado" src="IMG/apagado2.png"></a>
    
<% User user = (User)session.getAttribute("user"); %>

    <p><%= user.getName()%></p>
  </div>
   
<% 
  //para el formulario, cojo todos los usuarios de la base de datos.
  Vector<User> allUsers =(Vector<User>)request.getAttribute("allUsers");
  Vector<User> moderators = null;
  Vector<User> users = null;

%>

   <nav id="main_nav">
    <ul class="menu">
	<li><a href="profile">Perfil</a></li>
	<li id="sublista"><a href="showModifyTablones" class="current" >Mis tablones</a>
			<ul id="submenu_tablones">
		
				<li><a href="profile">Nuevo tablon</a>
				</li>
				
			</ul>

	</li>
	<li><a href="getTablones">Mensajes</a>
 				<ul id="submenu_mensajes">
                  
                </ul>
	</li>
      </ul>
   </nav>
</header>

<!-- a partir de aqui es lo que varía d
e una sección a otra -->


   <nav id="tablon_name">
   
   
   </nav>


   <section id="left">

	<div class="mensaje">
	  <h2>Nombre de tablón:</h2>
	  <p></p>
	</div>
	<p></p>
	<p></p>
	<div class="mensaje">
	  <h2>Moderadores asociados:</h2>

	  
	      <p>
              </p>

	</div>
	<p></p>
	<div class="mensaje">
	  <h2>Usuarios asociados:</h2>
	  
	  
	      <p>
		 
		 
		
              </p>


	</div>
	<p></p>
	<div class="mensaje">
	  <h2>Permisos asociados a usuario:</h2>
	  
	      <p>
              </p>
	</div>


  </section>

  <section id ="right" >
    


      <form method = "post" action= "deleteTablon">
      <input id="eliminar" type="submit" value="Eliminar tablón"/>
      </form>


      <form method="post" action="#">
      <h2>Nombre del tablón:</h2>
      <input type="text" value=""/>
      <p></p>
      <h2>Asociar moderadores: </h2>
      <%for(int i=0;i < allUsers.size(); i++){%>
	<select  multiple="multiple" name="moderators">
	 <option value="<%=allUsers.elementAt(i).getName() + " " +allUsers.elementAt(i).getSurName1() + " " +allUsers.elementAt(i).getSurName2()%>">
	  <%=allUsers.elementAt(i).getName() + " " +allUsers.elementAt(i).getSurName1() + " "+allUsers.elementAt(i).getSurName2()%></option>
	<p></p>
      <%}%>

      <h2>Asociar usuarios: </h2>
      <%for(int i=0;i < allUsers.size(); i++){%>
	<select multiple="multiple" name="targetUsers">
	 <option value="<%=allUsers.elementAt(i).getName() + " " +allUsers.elementAt(i).getSurName1() + " " +allUsers.elementAt(i).getSurName2()%>">
	  <%=allUsers.elementAt(i).getName() + " " +allUsers.elementAt(i).getSurName1() + " "+allUsers.elementAt(i).getSurName2()%></option>
	<p></p>
      <%}%>

      <h2>Asociar permiso a usuario: </h2>



   
      <input id="modificar" type="button" value="Modificar tablón" />

      <input id="nuevo" type="button" value="Nuevo tablón" />
            
      </form>
      


  </section>


</div>
</html>
