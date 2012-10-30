package com.example.prueba;

import java.util.Vector;

public class Tablon {

	private String id;
	private String spaceId;
	private String name;
	private Vector <User> users = new Vector<User>();
	private Byte permission;
	private Byte visibility;
	private Vector <User> targetUsers = new Vector<User>();
	private Vector <Message> msg = new Vector<Message>();

	public void setId(String id){
		
		this.id = id;
		
	}
	
	public String getId (){
		
		return id;
	}

	public void setSpaceId(String spaceId){
		
		this.spaceId = spaceId;
		
	}
	
	public String getSpaceId (){
		
		return spaceId;
	}

	
	
	public void setUser(User user){
		
		users.addElement(user);
		
	}
	
	public Vector<User> getUsers(){		
		
		return 	users;
		
	}


	public void setPermission(Byte permission){
		
		this.permission = permission;
		
	}
	
	public String getPermission (){
		
		return permission;
	}

	public void setVisibility(Byte visibility){
		
		this.visibility = visibility;
		
	}
	
	public String getVisibility (){
		
		return visibility;
	}

	public void setTargetUser(User user){
		
		targetUsers.addElement(user);
		
	}
	
	public Vector<User> getTargetUsers(){		
		
		return 	targetUsers;
		
	}

	public void setMsg(Mensaje mensaje){
		
		msg.addElement(mensaje);
		
		
	}
	
	public Vector<Mensaje> getMsg(){		
		
		return 	msg;
		
	}
	
}
