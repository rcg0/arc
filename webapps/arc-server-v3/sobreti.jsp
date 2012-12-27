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
	<link href="style.css" rel="stylesheet" type="text/css" media="screen" />
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
   
  <ul class= "menu">
	         <li><a href="profile" class = "current">Perfil</a></li>
	         <li><a href="#">Mis tablones</a>

              <ul id="submenu_tablones">
                  <li><a href="showModifyTablones">Nuevo tablon</a>
                  </li>
                  
              </ul>
            </li>
	          <li><a href="#" >Mensajes</a>
                <ul id="submenu_mensajes">
                  
                </ul>
            </li>
   </ul>

</header>



</div>

</body>

</html>
