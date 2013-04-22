package arc;

import java.sql.Date;
import java.util.Vector;


public class Message {

	private int id;
	private User creator;
	private String msg;
	private int format;
	private int visibility;
	private Vector <User> targetUsers = new Vector<User>();
	private java.sql.Timestamp date;

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

	public void setTargetUser(User user){
		
		targetUsers.addElement(user);
		
	}
	
	public void setAllTargetUser(Vector<User> u){
		
		targetUsers = u;
		
		
	}


	public Vector<User> getTargetUsers(){		
		
		return 	targetUsers;
		
	}
	

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

	public java.sql.Timestamp getDate() {
		return date;
	}
	
	public void setDate(java.sql.Timestamp date2) {
		this.date = date2;
	}

	public boolean checkIfUserBelongsToMessage(User user){

		boolean result = false;

		if(user != null && this.getTargetUsers().size() > 0){
			
			for(int i = 0; i < this.targetUsers.size(); i++){

				if(this.targetUsers.elementAt(i).getId() == user.getId()){
					result = true;
				}

			}
		}

		return result;

	}

	
}
