package com.example.asdf;

import java.util.Vector;


public class User {

  private int id;
  private String nick;
  private String name;
  private String surname1;
  private String surname2;
  private Vector<Integer> author = new Vector<Integer>();
  private Vector<Integer> moderator = new Vector<Integer>();
  private String genre;
  private String age;
  private String work;
  //private byte permission;
  private int permission;
  
  public void setId(int id){
		
	this.id = id;
		
  }

  public int getId (){
		
	return id;
  }

    public void setNick(String nick){
		
	this.nick=nick;
  }

  public String getNick(){
		
	return nick;
		
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

  public void setSurName2(String surname2){
		
	this.surname2=surname2;
  }

  public String getSurName2(){
		
	return surname2;
		
  }

  public void setGenre(String genre){
		
	this.genre=genre;
  }

  public String getGenre(){
		
	return genre;
		
  }

 public void setAge(String age){
		
	this.age=age;
  }

  public String getAge(){
		
	return age;
		
  }

 public void setWork(String work){
		
	this.work=work;
  }

  public String getWork(){
		
	return work;
		
  }

  public void setAuthor(int authorIndex){
		
	author.addElement(authorIndex);
		
  }
	
  public Vector<Integer> getAuthor(){		
		
	return 	author;
		
  }

 public void setModerator(int moderatorIndex){
		
	moderator.addElement(moderatorIndex);
		
  }
  
  public void setAllModerators(Vector<Integer> mod){
  
    moderator = mod;
  
  }

  public void setPermission(int permission){
		
	this.permission = permission;
		
  }
	
  public int getPermission (){
		
	return permission;
  }
  
  public Boolean isValid(){
	  Boolean result = false;
	  if (this.id != 0) {
		  result = true;
	  }
	  return result;
  }
}
