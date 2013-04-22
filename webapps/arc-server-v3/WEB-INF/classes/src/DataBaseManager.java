package arc;


import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;

import javax.sql.*;

import java.sql.*;
import javax.sql.*;
import java.util.*;
import java.lang.String;



public class DataBaseManager{

	String error ="";
	public Connection openConnectionPool(){
		Connection conn=null;		
		try{	    	
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			DataSource ds = (DataSource) envCtx.lookup("jdbc/BaseDeDatos");
			conn = ds.getConnection();
			
			//String url= "jdbc:mysql://127.0.0.1:3306/BaseDeDatos";
			//conn = DriverManager.getConnection(url,"jrafael", "hmspawnn");
			//conn = DriverManager.getConnection(url,"root","hmspawnn"); //"givyijRod9");
		}catch(SQLException ex){
			System.out.println("SQLException: " + ex.getMessage());
                        System.out.println("SQLState: " + ex.getSQLState());
                        System.out.println("VendorError: " + ex.getErrorCode());

		}catch(NamingException e){
			System.out.println("NamingException: " + e.getMessage());
		}
		return conn;
	}

	private void closeConnectionPool(Connection conn){
		try{
			conn.close();			
		}catch(SQLException ex){
			System.out.println("SQLException: " + ex.getMessage());
                        System.out.println("SQLState: " + ex.getSQLState());
                        System.out.println("VendorError: " + ex.getErrorCode());
		}	
	}



	public User checkUser(User user) {
		User databaseUser=new User();
		
		try{
			Connection conn = openConnectionPool();
			/***********************************************PARAMETRIZACIÓN*************************************************/
			PreparedStatement statement = conn.prepareStatement("SELECT * FROM User WHERE name=?"); //AND surname1 = ? AND surname2 = ?");
			statement.setString(1, user.getName());
			/*statement.setString(2, user.getSurName1());
			statement.setString(3, user.getSurName2());*/
			/****************************************************************************************************************/

			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				databaseUser = new User();
				databaseUser.setId(rs.getInt("id"));
				databaseUser.setName(rs.getString("name"));
				databaseUser.setNick(rs.getString("nick"));
				databaseUser.setSurName1(rs.getString("surname1"));
				databaseUser.setSurName2(rs.getString("surname2"));
				databaseUser.setAllModerators(getIdTablonModerateUsers(databaseUser.getId()));
			}

			System.out.println("Lo que hay en la base de datos: ");
			System.out.println("name:" + databaseUser.getName());
			closeConnectionPool(conn);
		}catch(SQLException ex){
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		
		
		return databaseUser;
	}

	
	/*
	*Método que recoge a usuarios con nombres o apellidos que empiecen por @param String name
	*Devuelve el array con los usuarios encontrados.
	*/
	public Vector<User> getUserStartsWith(String name) {

		Vector<User> users=new Vector<User>();
		User databaseUser = null;

		try{
			Connection conn = openConnectionPool();
			System.out.println(name+"%");

			/***********************************************PARAMETRIZACIÓN*************************************************/
			PreparedStatement statement = conn.prepareStatement("SELECT * FROM User WHERE name LIKE ? OR surname1 LIKE ? OR surname2 LIKE ?;");
			statement.setString(1, name+"%");
			statement.setString(2, name+"%");
			statement.setString(3, name+"%");
			/****************************************************************************************************************/

			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				databaseUser = new User();
				databaseUser.setId(rs.getInt("id"));
				databaseUser.setName(rs.getString("name"));
				databaseUser.setSurName1(rs.getString("surname1"));
				databaseUser.setSurName2(rs.getString("surname2"));
				databaseUser.setAllModerators(getIdTablonModerateUsers(databaseUser.getId()));
				users.addElement(databaseUser);


				System.out.println("Nombre:" + databaseUser.getName());
			}

			closeConnectionPool(conn);
		}catch(SQLException ex){
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		
		
		return users;
	}

	/*
	*@param space is the tablon space, it identifies a set of tablons
	*@param user is the session user, it is used basically for filter tasking.
	*/

	public Vector<Tablon> getTablon(String space, User user){
		
		Vector<Tablon> tablones = new Vector<Tablon>();
		Tablon tablon = null;

		try{
			Connection conn = openConnectionPool();
			/***********************************************PARAMETRIZACIÓN*************************************************/
			PreparedStatement statement = conn.prepareStatement("SELECT * FROM Tablon WHERE space=?"); //AND surname1 = ? AND surname2 = ?");
			statement.setString(1, space);

			/****************************************************************************************************************/

			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				tablon = new Tablon();
				int idTablon = rs.getInt("id");
				tablon.setId(idTablon);
				tablon.setName(rs.getString("name"));
				tablon.setSubtitle(rs.getString("subtitle"));
				System.out.println("Lo que saca de la bbdd, el nombre de tablon es : "+rs.getString("name"));
				tablon.setVisibility(rs.getInt("visibility"));
				tablon.setSpaceId(rs.getString("space"));
				//tablon.setPermission(rs.getInt("permission"));
				tablon.setRate(readRateDDBB(tablon));
				tablon.setAllMsg(getMessagesFromTablon(idTablon, user));
				tablon.setAllTargetUser(getTablonTargetUsers(idTablon));
				tablon.setAllUsers(getTablonModerateUsers(idTablon));

				//check if the visibility is the correct, if not, tablon is not added.
				if(tablon.getVisibility()== 0 || tablon.getVisibility() == 1 
					|| tablon.checkIfUserBelongsToTablon(user)){//0->anoniymous, 1->public
					
					tablones.addElement(tablon);	
					
				}
				
			}
			closeConnectionPool(conn);

			
		}catch(SQLException ex){
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		
		
		
		return tablones;
		
	}


	/*Obtiene el tablón según su id, muestra los mensajes asociados*/
	
	public Tablon getTablon(int id){
		
		Tablon tablon = null;
		
		try{
			Connection conn = openConnectionPool();
			/***********************************************PARAMETRIZACIÓN*************************************************/
			PreparedStatement statement = conn.prepareStatement("SELECT * FROM Tablon WHERE id=?"); //AND surname1 = ? AND surname2 = ?");
			statement.setInt(1, id);

			/****************************************************************************************************************/

			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				tablon = new Tablon();
				int idTablon = rs.getInt("id");
				tablon.setId(idTablon);
				tablon.setName(rs.getString("name"));
				tablon.setVisibility(rs.getInt("visibility"));
				tablon.setSpaceId(rs.getString("space"));
				//tablon.setPermission(rs.getInt("permission"));
				tablon.setAllMsg(getMessagesFromTablon(idTablon, null));//fix it... is not user hasn't be null
				tablon.setAllTargetUser(getTablonTargetUsers(idTablon));
				tablon.setAllUsers(getTablonModerateUsers(idTablon));
			}
			closeConnectionPool(conn);

			
		}catch(SQLException ex){
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		
		
		
		return tablon;
		
	}

	
	/*Obtiene el tablón según su id ---> sin mensajes!!! si no no escala, tengo que ir pidiéndolos dinámicamente*/
	
	public Tablon getSoftTablon(int id){
		
		Tablon tablon = null;
		
		try{
			Connection conn = openConnectionPool();
			/***********************************************PARAMETRIZACIÓN*************************************************/
			PreparedStatement statement = conn.prepareStatement("SELECT * FROM Tablon WHERE id=?");
			statement.setInt(1, id);
			/****************************************************************************************************************/

			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				tablon = new Tablon();
				int idTablon = rs.getInt("id");
				tablon.setId(idTablon);
				tablon.setName(rs.getString("name"));
				tablon.setVisibility(rs.getInt("visibility"));
				tablon.setSpaceId(rs.getString("space"));
				//tablon.setPermission(rs.getInt("permission"));
				
				//tablon.setAllMsg(getMessagesFromTablon(idTablon));

				tablon.setAllTargetUser(getTablonTargetUsers(idTablon));
				tablon.setAllUsers(getTablonModerateUsers(idTablon));
			}
			closeConnectionPool(conn);

			
		}catch(SQLException ex){
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		
		
		
		return tablon;
		
	}


	public Vector<String> getTablonNames(Vector<Integer> tablonId){
		
		Vector<String> names = new Vector<String>();

		String query = new String("SELECT name FROM Tablon WHERE  ");

		for(int i = 0; i<tablonId.size(); i++){

		      if(i!=0){//la primera vez no hay OR que valga :)

			  query= query+" OR";

		      }
		      query= query+" id = "+tablonId.elementAt(i);
		}
		query= query+";";
		System.out.println(query);



		try{
			Connection conn = openConnectionPool();
			PreparedStatement statement = conn.prepareStatement(query);
			ResultSet rs = statement.executeQuery();

			int vez = 0;
			while (rs.next()) {

				String anadir = new String(rs.getString("name"));
				System.out.println(vez+" "+anadir);
				names.addElement(anadir);
				vez++;
				
			}
			closeConnectionPool(conn);

			
		}catch(SQLException ex){
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		
		
		
		return names;
		
	}

	
	public boolean addTablon(Tablon tablon){
		boolean result= false;
		
		try {
			Connection conn = openConnectionPool();
			Statement stmt = conn.createStatement();
			
			PreparedStatement statement = conn.prepareStatement("INSERT INTO Tablon (name, visibility, space) VALUES (?, ?, ?)");
				
			statement.setString(1, tablon.getName());
			statement.setInt(2, tablon.getVisibility());
			statement.setString(3, tablon.getSpaceId());
		
			int aux = statement.executeUpdate();
			if(aux!=0)
				result=true;
		
			closeConnectionPool(conn);
			
		}catch(SQLException ex){
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		
		return result;
	}

	



/*DEBUG HERE*/
	public Vector<Message> getMessagesFromTablon(int idTablon, User user){
		
		Vector<Message> msg = new Vector<Message>();
		
		try{
			Connection conn = openConnectionPool();
			/*select * from TablonMessage INNER JOIN Message ON TablonMessage.message_id = Message.id INNER JOIN User ON Message.user_id = User.id WHERE TablonMessage.id = ?;*/
			/***********************************************PARAMETRIZACIÓN*************************************************/
			PreparedStatement statement = conn.prepareStatement("SELECT * FROM Message INNER JOIN User ON Message.user_id=User.id WHERE tablon_id = ? ORDER BY dateTime ASC;"); 
			statement.setInt(1, idTablon);
			/****************************************************************************************************************/

			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				Message m = new Message();
				User u = new User();
				m.setId(rs.getInt("id"));
				m.setMsg(rs.getString("message"));
				m.setVisibility(rs.getInt("visibility"));
				m.setFormat(rs.getInt("format"));
				m.setAllTargetUser(getMessageTargetUsers(m.getId()));
				//m.setDate(rs.getTimestamp("dateTime"));//peta aquí
				u.setId(rs.getInt("Message.user_id"));
				u.setNick(rs.getString("User.nick"));
				u.setName(rs.getString("User.name"));
				u.setSurName1(rs.getString("User.surname1"));
				u.setSurName2(rs.getString("User.surname2"));

				m.setCreator(u);

				if(m.getVisibility() == 0 || m.getVisibility() == 1 || m.checkIfUserBelongsToMessage(user)){

					msg.addElement(m);

				}
			}

			closeConnectionPool(conn);
					
			}catch(SQLException ex){
				System.out.println("SQLException: " + ex.getMessage());
				System.out.println("SQLState: " + ex.getSQLState());
				System.out.println("VendorError: " + ex.getErrorCode());
			}
		
		return msg;
		
	}
	
	public Vector<User> getMessageTargetUsers(int idMessage){

		Vector<User> targetUsers = new Vector<User>();
		
		try{
			Connection conn = openConnectionPool();
			/***********************************************PARAMETRIZACIÓN*************************************************/
		
			PreparedStatement statement = conn.prepareStatement("SELECT User.id, User.nick, User.name, User.surname1, User.surname2, User.work, User.age, User.genre FROM MessageTargetUser INNER JOIN User ON MessageTargetUser.user_id = User.id WHERE MessageTargetUser.message_id = ?"); 
			statement.setInt(1, idMessage);
			/***************************	*************************************************************************************/
			System.out.println("int idMessage: "+idMessage);
			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				
				User u = new User();
				u.setId(rs.getInt("User.id"));
				u.setName(rs.getString("User.name"));
				u.setNick(rs.getString("User.nick"));
				System.out.println("El usuario/s es/son:"+ u.getNick());
				u.setWork(rs.getString("User.work"));
				u.setAge(rs.getString("User.age"));
				u.setGenre(rs.getString("User.genre"));
				u.setSurName1(rs.getString("User.surname1"));
				u.setSurName2(rs.getString("User.surname2"));

				targetUsers.addElement(u);
				
			}

			closeConnectionPool(conn);
					
			}catch(SQLException ex){
				System.out.println("SQLException: " + ex.getMessage());
				System.out.println("SQLState: " + ex.getSQLState());
				System.out.println("VendorError: " + ex.getErrorCode());
			}
		
		return targetUsers;
		

	}


	/*Obtiene un tablon_id que corresponde a un message_id*/

	public int getTablonIdFromMessageId(int message_id){

		int tablon_id=-1;

		try{
			Connection conn = openConnectionPool();

			PreparedStatement statement = conn.prepareStatement("SELECT tablon_id FROM Message WHERE id = ?;"); 
			statement.setInt(1,message_id);

			ResultSet rs = statement.executeQuery();
			if(rs.next()){
				tablon_id = rs.getInt("tablon_id");
			}

			closeConnectionPool(conn);
			
			}catch(SQLException ex){
				System.out.println("SQLException: " + ex.getMessage());
				System.out.println("SQLState: " + ex.getSQLState());
				System.out.println("VendorError: " + ex.getErrorCode());
			}

		return tablon_id;

	}


	/*Obtiene los mensajes anteriores a un message_id dado*/

	public Vector<Message> getBeforeMessages(int tablonId, int messageId, int limit){

		Vector <Message> msg = new Vector<Message>();
		try{
			Connection conn = openConnectionPool();


			/*select * from TablonMessage INNER JOIN Message ON TablonMessage.message_id = Message.id INNER JOIN User ON Message.user_id = User.id WHERE TablonMessage.id = ?;*/
			/***********************************************PARAMETRIZACIÓN*************************************************/
			PreparedStatement statement = conn.prepareStatement("SELECT * FROM Message INNER JOIN User ON Message.user_id=User.id WHERE tablon_id = ? AND Message.id < ? ORDER BY dateTime DESC limit ?;"); 
			
			statement.setInt(1, tablonId);
			statement.setInt(2, messageId);
			statement.setInt(3, limit);
				/****************************************************************************************************************/
			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				Message m = new Message();
				User u = new User();
				m.setId(rs.getInt("id"));
				m.setFormat(Integer.parseInt(rs.getString("format")));
				//System.out.println(rs.getString("Message.message"));
				m.setMsg(rs.getString("Message.message"));
				//System.out.println("visibility:"+rs.getInt("visibility"));
				m.setVisibility(rs.getInt("visibility"));
				//m.setDate(rs.getTimestamp("dateTime"));//peta aquí
				u.setId(rs.getInt("Message.user_id"));
				u.setName(rs.getString("User.name"));
				u.setNick(rs.getString("User.nick"));
				//System.out.println(rs.getString("User.name"));
				u.setSurName1(rs.getString("User.surname1"));
				u.setSurName2(rs.getString("User.surname2"));
				m.setCreator(u);
				msg.addElement(m);
			}	

			closeConnectionPool(conn);
				
		}catch(SQLException ex){
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}

		return msg;

	}

	/*Obtiene los mensajes posteriores a un message_id dado*/

	public Tablon getAfterMessages(int messageId, int tablonId){

		Tablon tablon = new Tablon();
		Vector <Message> msg = new Vector<Message>();

		try{
			Connection conn = openConnectionPool();

			/***********************************************PARAMETRIZACIÓN*************************************************/
			PreparedStatement statement = conn.prepareStatement("SELECT * FROM Message INNER JOIN User ON Message.user_id=User.id WHERE tablon_id = ? AND Message.id > ?"); 
			
			statement.setInt(1, tablonId);
			statement.setInt(2, messageId);
			
			System.out.println("dentro del manager:  vemos que tablonId es :"+ tablonId+ " y el messageId es: "+messageId);

				/****************************************************************************************************************/
			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				Message m = new Message();
				User u = new User();
				m.setId(rs.getInt("id"));

				System.out.println("El mensaje de la base de datos es: "+rs.getString("Message.message"));
				m.setMsg(rs.getString("Message.message"));
				//System.out.println("visibility:"+rs.getInt("visibility"));
				m.setVisibility(rs.getInt("visibility"));
				m.setFormat(rs.getInt("Message.format"));
				//m.setDate(rs.getTimestamp("dateTime"));//peta aquí
				u.setId(rs.getInt("Message.user_id"));
				u.setName(rs.getString("User.name"));
				u.setNick(rs.getString("User.nick"));
				//System.out.println(rs.getString("User.name"));
				u.setSurName1(rs.getString("User.surname1"));
				u.setSurName2(rs.getString("User.surname2"));
				m.setCreator(u);
				msg.addElement(m);
			}	
			tablon.setAllMsg(msg);

			closeConnectionPool(conn);
				
		}catch(SQLException ex){
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}

		return tablon;

	}


	/*Obtiene los 'limit últimos mensajes del tablón' y los asocia al tablon*/

	public void getSomeMessagesFromTablon(Tablon tablon, int limit){

			Vector <Message> msg = new Vector<Message>();
		try{
			Connection conn = openConnectionPool();
			/*select * from TablonMessage INNER JOIN Message ON TablonMessage.message_id = Message.id INNER JOIN User ON Message.user_id = User.id WHERE TablonMessage.id = ?;*/
			/***********************************************PARAMETRIZACIÓN*************************************************/
			PreparedStatement statement = conn.prepareStatement("SELECT * FROM Message INNER JOIN User ON Message.user_id=User.id INNER JOIN Tablon ON Message.tablon_id = Tablon.id WHERE tablon_id = ? ORDER BY dateTime DESC limit ?;"); 
			System.out.println("el id del tablon :"+tablon.getId());
			statement.setInt(1, tablon.getId());
			statement.setInt(2, limit);
			/****************************************************************************************************************/

			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				Message m = new Message();
				User u = new User();
				m.setId(rs.getInt("id"));
				m.setFormat(Integer.parseInt(rs.getString("format")));
				System.out.println(rs.getString("Message.message"));
				m.setMsg(rs.getString("Message.message"));

				/********************************/
				try{
					String string = new String(rs.getString("Message.message").getBytes(), "UTF-8");
					System.out.println("EL STRING TIENE PROBLEMAS ? :  " + string);
				}
				catch(Exception ioe){
	
				}

				/**********************************/


				System.out.println("visibility:"+rs.getInt("visibility"));
				m.setVisibility(rs.getInt("visibility"));
				//m.setDate(rs.getTimestamp("dateTime"));//peta aquí
				u.setId(rs.getInt("Message.user_id"));
				u.setName(rs.getString("User.name"));
				u.setNick(rs.getString("User.nick"));
				System.out.println(rs.getString("User.name"));

				u.setSurName1(rs.getString("User.surname1"));
				u.setSurName2(rs.getString("User.surname2"));
				m.setCreator(u);
				msg.addElement(m);
				tablon.setName(rs.getString("Tablon.name"));


			}
			tablon.setAllMsg(msg);
			

			closeConnectionPool(conn);
					
			}catch(SQLException ex){
				System.out.println("SQLException: " + ex.getMessage());
				System.out.println("SQLState: " + ex.getSQLState());
				System.out.println("VendorError: " + ex.getErrorCode());
			}
				
	}
	

/*método que dado un id de usuario te devuelve el vector de enteros de tablones que modera ese usuario */
	 public Vector<Integer> getIdTablonModerateUsers(int userId){
		
		Vector<Integer> tablones = new Vector<Integer>();

		try{
			Connection conn = openConnectionPool();
			/***********************************************PARAMETRIZACIÓN*************************************************/
			PreparedStatement statement = conn.prepareStatement(" SELECT tablon_id FROM TablonUserModerates INNER JOIN User ON TablonUserModerates.user_id = User.id WHERE TablonUserModerates.user_id = ?;"); 
			statement.setInt(1, userId);
			/****************************************************************************************************************/

			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				Integer id = new Integer(rs.getInt("tablon_id"));
				tablones.add(id);
			}

			closeConnectionPool(conn);
					
			}catch(SQLException ex){
				System.out.println("SQLException: " + ex.getMessage());
				System.out.println("SQLState: " + ex.getSQLState());
				System.out.println("VendorError: " + ex.getErrorCode());
			}
		
		return tablones;
		
		
	}


	public Vector<User> getTablonModerateUsers(int idTablon){
		
		Vector<User> moderators = new Vector<User>();

		try{
			Connection conn = openConnectionPool();
			/***********************************************PARAMETRIZACIÓN*************************************************/
			PreparedStatement statement = conn.prepareStatement("SELECT User.id, User.nick, User.name, User.surname1,User.surname2 FROM TablonUserModerates INNER JOIN User ON TablonUserModerates.user_id = User.id WHERE TablonUserModerates.tablon_id = ?;"); 
			statement.setInt(1, idTablon);
			/****************************************************************************************************************/

			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				
				User u = new User();
				u.setId(rs.getInt("User.id"));
				u.setName(rs.getString("User.name"));
				u.setNick(rs.getString("User.nick"));
				u.setSurName1(rs.getString("User.surname1"));
				u.setSurName2(rs.getString("User.surname2"));
				moderators.addElement(u);
			}

			closeConnectionPool(conn);
					
			}catch(SQLException ex){
				System.out.println("SQLException: " + ex.getMessage());
				System.out.println("SQLState: " + ex.getSQLState());
				System.out.println("VendorError: " + ex.getErrorCode());
			}
		
		return moderators;
		
		
	}


	public Vector<User> getTablonTargetUsers(int idTablon){
		
		Vector<User> targetUsers = new Vector<User>();
		
		try{
			Connection conn = openConnectionPool();
			/***********************************************PARAMETRIZACIÓN*************************************************/
			//PreparedStatement statement = conn.prepareStatement("SELECT User.id, User.nick, User.name, User.surname1, User.surname2, UserPermission.permission  FROM TablonTargetUser INNER JOIN User ON TablonTargetUser.user_id = User.id INNER JOIN UserPermission ON UserPermission.user_id = TablonTargetUser.user_id  WHERE TablonTargetUser.tablon_id = ?"); 
			PreparedStatement statement = conn.prepareStatement("SELECT User.id, User.nick, User.name, User.surname1, User.surname2, User.work, User.age, User.genre FROM TablonTargetUser INNER JOIN User ON TablonTargetUser.user_id = User.id WHERE TablonTargetUser.tablon_id = ?"); 
			statement.setInt(1, idTablon);
			/****************************************************************************************************************/

			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				
				User u = new User();
				u.setId(rs.getInt("User.id"));
				u.setName(rs.getString("User.name"));
				u.setNick(rs.getString("User.nick"));
				u.setWork(rs.getString("User.work"));
				u.setAge(rs.getString("User.age"));
				u.setGenre(rs.getString("User.genre"));
				u.setSurName1(rs.getString("User.surname1"));
				u.setSurName2(rs.getString("User.surname2"));
				//u.setPermission(rs.getByte("UserPermission.permission"));

				targetUsers.addElement(u);

				System.out.println("u.getID!!"+u.getId());
			}

			closeConnectionPool(conn);
					
			}catch(SQLException ex){
				System.out.println("SQLException: " + ex.getMessage());
				System.out.println("SQLState: " + ex.getSQLState());
				System.out.println("VendorError: " + ex.getErrorCode());
			}
		
		return targetUsers;
		
	}
	

	public Vector<User> getAllDataBaseUsers(){
		
		Vector<User> users = new Vector<User>();
		
		try{
			Connection conn = openConnectionPool();
			/***********************************************PARAMETRIZACIÓN*************************************************/
			PreparedStatement statement = conn.prepareStatement("SELECT User.id, User.name, User.surname1, User.surname2, UserPermission.permission  FROM User INNER JOIN UserPermission ON UserPermission.user_id = User.id ORDER BY User.name ASC"); 
			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				
				User u = new User();
				u.setId(rs.getInt("User.id"));
				u.setName(rs.getString("User.name"));
				u.setNick(rs.getString("User.nick"));
				u.setSurName1(rs.getString("User.surname1"));
				u.setSurName2(rs.getString("User.surname2"));
				u.setPermission(rs.getByte("UserPermission.permission"));
				users.addElement(u);
			}

			closeConnectionPool(conn);
					
			}catch(SQLException ex){
				System.out.println("SQLException: " + ex.getMessage());
				System.out.println("SQLState: " + ex.getSQLState());
				System.out.println("VendorError: " + ex.getErrorCode());
			}
		
		return users;
		
	}
	
	/*
	
	public void printTablon(Tablon tablon){
		
		System.out.println("id: "+ tablon.getId());
		System.out.println("espacio: "+ tablon.getSpaceId());
		System.out.println("nombre: "+ tablon.getName());
		System.out.println("permiso: "+ tablon.getPermission());
		System.out.println("visibilidad: "+ tablon.getVisibility());
		System.out.println("Usuarios moderadores del tablon:");
		System.out.println("---------------------------");

		for(int i=0; i<tablon.getUsers().size(); i++){
			printUser(tablon.getUsers().elementAt(i));
		}
		System.out.println("Mensajes asociados al tablón");
		System.out.println("---------------------------");

		for(int i=0; i<tablon.getAllMsg().size(); i++){
			printMessage(tablon.getAllMsg().elementAt(i));
		}
		System.out.println("Usuarios asociados al tabĺon");
		System.out.println("---------------------------");

		for(int i=0; i<tablon.getTargetUsers().size(); i++){
			printUser(tablon.getTargetUsers().elementAt(i));
		}
	
	}
	*/
	
	
	public void printUser(User user){
		
		System.out.println("---------------------------");
		System.out.println("id: "+user.getId());
		System.out.println("nombre: "+user.getName());
		System.out.println("apellido1: "+user.getSurName1());
		System.out.println("apellido2: "+user.getSurName2());
		System.out.println("nick: "+user.getNick());
	}
	
	public void printMessage(Message message){
		
		System.out.println("id: "+message.getId());
		System.out.println("mensaje: "+message.getMsg());
		System.out.println("visibilidad: "+message.getVisibility());
		System.out.println("fecha: "+message.getDate());
		System.out.println("usuario creador: ");
		printUser(message.getCreator());
	}
	
	public boolean addUser(User user){
		boolean result= false;
		
		try {
			Connection conn = openConnectionPool();
			Statement stmt = conn.createStatement();
			
			PreparedStatement statement = conn.prepareStatement("INSERT INTO User (ldap_identifier) VALUES (?)");
				
			statement.setString(1, user.getLdapIdentifier());
			//statement.setString(2, user.getNick());
			//statement.setString(3, user.getSurName1());
			//statement.setString(4, user.getSurName2());
		
			int aux = statement.executeUpdate();
			if(aux!=0)
				result=true;
		
			closeConnectionPool(conn);
		}catch(SQLException ex){
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		
		return result;
	}
	
	public boolean deleteUser(int userId){
		boolean result= false;
		
		try {
			Connection conn = openConnectionPool();
			Statement stmt = conn.createStatement();
			
			PreparedStatement statement = conn.prepareStatement("DELETE FROM User WHERE id = ?");
			statement.setInt(1, userId);
			
		
			int aux = statement.executeUpdate();
			if(aux!=0)
				result=true;
		
			closeConnectionPool(conn);
		}catch(SQLException ex){
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		
		return result;
	}


	public boolean deleteTablon(int tablonId){
		boolean result= false;
		
		try {
			Connection conn = openConnectionPool();
			Statement stmt = conn.createStatement();
			
			PreparedStatement statement = conn.prepareStatement("DELETE FROM Tablon WHERE id = ?");
			statement.setInt(1, tablonId);
			
		
			int aux = statement.executeUpdate();
			if(aux!=0)
				result=true;
		
			closeConnectionPool(conn);
		}catch(SQLException ex){
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		
		return result;
	}
	
	public boolean setPermissionToUser(int userId, int permission, int tablonId){
		boolean result= false;
		
		try {
			Connection conn = openConnectionPool();
			Statement stmt = conn.createStatement();
			
			PreparedStatement statement = conn.prepareStatement("INSERT INTO UserPermission (user_id, tablon_id, permission) VALUES (?, ?, ?)");
				
			statement.setInt(1, userId);
			statement.setInt(2, tablonId);
			statement.setInt(3, permission);
		
			int aux = statement.executeUpdate();
			if(aux!=0)
				result=true;
		
			closeConnectionPool(conn);
		}catch(SQLException ex){
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		
		return result;
	}
	
	public int getUserPermission(int userId, int tablonId){
		
		int i=-1;
		
		try{
			Connection conn = openConnectionPool();
			/***********************************************PARAMETRIZACIÓN*************************************************/
			PreparedStatement statement = conn.prepareStatement("SELECT * FROM UserPermission WHERE user_id = ? AND tablon_id = ?"); 
			statement.setInt(1, userId);
			statement.setInt(2, tablonId);
			/****************************************************************************************************************/

			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				
				i = rs.getInt("permission");
					 
			}

			closeConnectionPool(conn);
					
			}catch(SQLException ex){
				System.out.println("SQLException: " + ex.getMessage());
				System.out.println("SQLState: " + ex.getSQLState());
				System.out.println("VendorError: " + ex.getErrorCode());
			}
		
		return i;
		
		
	}
	
	public int getMessageId (String msg){


		int id = 0;

		try{
			Connection conn = openConnectionPool();
			/***********************************************PARAMETRIZACIÓN*************************************************/
			PreparedStatement statement = conn.prepareStatement("SELECT * FROM Message WHERE  = ?;"); 
			statement.setString(1, msg);
			/****************************************************************************************************************/

			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				
				id = rs.getInt("id");

			}

			closeConnectionPool(conn);
					
			}catch(SQLException ex){
				System.out.println("SQLException: " + ex.getMessage());
				System.out.println("SQLState: " + ex.getSQLState());
				System.out.println("VendorError: " + ex.getErrorCode());
			}

		return id; 

	}


	public long createMessage(Message message, int tablonId){
		long result = 0;

		try {
			Connection conn = openConnectionPool();
			Statement stmt = conn.createStatement();
		    ResultSet generatedKeys = null;

			PreparedStatement statement = conn.prepareStatement("INSERT INTO " +
					"Message (user_id,tablon_id, message, format, visibility, dateTime) VALUES (?, ?, ?, ?, ?, NOW())",Statement.RETURN_GENERATED_KEYS);
				
			statement.setInt(1, message.getCreator().getId());
			statement.setInt(2, tablonId);
			statement.setString(3, message.getMsg());
			statement.setInt(4, message.getFormat());
			statement.setInt(5, message.getVisibility());

			
			int aux = statement.executeUpdate();
			
	        generatedKeys = statement.getGeneratedKeys();
	        if (generatedKeys.next()) {
	        	result = generatedKeys.getLong(1);
          		System.out.println("El id del msg insertado es : "+ result);
        	} 

			closeConnectionPool(conn);
		}catch(SQLException ex){
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		
		return result;
	}
	
	public boolean deleteMessage(String messageId){
		boolean result= false;
		
		try {
			Connection conn = openConnectionPool();
			Statement stmt = conn.createStatement();
			
			PreparedStatement statement = conn.prepareStatement("DELETE FROM Message WHERE id = ?");
			statement.setString(1,messageId);
			System.out.println("DELETE FROM Message WHERE id ="+messageId);
			
			int aux = statement.executeUpdate();
			if(aux!=0)
				result=true;
		
			closeConnectionPool(conn);
		}catch(SQLException ex){
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		
		return result;
	}
	
	
	public boolean setModeratorsToTablon(int tablonId, Vector<Integer> moderatorsId){
		boolean result= false;
		
		try {
			Connection conn = openConnectionPool();
			Statement stmt = conn.createStatement();
			
			PreparedStatement statement = conn.prepareStatement("INSERT INTO TablonUserModerates (tablon_id, user_id) VALUES (?, ?)");
			
			for(int i=0; i<moderatorsId.size();i++){
				statement.setInt(1, tablonId);
				statement.setInt(2, moderatorsId.elementAt(i).intValue());
			
				int aux = statement.executeUpdate();
				if(aux!=0)
					result=true;
		
				}
			
			closeConnectionPool(conn);
		}catch(SQLException ex){
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		
		return result;
	}
	
	public boolean setTargetUsersToTablon(int tablonId, Vector<Integer> targetUsersId){
		boolean result= false;
		
		try {
			Connection conn = openConnectionPool();
			Statement stmt = conn.createStatement();
			
			PreparedStatement statement = conn.prepareStatement("INSERT INTO TablonTargetUser (tablon_id, user_id) VALUES (?, ?)");
			
			for(int i=0; i<targetUsersId.size();i++){
				statement.setInt(1, tablonId);
				statement.setInt(2, targetUsersId.elementAt(i).intValue());
			
				int aux = statement.executeUpdate();
				if(aux!=0)
					result=true;
		
				}
			
			closeConnectionPool(conn);
		}catch(SQLException ex){
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		
		return result;
	}



	public String sanitizer(String cadena){
	
		return cadena.replaceAll( "<[^>]*>|[<>\'\"]"," ");


	}


/*login mobile uses it, returns the user object if exists, or null if not*/

/*fix when you can, see checkUser -> do the same.*/

	public User existsSameNick(String nick){
			User result = null;
			User databaseUser=new User();
		try{
			Connection conn = openConnectionPool();
			/***********************************************PARAMETRIZACIÓN*************************************************/
			PreparedStatement statement = conn.prepareStatement("SELECT * FROM User WHERE nick=?"); //AND surname1 = ? AND surname2 = ?");
			statement.setString(1, nick);
			/****************************************************************************************************************/

			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				databaseUser = new User();
				databaseUser.setId(rs.getInt("id"));
				databaseUser.setName(rs.getString("name"));
				databaseUser.setNick(rs.getString("nick"));
				databaseUser.setSurName1(rs.getString("surname1"));
				databaseUser.setSurName2(rs.getString("surname2"));
				databaseUser.setAge(rs.getString("age"));
				databaseUser.setWork(rs.getString("work"));
				databaseUser.setGenre(rs.getString("genre"));
				databaseUser.setLdapIdentifier(rs.getString("ldap_identifier"));
				result = databaseUser;
			}

			System.out.println("Lo que hay en la base de datos: ");
			System.out.println("name:" + databaseUser.getName());
			closeConnectionPool(conn);
		}catch(SQLException ex){
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		
		return result;
	}


	public User existsSameLdapIdentifier(String ldapIdentifier){
			User result = null;
			User databaseUser=new User();
		try{
			Connection conn = openConnectionPool();
			/***********************************************PARAMETRIZACIÓN*************************************************/
			PreparedStatement statement = conn.prepareStatement("SELECT * FROM User WHERE ldap_identifier=?"); //AND surname1 = ? AND surname2 = ?");
			statement.setString(1, ldapIdentifier);
			/****************************************************************************************************************/

			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				databaseUser = new User();
				databaseUser.setId(rs.getInt("id"));
				databaseUser.setName(rs.getString("name"));
				databaseUser.setNick(rs.getString("nick"));
				databaseUser.setSurName1(rs.getString("surname1"));
				databaseUser.setSurName2(rs.getString("surname2"));
				databaseUser.setAge(rs.getString("age"));
				databaseUser.setWork(rs.getString("work"));
				databaseUser.setGenre(rs.getString("genre"));
				databaseUser.setLdapIdentifier(rs.getString("ldap_identifier"));
				result = databaseUser;
			}

			System.out.println("Lo que hay en la base de datos: ");
			System.out.println("name:" + databaseUser.getName());
			closeConnectionPool(conn);
		}catch(SQLException ex){
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		
		return result;
	}

		/*mobile register, without name, and surnames*/
		public int saveRegister(User user){

			int result = 0;

			try {
				Connection conn = openConnectionPool();
				Statement stmt = conn.createStatement();
				ResultSet generatedKeys = null;

				PreparedStatement statement = conn.prepareStatement("INSERT INTO User (	genre, age, work, nick) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
				
				statement.setString(1, user.getGenre());
				statement.setString(2, user.getAge());
				statement.setString(3, user.getWork());
				statement.setString(4, user.getNick());
		
				int aux = statement.executeUpdate();
		
				generatedKeys = statement.getGeneratedKeys();
			        if (generatedKeys.next()) {
			        	result = generatedKeys.getInt(1);
		          		System.out.println("El id del usuario insertado es : "+ result);
		        	} 
	
				closeConnectionPool(conn);
	
			}catch(SQLException ex){
				System.out.println("SQLException: " + ex.getMessage());
				System.out.println("SQLState: " + ex.getSQLState());
				System.out.println("VendorError: " + ex.getErrorCode());
			}
	
			return result;
		}

	public void setRateDDBB(Rate rate){

		Connection conn = openConnectionPool();
		try {	
			Statement stmt = conn.createStatement();
			PreparedStatement statement = conn.prepareStatement("INSERT INTO Rate (user_id,tablon_id,rate) VALUES (?,?,?)");
		
			statement.setInt(1, rate.getUserId());
			statement.setInt(2, rate.getTablonId());
			statement.setFloat(3, rate.getRate());

			int aux = statement.executeUpdate();

			closeConnectionPool(conn);

		}catch(SQLException ex){
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}		
	}



	/*método media y auxiliares debajo*/

	public float setMediaToTablon(Tablon tablon, float newRate){
	

		float oldRate=0;
		//leo rate
		oldRate = Float.parseFloat(readRateDDBB(tablon));
		System.out.println("oldRate:" +oldRate);
		//calculo nueva rate
		newRate = (newRate + oldRate) / 2;
		System.out.println("La newRate es: " + newRate);
		//actualizo nueva rate.
		updateRate(tablon, newRate);

		return newRate;
	}


	public void updateRate(Tablon tablon, float newRate){

		try {
			Connection conn = openConnectionPool();
			Statement stmt = conn.createStatement();
			
			System.out.println("tablon id : "+tablon.getId() + "rate: "+newRate);

			PreparedStatement statement = conn.prepareStatement("UPDATE Tablon SET rate = ? where id = ?");
				
			statement.setString(1, newRate+"");
			statement.setInt(2, tablon.getId());
			int aux = statement.executeUpdate();
		
			closeConnectionPool(conn);

		}catch(SQLException ex){
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
	}


	public String readRateDDBB(Tablon tablon){

		String oldRate = "";

		try{
			Connection conn = openConnectionPool();
		

			PreparedStatement statement = conn.prepareStatement("SELECT rate FROM Tablon WHERE id  = ?;");
			statement.setInt(1, tablon.getId());


			ResultSet rs = statement.executeQuery();
			if (rs.next()) {

				oldRate = rs.getString("rate");
				System.out.println("readRateDDBB: oldRate: "+oldRate);				
				
			}

			closeConnectionPool(conn);
		}catch(SQLException ex){
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		
		
		return oldRate;			
	}

}
