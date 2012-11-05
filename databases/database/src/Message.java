

import java.sql.Date;
import java.util.Vector;


public class Message {

	private int id;
	private User creator;
	private String msg;
	private int format;
	//private Byte visibility;
	private int visibility;
	//private Vector <User> targetUsers = new Vector<User>();
	private Date date;

	public void setId(int id){
		
		this.id=id;
		
	}
	
	public int getId(){
		
		return id;
		
	}

	public void setCreator (User user){
		
		this.creator=user;
	}

	public User getCreator(){
		
		return creator;
		
	}
	
	public void setMsg (String msg){
		
		this.msg=msg;
	}
	
	public String getMsg(){
		
		return msg;
		
	}
/*
	public void setTargetUser(User user){
		
		targetUsers.addElement(user);
		
	}
	
	public Vector<User> getTargetUsers(){		
		
		return 	targetUsers;
		
	}
	*/

	public void setVisibility(int visibility){
		
		this.visibility = visibility;
		
	}
	
	public int getVisibility (){
		
		return visibility;
	}
	
	public void setFormat(int format){
		
		this.format = format;
		
	}
	
	public int getFormat(){
		
		return format;
	}

	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date2) {
		this.date = date2;
	}
	
}
