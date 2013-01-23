package com.example.arc;


import java.util.Vector;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import java.lang.Math;

public class Tablon {

	private int id;
	private String spaceId;
	private String name;
	private String subtitle;
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
	
	public void setSubtitle(String subtitle){
		
		this.subtitle = subtitle;
		
	}
	
	public String getSubtitle (){
		
		return subtitle;
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
	
	public void setSomeMsg(Vector<Message> m){
		for(int i = 0; i<m.size(); i++){
			this.setMsg(m.elementAt(i));
		}
	}
	
	public void printTablon(TextView tablonName, TextView tablonSubtitle, RatingBar ratingBar, LinearLayout layout,Context context){
		
		Vector<Message> messages = this.getAllMsg();
		TextView name;
		TextView author;
		TextView text;
		
		tablonName.setText(this.getName());
		tablonSubtitle.setText(this.getSubtitle());
		
		printSomeMessages(messages, layout, context);
		printRate(ratingBar);
		
	}
	
	public void printSomeMessages(Vector<Message> messages, LinearLayout layout,Context context){
		
		for(int i = 0; i<messages.size(); i++){	
			this.printMessage(messages.elementAt(i).getCreator().getNick(),messages.elementAt(i).getMsg(),layout,context);
		}
	}
	
	public void printMessage(String nick, String msg, LinearLayout layout,Context context){
		
		LinearLayout l = new LinearLayout(context);
		l.setOrientation(LinearLayout.HORIZONTAL);			
		TextView author = new TextView(context);
		TextView text = new TextView(context);
		
		
		text.setText(msg);
		text.setTextColor(Color.BLACK);
		
		author.setText(nick+ ": ");
		author.setPadding(50, 0, 0, 0);
		author.setTypeface(null, Typeface.BOLD);
		author.setTextColor(Color.BLACK);

		l.addView(author);
		l.addView(text);
		layout.addView(l);
	}
	
	public int searchHighMessageId(){
		
		int higherMessageId = this.msg.elementAt(msg.size()-1).getId();
		
		return higherMessageId; 
	}
	
	public void printRate(RatingBar ratingBar){
		
		float rate = Float.parseFloat(this.rate);
		ratingBar.setRating(rate);
	}
	
	public void printRate(RatingBar ratingBar, float rate){
		ratingBar.setRating(rate);
	}
}
