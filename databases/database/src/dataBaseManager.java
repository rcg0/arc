


import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import javax.sql.*;

import java.sql.*;
import javax.sql.*;
import java.util.Vector;
import java.lang.String;



public class dataBaseManager{

	String error ="";
	public Connection openConnectionPool(){
		Connection conn=null;		
		try{	    	
			Context initCtx = new InitialContext();
			//Context envCtx = (Context) initCtx.lookup("java:comp/env");
			//DataSource ds = (DataSource) envCtx.lookup("jdbc/VideoClub");
			//conn = ds.getConnection();
			
			String url= "jdbc:mysql://127.0.0.1:3306/BaseDeDatos";
			conn = DriverManager.getConnection(url,"jrafael", "hmspawnn");
			
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


	public Boolean checkUser(User user) {
		User databaseUser=new User();
		Boolean result = false;
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
				databaseUser.setSurName1(rs.getString("surname1"));
				databaseUser.setSurName2(rs.getString("surname2"));

			}

			System.out.println("Lo que hay en la base de datos: ");
			System.out.println("name:" + databaseUser.getName());
			closeConnectionPool(conn);
		}catch(SQLException ex){
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		
		if(user.getName().compareTo(databaseUser.getName()) == 0 && 
		user.getSurName1().compareTo(databaseUser.getSurName1()) ==0){
		
			result= true;
		}
		
		return result;
	}
	
	/*Obtiene el tablón según su descriptor, muestra los mensajes asociados*/
	
	public Tablon getTablon(String descriptor){
		
		Tablon tablon = null;
		
		try{
			Connection conn = openConnectionPool();
			/***********************************************PARAMETRIZACIÓN*************************************************/
			PreparedStatement statement = conn.prepareStatement("SELECT * FROM Tablon WHERE space=?"); //AND surname1 = ? AND surname2 = ?");
			statement.setString(1, descriptor);
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
				tablon.setAllMsg(getMessagesFromTablon(idTablon));
				tablon.setAllTargetUser(getTablonTargetUsers(idTablon));
				tablon.setAllUsers(getTablonModerateUsers(idTablon));
			}

			
		}catch(SQLException ex){
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		
		
		
		return tablon;
		
	}

	public Vector<Message> getMessagesFromTablon(int idTablon){
		
		Vector<Message> msg = new Vector<Message>();
		
		try{
			Connection conn = openConnectionPool();
			/*select * from TablonMessage INNER JOIN Message ON TablonMessage.message_id = Message.id INNER JOIN User ON Message.user_id = User.id WHERE TablonMessage.id = ?;*/
			/***********************************************PARAMETRIZACIÓN*************************************************/
			PreparedStatement statement = conn.prepareStatement("SELECT * FROM TablonMessage INNER JOIN Message ON TablonMessage.message_id = Message.id INNER JOIN User ON Message.user_id = User.id WHERE TablonMessage.id = ?;"); 
			statement.setInt(1, idTablon);
			/****************************************************************************************************************/

			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				Message m = new Message();
				User u = new User();
				m.setId(rs.getInt("message_id"));
				m.setMsg(rs.getString("message"));
				m.setVisibility(rs.getInt("visibility"));
				//m.setDate(rs.getDate("dateTime"));//peta aquí
				u.setId(rs.getInt("Message.user_id"));
				u.setName(rs.getString("User.name"));
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
	
	public Vector<User> getTablonModerateUsers(int idTablon){
		
		Vector<User> moderators = new Vector<User>();

		try{
			Connection conn = openConnectionPool();
			/***********************************************PARAMETRIZACIÓN*************************************************/
			PreparedStatement statement = conn.prepareStatement("SELECT * FROM TablonUserModerates INNER JOIN User ON TablonUserModerates.user_id = User.id WHERE TablonUserModerates.tablon_id = ?;"); 
			statement.setInt(1, idTablon);
			/****************************************************************************************************************/

			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				
				User u = new User();
				u.setId(rs.getInt("User.id"));
				u.setName(rs.getString("User.name"));
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
			PreparedStatement statement = conn.prepareStatement("SELECT * FROM TablonTargetUser INNER JOIN User ON TablonTargetUser.user_id = User.id WHERE TablonTargetUser.tablon_id = ?;"); 
			statement.setInt(1, idTablon);
			/****************************************************************************************************************/

			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				
				User u = new User();
				u.setId(rs.getInt("User.id"));
				u.setName(rs.getString("User.name"));
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
	
	public void printUser(User user){
		
		System.out.println("---------------------------");
		System.out.println("id: "+user.getId());
		System.out.println("nombre: "+user.getName());
		System.out.println("apellido1: "+user.getSurName1());
		System.out.println("apellido2: "+user.getSurName2());
	}
	
	public void printMessage(Message message){
		
		System.out.println("id: "+message.getId());
		System.out.println("mensaje: "+message.getMsg());
		System.out.println("visibilidad: "+message.getVisibility());
		System.out.println("fecha: "+message.getDate());
		System.out.println("usuario creador: ");
		printUser(message.getCreator());
	}
	
	/*EJEMPLO DE ANTERIOR PROYECTO*/

    	/**
	 * Mete comentarios a la bbdd 
	 * @param objeto comentario
	 * @return nada
	 */
	public boolean meteComentario(String peli, String usu, String com){//Comentario comentario) {
		boolean result=false;
		try{
			Connection conn = openConnectionPool();
			Statement stmt = conn.createStatement();
			String query= "INSERT INTO Comentarios (pelicula, usuario, comentario) VALUES ('"+peli+"', '"+usu+"', '"+com+"');";			
//String query= "INSERT INTO Comentarios (pelicula, usuario, comentario) VALUES ('"+comentario.getPelicula()+"', '"+comentario.getUsuario()+"', '"+comentario.getComentario()+"');";

/***********************************************PARAMETRIZACIÓN*************************************************/
			PreparedStatement statement = conn.prepareStatement("INSERT INTO Comentarios (pelicula, usuario, comentario) VALUES (?, ?, ?)");
			statement.setString(1, peli);
			statement.setString(2, usu);
			statement.setString(3, com);
/****************************************************************************************************************/
			int aux = stmt.executeUpdate(query);
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



}