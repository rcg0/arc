package arc;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import java.util.Vector;

public class ShowModifyTablones extends HttpServlet {
  /**
	 * 
	 */
public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

      

	HttpSession session = request.getSession(false);
	String next = "/error.html";
	User user;
	//Vector<Tablon> tablones;
	Vector<User> users;
	Tablon tablon;

	if(session!=null){
      
		user= (User)session.getAttribute("user");

		if(user!=null){
      		
			
			String attribute = request.getParameter("tablonId");
			if(attribute != null){
				tablon=new Tablon();
				tablon = tablon.getSoftTablonInformation(Integer.parseInt(attribute));
			
				request.setAttribute("tablon", tablon);
			}

			users = user.getAllUsers();
			request.setAttribute("allUsers",users);
  
			next = "/mis-tablones.jsp"; //JSP destino

		  }
	}
	RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(next);
	dispatcher.forward(request,response);

  }

  public void doPost(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
  
    doGet(request,response);

    }
}
