package arc;

import java.util.Vector;
import java.util.BitSet;

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
	private int lastMessageId;
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

	public void setLastMessageId(int lastMessageId){
		
		this.lastMessageId = lastMessageId;
		
	}
	
	public int getLastMessageId (){
		
		return lastMessageId;
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

	public void setSomeMsg(Vector<Message> m){
		for(int i = 0; i<m.size(); i++){
			this.setMsg(m.elementAt(i));
		}
	}

	public long sendMessage(Message message){
		DataBaseManager manager = new DataBaseManager();
		String msg = "";
		if(message.getFormat() == 0){

			message.setMsg(manager.sanitizer(message.getMsg()));//saneo la cadena antes de pasarla a la base de datos.
		}else{
			message.setMsg(message.getMsg());
		}	

		return manager.createMessage(message, this.getId());
	}


	public Tablon getSoftTablonInformation(int id){
		Tablon tablon = null;
		DataBaseManager manager = new DataBaseManager();
		tablon = manager.getSoftTablon(id);
		
		return tablon;
	}

	public void deleteMessageFromTablon(String messageId){

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

	public Vector<Tablon> getTablonDDBB(String space){
		Vector<Tablon> tablones = null;
		DataBaseManager manager = new DataBaseManager();
		tablones = manager.getTablon(space);
		
		return tablones;
	}

	public Vector<Message> getBeforeMessages(int messageId,int limit){

		DataBaseManager manager = new DataBaseManager();

		return manager.getBeforeMessages(messageId,limit);


	}

	public Tablon getAfterMessages(int messageId, int tablonId){

		DataBaseManager manager = new DataBaseManager();

		return manager.getAfterMessages(messageId, tablonId);


	}

	public float setMediaToTablon(float newRate){

		System.out.println("SetMediaToTablon en tablon");
		DataBaseManager manager = new DataBaseManager();		

		return manager.setMediaToTablon(this, newRate);

	}



}
