

import java.util.Vector;

public class exampleMain {


	public static void main(String[] args) {

	  dataBaseManager gestor = new dataBaseManager();
	  User user = new User();
	  user.setName("Javier");
	  user.setSurName1("Rafael");
	  user.setSurName2("Sánchez");
	
	  /*Boolean bool = gestor.checkUser(user);
	  System.out.println(bool);*/
	  
	  gestor.printTablon(gestor.getTablon("espacio"));
	
	}
	
	
}