

import java.sql.Date;
import java.util.Vector;

public class exampleMain {


	public static void main(String[] args) {

	  dataBaseManager gestor = new dataBaseManager();
	  User user = new User();
	  user.setName("Javier");
	  user.setSurName1("Rafael");
	  user.setSurName2("Sánchez");
	
	  User user2 = new User();
	  user2.setName("Usuario");
	  user2.setSurName1("Añadido de");
	  user2.setSurName2("Prueba");
	  user2.setId(1);//este es un usuario de prueba evidentemente, le meto el id que corresponde con la base de datos para que funcione
	
	  Message message1 = new Message();
	  //Date date = new Date('2012-11-5');
	  
	  message1.setMsg("prueba de inserción de mensaje con tilde y enies ñ");
	  //message1.setDate(date);
	  message1.setCreator(user2);
	  message1.setFormat(1);
	  message1.setVisibility(4);
	  message1.setDate(new java.sql.Timestamp(0));
	  
	  gestor.createMessage(message1);
	  /*Boolean bool = gestor.checkUser(user);
	  System.out.println(bool);*/
	  
	  //gestor.printTablon(gestor.getTablon("espacio"));
	  
	  //gestor.deleteUser(4);
	  //gestor.setPermissionToUser(3, 4);
	  //gestor.createMessage(message1);
	  
	  
	  /*creo un tablon y le incrusto el mensaje que creo arriba*/
	  /*Tablon tablon = new Tablon();
	  tablon.setName("tablon de prueba");
	  tablon.setSpaceId("espacio de prueba");
	  tablon.setVisibility(4);
	  tablon.setPermission(1);
	  tablon.setId(20);
	  gestor.addTablon(tablon);*/
	  
	  //gestor.setMessageToTablon(8,2);
	  
	  /*Vector<Integer> moderatorsId = new Vector <Integer>();
	  int tablonId = 1;
	  Integer moderator1 = new Integer(3);
	  Integer moderator2 = new Integer(5);
	  moderatorsId.add(moderator1);
	  moderatorsId.add(moderator2);
	  
	  
	  gestor.setModeratorsToTablon(tablonId,moderatorsId);
	  */
	 /*int tablonId = 1;
	 Vector<Integer> targetUsersId = new Vector <Integer>();

	 Integer targetUser1 = new Integer(7);
	 Integer targetUser2 = new Integer(8);
	 targetUsersId.add(targetUser1);
	 targetUsersId.add(targetUser2);
	 gestor.setTargetUsersToTablon(tablonId, targetUsersId);
	  */
	  
	  //System.out.println(gestor.getUserPermission(1, 1));
	  //gestor.deleteMessage(3);
	  
	 //gestor.printTablon(gestor.getTablon("espacio"));
	 
	 /*Vector<Message> msg = gestor.getMessagesFromTablon(1);
	 
	 for(int i=0; i<msg.size();i++){
		 
		 gestor.printMessage(msg.elementAt(i));
		 
	 }*/
	 
	 
	}


	
	
}