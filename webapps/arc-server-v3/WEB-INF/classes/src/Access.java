
package arc;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import java.lang.String.*;
import java.util.Vector;

import com.unboundid.ldap.sdk.LDAPConnection;
import com.unboundid.ldap.sdk.LDAPConnectionOptions;
import com.unboundid.ldap.sdk.LDAPException;
import com.unboundid.ldap.sdk.BindRequest;
import com.unboundid.ldap.sdk.BindRequest;
import com.unboundid.ldap.sdk.BindResult;
import com.unboundid.ldap.sdk.SimpleBindRequest;
import com.unboundid.ldap.sdk.ResultCode;
import com.unboundid.ldap.sdk.SearchResult;
import com.unboundid.ldap.sdk.SearchScope;
import com.unboundid.ldap.sdk.Control;


public class Access extends HttpServlet {
  /**
	 * 
	 */
public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
	
	String nextServlet = "/profile"; //Servlet destino

	ConfigurationParser conf = new ConfigurationParser();

	HttpSession session = request.getSession(false);
	User user = new User();
	
	String nameAndMore = new String(request.getParameter("name"));
	String [ ] names= nameAndMore.split("\\ ");

	String password = request.getParameter("password");

	user.setName(names[0]);
	user.setSurName1(names[1]);
	user.setSurName2(names[2]);

	User dataBaseUser = user.checkUser(user);

	if(dataBaseUser.getName().compareTo(user.getName())==0 && dataBaseUser.getSurName1().compareTo(user.getSurName1())==0 && dataBaseUser.getSurName2().compareTo(user.getSurName2())==0){

		session=request.getSession();
		session.setAttribute("user",dataBaseUser);
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextServlet);
		dispatcher.forward(request,response);
	


	}
	
	/*
	String path = getServletContext().getRealPath("/WEB-INF");
	System.out.println("El fichero de configuracion está: "+ path);

	if(conf.requiresLDAP(path+"/configuration.xml")){
	
		try{
			if(authenticate(nameAndMore,password)){
			System.out.println("Autenticado");
			user.setLdapIdentifier(nameAndMore);
			if(user.existsSameLdapIdentifier() == null){
				user.addUser();
			}
			session=request.getSession();
			session.setAttribute("user",user);


			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextServlet);
			dispatcher.forward(request,response);
			}
		}catch(Exception e ){
			System.out.println("No Autenticado");
			System.out.println(e);
		}
	}
	*/
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
  
    doGet(request,response);

    }

    public static boolean authenticate(String username, String password) throws LDAPException {
    // exception handling not shown
	boolean result = false;	
   	LDAPConnection ldapConnection = null;
   	String hostname = "ldap.uc3m.es";
   	//String dn = "uid=100081398,ou=TELEMATICA,ou=Personal,ou=Gente,o=Universidad Carlos III,c=es";
   	String dn = "uid=100081398,ou=Gente,o=Universidad Carlos III,c=es";

   	try {
            ldapConnection = new LDAPConnection(hostname,389);
        } catch(final LDAPException lex) {
            System.err.println("failed to connect to "+ hostname + " " +lex.getMessage());
            return result;
        }

  	try {
            final BindRequest bindRequest = new SimpleBindRequest(dn,password);
            BindResult bindResult = ldapConnection.bind(bindRequest);
            if(bindResult.getResultCode() == ResultCode.SUCCESS) {
                System.out.println("authentication successful");
                result = true;
            }
            if(bindResult.hasResponseControl()) {
                Control[] controls = bindResult.getResponseControls();
                // handle response controls ...
            }
            ldapConnection.close();
        } catch(final LDAPException lex ) {
            System.err.println(lex);
            ldapConnection.close();
            return result;
        }
        return result;
/*

    	LDAPConnection ldap = new LDAPConnection("ldap.uc3m.es", 389);
    	SearchResult sr = ldap.search("ou=Gente,o=Universidad Carlos III,c=es", SearchScope.SUB, "(uid=" + username + ")");
    	if (sr.getEntryCount() == 0){
	     	System.out.println("getEntryCount == 0");
	        return false;
	    }

    	String dn = sr.getSearchEntries().get(0).getDN();

    	try {
	        ldap = new LDAPConnection("ldap.uc3m.es", 389, dn, password);
        	return true;
    	}
    	catch (LDAPException e) {
        	if (e.getResultCode() == ResultCode.INVALID_CREDENTIALS){
	            System.out.println("INVALID CREDENTIALS");
	            return false;
	           }
        	throw e;
    	}*/


	}

}

