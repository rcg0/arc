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

   <nav id="main_nav">
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
   </nav>
</header>

<!-- a partir de aqui es lo que varía d
e una sección a otra -->


   <nav id="tablon_name">
   
   
   </nav>


   <section id="left">

	<div class="mensaje">
	  <h2>Identificador del tablón:</h2>
	  <%if(tablon!=null){%>
	  <p><%=tablon.getId()%></p>
	  <%}%>
	</div>
	<p></p>
	<p></p>

	<div class="mensaje">
	  <h2>Nombre de tablón:</h2>
	  <%if(tablon!=null){%>
	  <p><%=tablon.getName()%></p>
	  <%}%>
	</div>
	<p></p>
	<p></p>
	<div class="mensaje">
	  <h2>Moderadores asociados:</h2>

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
	  <h2>Usuarios asociados:</h2>
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
	  <h2>Permisos asociados a usuario:</h2>
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
    


      <form method = "post" action= "deleteTablon">

      <input id="eliminar" type="submit" value="Eliminar tablón"/>
      </form>


      <form method="post" action="#">
      <h2>Nombre del tablón:</h2>
      <input type="text" value=""/>
      <p></p>
      <h2>Asociar moderadores: </h2>


	  <input autofocus type="text" id="patron" name="patron" onKeyDown ="sendPatron()" required>
     	</input>


	  <select  name="moderators">
	 	<option value="pepito">Javier Rafael Sánchez
	  	</option>
		
	<p></p>

      <h2>Asociar usuarios: </h2>

	<select name="targetUsers">
	 <option value="">
	  </option>
	<p></p>
      

    <!--  <h2>Asociar permiso a usuario: </h2>



   
      <input id="modificar" type="button" value="Modificar tablón" />

      <input id="nuevo" type="button" value="Nuevo tablón" />

  -->
            
      </form>
      


  </section>


</div>
</html>