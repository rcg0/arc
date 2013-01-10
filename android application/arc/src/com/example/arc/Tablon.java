package com.example.arc;


import java.util.Vector;

import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Tablon {

	private int id;
	private String spaceId;
	private String name;
	private Vector <User> users = new Vector<User>();
	private int permission;//?
	private int visibility;
	private Vector <User> targetUsers = new Vector<User>();
	private Vector <Message> msg = new Vector<Message>();
	private String rate;

	public void setId(int id){
		
		this.id = id;
		
	}
	
	public int getId (){
		
		return id;
	}

	public void setSpaceId(String spaceId){
		
		this.spaceId = spaceId;
		
	}
	
	public String getSpaceId (){
		
		return spaceId;
	}

	public void setName(String name){
		
		this.name=name;
		
	}
	
	public String getName(){
		
		return name;
	}

	public void setRate(String rate){
		
		this.rate=rate;
		
	}
	
	public String getRate(){
		
		return rate;
	}
	
	public void setAllUsers(Vector<User> users){
		
		this.users = users; 
		
	}
	
	public void setUser(User user){
		
		users.addElement(user);
		
	}

	public Vector<User> getUsers(){
	  
	    return users;
      
	}

	public void setPermission(int permission){
		
		this.permission = permission;
		
	}
	
	public int getPermission (){
		
		return permission;
	}

	public void setVisibility(int visibility){
		
		this.visibility = visibility;
		
	}
	
	public int getVisibility (){
		
		return visibility;
	}
	
	public void setAllTargetUser(Vector<User> u){
		
		targetUsers = u;
		
		
	}

	public void setTargetUser(User user){
		
		targetUsers.addElement(user);
		
	}
	
	public Vector<User> getTargetUsers(){		
		
		return 	targetUsers;
		
	}

	public Vector<Message> getAllMsg(){		
		
		return 	msg;
		
	}
	
	public void setAllMsg(Vector<Message> m){
		
		msg=m;
		
	}
	
	public void setMsg(Message mensaje){

		msg.addElement(mensaje);
	}
	
	public void printTablon(TextView tablonName, LinearLayout layout,Context context){
		
		Vector<Message> messages = this.getAllMsg();
		TextView name;
		TextView author;
		TextView text;
		
		tablonName.setText(this.getName());
		
		for(int i = 0; i<messages.size(); i++){
			author = new TextView(context);
			text = new TextView(context);
			
			author.setText(messages.elementAt(i).getCreator().getNick());
			author.setTextColor(Color.BLACK);
			text.setText(messages.elementAt(i).getMsg());
			author.setPadding(50, 0, 50, 0);
			text.setPadding(50, 0, 50, 5);
			text.setTextColor(Color.GRAY);
			
			layout.addView(author);
			layout.addView(text);
		}
		
	}
	
	public void printMessage(String nick, String msg, LinearLayout layout,Context context){
		
		TextView author = new TextView(context);
		TextView message = new TextView(context);
		
		author.setText(nick);
		message.setText(msg);
		
		author.setTextColor(Color.BLACK);
		message.setTextColor(Color.GRAY);

		author.setPadding(50, 0, 50, 0);
		message.setPadding(50, 0, 50, 5);
		
		layout.addView(author);
		layout.addView(message);
	}
}
