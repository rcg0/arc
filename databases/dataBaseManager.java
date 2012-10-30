import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import javax.sql.*;

import java.sql.*;
import javax.sql.*;
import java.util.Vector;

public class dataBaseManager{

	String error ="";
	private Connection openConnectionPool(){
		Connection conn=null;		
		try{	    	
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			DataSource ds = (DataSource) envCtx.lookup("jdbc/VideoClub");
			conn = ds.getConnection();
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
		Usuario databaseUser=null;
		try{
			Connection conn = openConnectionPool();
			/***********************************************PARAMETRIZACIÓN*************************************************/
			PreparedStatement statement = conn.prepareStatement("SELECT * FROM User WHERE name=? AND surname1 = ? AND surname2 = ?");
			statement.setString(1, user.getName());
			statement.setString(2, user.getSurname1());
			statement.setString(3, user.getSurname2());
			/****************************************************************************************************************/

			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				databaseUser = new Usuario();
				databaseUser.setId(rs.getString("id"));
				databaseUser.setName(rs.getString("nombre"));
				databaseUser.setSurname1(rs.getString("apellido1"));
				databaseUser.setSurname2(rs.getString("apellido2"));
				//faltan
			} 
			closeConnectionPool(conn);
		}catch(SQLException ex){
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		return databaseUser;
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




{