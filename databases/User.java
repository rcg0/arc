package com.example.prueba;

import java.util.Vector;

public class User {

  private int id;
  private string name;
  private string surname1;
  private string surname2;
  private Vector<int> author = new Vector<int>();
  private Vector<int> moderator = new Vector<int>();
  private byte perimssion;
  
  public void setId(String id){
		
	this.id = id;
		
  }

  public String getId (){
		
	return id;
  }

  public void setName(String name){
		
	this.name=name;
  }

  public String getName(){
		
	return name;
		
  }

  public void setSurName1(String surname1){
		
	this.surname1=surname1;
  }

  public String getSurName1(){
		
	return surname1;
		
  }

  public void setSurName2(String surname1){
		
	this.surname2=surname2;
  }

  public String getSurName2(){
		
	return surname2;
		
  }

  public void setAuthor(int authorIndex){
		
	author.addElement(authorIndex);
		
  }
	
  public Vector<int> getAuthor(){		
		
	return 	author;
		
  }

 public void setModerator(int moderatorIndex){
		
	moderator.addElement(moderatorIndex);
		
  }
	
  public Vector<int> getModerator(){		
		
	return 	moderator;
		
  }

  public void setPermission(Byte permission){
		
	this.permission = permission;
		
	}
	
	public String getPermission (){
		
		return permission;
	}


} 