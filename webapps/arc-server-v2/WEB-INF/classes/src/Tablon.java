package arc;

import java.util.Vector;
import java.util.BitSet;

public class Tablon {

	private int id;
	private String spaceId;
	private String name;
	private Vector <User> users = new Vector<User>();
	private int permission;//?
	private int visibility;
	private Vector <User> targetUsers = new Vector<User>();
	private Vector <Message> msg = new Vector<Message>();

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
	
	public void setAllUsers(Vector<User> users){
		
		this.users = users; 
		
	}
	
	public void setUser(User user){
		
		users.addElement(user);
		
	}

	public Vector<User> getUsers(){
	  
	    return users;
      
	}
	
	public Vector<User> getUsersDB(int idTablon){		
		
		DataBaseManager manager = new DataBaseManager();
		return 	manager.getTablonModerateUsers(idTablon);
				
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

	public void getSomeMsg(int limit){
		
		DataBaseManager manager = new DataBaseManager();
		manager.getSomeMessagesFromTablon(this,limit);

	}
	
	public void setAllMsg(Vector<Message> m){
		
		msg=m;
		
	}
	

	
	public void setMsg(Message mensaje){

		msg.addElement(mensaje);
	}

	public void sendMessage(Message message){
	      
		DataBaseManager manager = new DataBaseManager();
		manager.createMessage(message, this.getId());
	}


	public Tablon getSoftTablonInformation(int id){
		Tablon tablon = null;
		DataBaseManager manager = new DataBaseManager();
		tablon = manager.getSoftTablon(id);
		
		return tablon;
	}

	public void deleteMessageFromTablon(int messageId){

	  DataBaseManager manager = new DataBaseManager();
	  manager.deleteMessage(messageId);


	}

	public void deleteTablon(){

	  DataBaseManager manager = new DataBaseManager();
	  manager.deleteTablon(this.getId());

	}

	public Vector<String> getTablonNames(Vector<Integer> tablonId){

	    DataBaseManager manager = new DataBaseManager();
	    return manager.getTablonNames(tablonId);

	}

	public Tablon getTablonDDBB(int id){
		Tablon tablon = null;
		DataBaseManager manager = new DataBaseManager();
		tablon = manager.getTablon(id);
		
		return tablon;
	}

}
