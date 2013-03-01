package com.example.asdf;


import java.util.Vector;
import java.util.regex.Pattern;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;



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
	
	public void printTablon(TextView tablonSubtitle, RatingBar ratingBar, LinearLayout layout,Context context){
		
		Vector<Message> messages = this.getAllMsg();
		
		layout.removeAllViews();
		//tablonSubtitle.setText(this.getSubtitle());
		
		printSomeMessages(messages, layout, context);
		printRate(ratingBar);
		
	}
	
	public void printSomeMessages(Vector<Message> messages, LinearLayout layout,Context context){
		
		for(int i = 0; i<messages.size(); i++){	
			//this.printMessage(messages.elementAt(i).getCreator().getNick(),messages.elementAt(i).getMsg(),layout,context);
			this.printMessage(messages.elementAt(i),layout,context);
		}
	}
	
	public void printMessage(Message message, LinearLayout layout,Context context){
		
		LinearLayout l = new LinearLayout(context);
		l.setOrientation(LinearLayout.HORIZONTAL);			
		TextView author = new TextView(context);
		TextView text = new TextView(context);

		int lastIndex = message.getMsg().lastIndexOf("/");
		
		if(message.getFormat() == 0){//texto
			text.setText(message.getMsg());
			text.setTextColor(Color.BLACK);
		
			author.setText(message.getCreator().getNick()+ ": ");
			author.setPadding(30, 0, 0, 0);
			author.setTypeface(null, Typeface.BOLD);
			author.setTextColor(Color.BLACK);

		}
		else if(message.getFormat() == 1){//image
			
			String name = message.getMsg().substring(lastIndex+1);//el nombre del archivo, necesito ruta + nombre del archivo para confeccinar el link que lleve al archivo
			
			setAsLink(text,"www.google.es", "He compartido una imagen","http://bruckner.gast.it.uc3m.es:8080/arc-server-v3/user-content/"+name);			
			text.setMovementMethod(LinkMovementMethod.getInstance());
			
			author.setText(message.getCreator().getNick()+ ": ");
			author.setPadding(30, 0, 0, 0);
			author.setTypeface(null, Typeface.BOLD);
			author.setTextColor(Color.BLACK);
			
		}else if(message.getFormat() == 2){//audio
			
			String name = message.getMsg().substring(lastIndex+1);//el nombre del archivo, necesito ruta + nombre del archivo para confeccinar el link que lleve al archivo
			
			setAsLink(text,"www.google.es","He compartido un clip de audio","http://bruckner.gast.it.uc3m.es:8080/arc-server-v3/user-content/"+name);			
			text.setMovementMethod(LinkMovementMethod.getInstance());
			
			author.setText(message.getCreator().getNick()+ ": ");
			author.setPadding(30, 0, 0, 0);
			author.setTypeface(null, Typeface.BOLD);
			author.setTextColor(Color.BLACK);	
		
		}else if(message.getFormat() == 3){//video
			
			String name = message.getMsg().substring(lastIndex+1);//el nombre del archivo, necesito ruta + nombre del archivo para confeccinar el link que lleve al archivo
			setAsLink(text,"www.google.es","He compartido un clip de video", "http://bruckner.gast.it.uc3m.es:8080/arc-server-v3/user-content/"+name);			
			text.setMovementMethod(LinkMovementMethod.getInstance());
			
			author.setText(message.getCreator().getNick()+ ": ");
			author.setPadding(30, 0, 0, 0);
			author.setTypeface(null, Typeface.BOLD);
			author.setTextColor(Color.BLACK);	
		}
		
		
		l.addView(author);
		l.addView(text);
		layout.addView(l);
	}
	
	private void setAsLink(TextView view, String url, String message, String startUrl){
        Pattern pattern = Pattern.compile(url);
        Linkify.addLinks(view, pattern, "http://");
        //view.setText(Html.fromHtml("<a href='http://"+url+"'>He compartido una imagen</a>"));
        view.setText(Html.fromHtml("<a href="+startUrl+">"+message+"</a>"));
    }
	
	/*returns the highmessageid or -1 if there isn't messages*/
	public int searchHighMessageId(){
		
		int result=-1;
		
		if(this.msg.size() > 0){
			result = this.msg.elementAt(msg.size()-1).getId();
		}
		
		return result; 
	}
	
	public void printRate(RatingBar ratingBar){
		
		float rate = Float.parseFloat(this.rate);
		ratingBar.setRating(rate);
	}
	
	public void printRate(RatingBar ratingBar, float rate){
		ratingBar.setRating(rate);
	}
}
