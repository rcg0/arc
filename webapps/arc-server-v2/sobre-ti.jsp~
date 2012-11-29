<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.Vector" %>
<%@ page import="arc.User" %>


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
	      <li><a href="profile"  class="current">Sobre ti</a></li>
	      <li><a href="getTablones">Tus tablones</a></li>
          <ul class ="children">
          <li><a href="showModifyTablones">Modificar tablones</a></li>
          <li><a href="showNewTablon">Nuevo tablón</a></li>
          </ul>
	  </ul>
      </nav>
  </header>

<article id = "tablon_content">



</article>



</section>


</html>
