package com.example.prueba;

public class Message {

	private String id;
	private User creator;
	private String msg;
	private Byte visibility;
	private Vector <User> targetUsers = new Vector<User>();
	private java.sql.Timestamp date;

	public void setId(String id){
		
		this.id=id;
		
	}
	
	public String getId(){
		
		return id;
		
	}

	public void setCreator (User user){
		
		this.user=user;
	}

	public String getCreator(){
		
		return creator;
		
	}
	
	public void setMsg (String msg){
		
		this.msg=msg;
	}
	
	public String getMsg(){
		
		return msg;
		
	}

	public void setTargetUser(User user){
		
		targetUsers.addElement(user);
		
	}
	
	public Vector<User> getTargetUsers(){		
		
		return 	targetUsers;
		
	}

	public void setVisibility(Byte visibility){
		
		this.visibility = visibility;
		
	}
	
	public String getVisibility (){
		
		return visibility;
	}

	public java.sql.Timestamp getDate() {
		return date;
	}
	
	public void setDate(java.sql.Timestamp date) {
		this.date = date;
	}
	
}
