<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.Vector" %>
<%@ page import="arc.User" %>


﻿<!DOCTYPE html>
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
	      <li><a href="getTablones">Tus tablones</a></li>
	      <ul class ="children">
          <li><a href="showModifyTablones">Modificar tablones</a></li>
          <li><a href="showNewTablon" class ="current">Nuevo tablón</a></li>
          </ul>
	  </ul>
      </nav>
  </header>

  <nav id="tablon_name">
	  <ul>
	      <li><a href="http://www.google.es">Global</a></li>
	      <li><a href="#">Alumnos</a></li>
 
	  </ul>
      </nav>

<article id = "tablon_content">

      <div id="gestion_1">
	<p>Nombre del tablón</p>
	<input type="text">

      </div>
      
      <div id="gestion_2">
	
	<p id="asociar">Asociar moderadores</p>

	  <div id="checkbox_gestion">
	    
        <select multiple="multiple" class ="selection">
        <option>Javier</option>
        <option>Jaime</option>
        <option>Nuria</option>
        <option>Francisco Hernánde Casado</option>
        
        </select>  
        <!--<p><input type="checkbox" name="Jaime_m"/>Jaime</p>
	    <p><input type="checkbox" name="Javier_m"/>Javier</p>
	    <p><input type="checkbox" name="Nuria_m"/>Nuria</p>
	    <p><input type="checkbox" name="Francisco Hernández Casadoddddddd_m"/>Francisco Hernández Casadoddddddd</p>
	    <p><input type="checkbox" name="Miriam_m"/>Miriam</p>
	    <p><input type="checkbox" name="Vilar_m"/>Vilar</p>
	    <p><input type="checkbox" name="Alfonso_m"/>Alfonso</p>
	    <p><input type="checkbox" name="Isabel_m"/>Isabel</p> -->
	  </div>

      </div>
      
      <div id="gestion_3">

	  <p id="asociar">Asociar usuarios</p>

	  <div id="checkbox_gestion">
	    
        <select multiple="multiple" class="selection">
        <option>Javier</option>
        <option>Jaime</option>
        <option>Nuria</option>
        <option>Francisco Hernánde Casado</option>
         <option>Francisco Hernánde Casado</option>
        <option>Francisco Hernánde Casado</option>
        <option>Francisco Hernánde Casado</option>
        <option>Francisco Hernánde Casado</option>
        <option>Francisco Hernánde Casado</option>
        <option>Francisco Hernánde Casado</option>

          
         <!--<p><input  class="check" type="checkbox"  name="Jaime"/>Jaime</p>
	    <p><input  class="check" type="checkbox"  name="Javier"/>Javier</p>
	    <p><input type="checkbox"  name="Nuria"/>Nuria</p>
	    <p><input type="checkbox"  name="Francisco Hernández Casadoddddddd"/>Francisco Hernández Casadoddddddd</p>
	    <p><input type="checkbox"  name="Miriam"/>Miriam</p>
	    <p><input type="checkbox" name="Vilar"/>Vilar</p>
	    <p><input type="checkbox"  name="Alfonso"/>Alfonso</p>
	    <p><input type="checkbox"  name="Isabel"/>Isabel</p>  -->
	  </div>

  
      </div>

      <div id="gestion_4">
	  

      </div>
      
      <div id="gestion_5">
    
      </div>

</article>

</section>


</html>




