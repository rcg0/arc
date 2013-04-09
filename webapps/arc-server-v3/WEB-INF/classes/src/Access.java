package arc;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import java.lang.String.*;
import java.util.Vector;

import com.unboundid.ldap.sdk.LDAPConnection;
import com.unboundid.ldap.sdk.LDAPConnectionOptions;
import com.unboundid.ldap.sdk.LDAPException;
import com.unboundid.ldap.sdk.ResultCode;
import com.unboundid.ldap.sdk.SearchResult;
import com.unboundid.ldap.sdk.SearchScope;

public class Access extends HttpServlet {
  /**
	 * 
	 */
public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
	
	String nextServlet = "/profile"; //Servlet destino

	HttpSession session = request.getSession(false);
	User user = new User();
	
	String nameAndMore = new String(request.getParameter("name"));
	String [ ] names= nameAndMore.split("\\ ");


	/*user.setName(names[0]);
	user.setSurName1(names[1]);
	user.setSurName2(names[2]);

	User dataBaseUser = user.checkUser(user);

	if(dataBaseUser.getName().compareTo(user.getName())==0 && dataBaseUser.getSurName1().compareTo(user.getSurName1())==0 && dataBaseUser.getSurName2().compareTo(user.getSurName2())==0){

		session=request.getSession();
		session.setAttribute("user",dataBaseUser);

	}
	*/
	String password = request.getParameter("password");
	
	try{
		authenticate(nameAndMore,password);
		System.out.println("Autenticado");
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextServlet);
		dispatcher.forward(request,response);
	}
	catch(Exception e ){
		System.out.println("No Autenticado");
		System.out.println(e);
	}
	
	
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
  
    doGet(request,response);

    }

    public static boolean authenticate(String username, String password) throws LDAPException {
    
    	LDAPConnection ldap = new LDAPConnection("ldap.uc3m.es", 389);
    	SearchResult sr = ldap.search("ou=Gente,o=Universidad Carlos III,c=es", SearchScope.SUB, "(uid=" + username + ")");
    	if (sr.getEntryCount() == 0)
	        return false;

    	String dn = sr.getSearchEntries().get(0).getDN();

    	try {
	        ldap = new LDAPConnection("ldap.uc3m.es", 389, dn, password);
        	return true;
    	}
    	catch (LDAPException e) {
        	if (e.getResultCode() == ResultCode.INVALID_CREDENTIALS)
	            return false;
        	throw e;
    	}
	}
}
