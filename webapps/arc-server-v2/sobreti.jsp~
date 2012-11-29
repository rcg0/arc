<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.Vector" %>
<%@ page import="arc.User" %>
<%@ page import="arc.Tablon" %>



<!DOCTYPE html>
<html lang="es">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	<title>ARC</title>
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
   
   <nav id="main_nav">
      <ul>
	<li><a href="profile" class = "current">Perfil</a></li>
	<li><a href="showModifyTablones">Mis tablones</a></li>
	<li><a href="getTablones" >Mensajes</a></li>
      </ul>
   </nav>
</header>



</div>

</body>

</html>
