package arc;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import java.lang.String.*;
import java.util.Vector;

public class Access extends HttpServlet {
  /**
	 * 
	 */
public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
	
	HttpSession session = request.getSession(false);
	User user = new User();
	
	String nameAndMore = new String(request.getParameter("name"));
	String [ ] names= nameAndMore.split("\\ ");


	user.setName(names[0]);
	user.setSurName1(names[1]);
	user.setSurName2(names[2]);

	DataBaseManager manager = new DataBaseManager();
	User dataBaseUser = manager.checkUser(user);

	if(dataBaseUser.getName().compareTo(user.getName())==0 && dataBaseUser.getSurName1().compareTo(user.getSurName1())==0 && dataBaseUser.getSurName2().compareTo(user.getSurName2())==0){

		session=request.getSession();
		session.setAttribute("user",dataBaseUser);
	}

	
	String nextServlet = "/profile"; //Servlet destino
	RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextServlet);
	dispatcher.forward(request,response);

  }

  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
  
    doGet(request,response);

    }
}
