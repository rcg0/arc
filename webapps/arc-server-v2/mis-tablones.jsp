<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Vector" %>
<%@ page import="arc.User" %>
<%@ page import="arc.Tablon" %>

<!DOCTYPE html>
<html lang="es">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	<title>ARC</title>
	

	<script src="js/script01.js" type="text/javascript" language="Javascript"> </script> 
	<script src="js/searchUsers.js" type="text/javascript" language="Javascript"> </script> 
	<script src="js/asociarModeradores.js" type="text/javascript" language="Javascript"> </script> 


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
  
  Vector<User> moderators = null;
  Vector<User> users = null;

  Tablon tablon = (Tablon)request.getAttribute("tablon");
 
%>

    <ul class="menu">
	<li><a href="profile">Perfil</a></li>
	<li id="sublista"><a href="showModifyTablones" class="current" >Mis tablones</a>
			<ul id="submenu_tablones">
		
				<li><a href="showModifyTablones">Nuevo tablón</a>
				</li>
				
			</ul>

	</li>
	<li><a href="#">Mensajes</a>
 				<ul id="submenu_mensajes">
                  
                </ul>
	</li>
      </ul>
</header>

<!-- a partir de aqui es lo que varía d
e una sección a otra -->


   <section id="left">

	<div  class="mensaje">
	  <p><b>Identificador del tablón:</b><%if(tablon!=null){%>
	  <a id= "messageTablonId"><%=tablon.getId()%></a><%}%></p>
	  
	</div>
	<p></p>
	<p></p>

	<div class="mensaje">
	  <p><b>Nombre de tablón:</b>
	  <%if(tablon!=null){%>
	  <%=tablon.getName()%></p>
	  <%}%>
	</div>
	<p></p>
	<p></p>
	<div class="mensaje">
	  <p><b>Moderadores asociados:</p></b>

	    <%if(tablon!=null){%>
		<%moderators = tablon.getUsers(); %>
	    <%for(int i = 0; i<moderators.size(); i++){%>
	      <p><%=moderators.elementAt(i).getName()%>
		 <%=moderators.elementAt(i).getSurName1()%>
		 <%=moderators.elementAt(i).getSurName2()%>
              </p>
	    <%}%>
	    <%}%>
	</div>
	<p></p>
	<div class="mensaje">
	  <p><b>Usuarios asociados:</p></b>
 	    <%if(tablon!=null){%>
	   <%users = tablon.getTargetUsers(); %>
	    <%for(int i = 0; i<users.size(); i++){%>
	      <p><%=users.elementAt(i).getName()%>
		 <%=users.elementAt(i).getSurName1()%>
		 <%=users.elementAt(i).getSurName2()%>
		
              </p>
	    <%}%>
		<%}%>
	</div>
	<p></p>
	<div class="mensaje">
	  <p><b>Permisos asociados a usuario:</p></b>
 	    <%if(tablon!=null){%>

	   <%for(int i = 0; i<users.size(); i++){%>
	      <p><%=users.elementAt(i).getName()%>
		 <%=users.elementAt(i).getSurName1()%>
		 <%=users.elementAt(i).getSurName2()%>
		 ->
		 <%=users.elementAt(i).getPermission()%>
	  <%}%></p>
	  <%}%>
	</div>


  </section>

  <section id ="right" >
    

<%if(tablon != null){%>
      <form id="form_delete" method = "post">

      	<input id="eliminar" type="submit" value="Eliminar tablón" onclick="removeTablon()"/>
      </form>
<%}%>

      <form method="post" action="#">
      <p><b>Nombre del tablón:</p></b>
      <input type="text" value=""/>
      <p></p>
      <p><b>Asociar moderadores: </p></b>


	  <input autofocus type="text" id="patron" name="patron" required>
     	</input>

     	<input id= "busqueda1" type="button" value ="Buscar" onClick = "sendPatron(this)"> 
     	<p></p>
	  <select multiple id="selectModerators" name="moderators">
	 		<option>Todos los usuarios</option>
	  </select>
		
		<input id= "asociar_moderadores" type="button" value ="Asociar moderadores" onClick = "select_moderators">
	<p></p>


   	<p><b>Asociar usuarios:</p></b>
    <p></p>
    <input autofocus type="text" id="patron" name="patron" required     	</input>

     <input id= "busqueda2" type="button" value ="Buscar" onClick = "sendPatron(this)"> 
     	<p></p>

	<select multiple id="selectUsers" name="targetUsers">
		<option>Todos los usuarios</option>

	<p></p>
	
	<input id= "asociar_usuarios" type="button" value ="Asociar usuarios" onClick = "select_users()">



	<p></p>
	<input id= "save_data" type="button" value ="Guardar datos" onClick = "save_data()">

      

    <!--  <h2>Asociar permiso a usuario: </h2>



   
      <input id="modificar" type="button" value="Modificar tablón" />

      <input id="nuevo" type="button" value="Nuevo tablón" />

  -->
            
      </form>
      


  </section>


</div>
</html>
