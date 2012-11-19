package arc;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import java.util.Vector;

public class ShowNewTablon extends HttpServlet {
  /**
	 * 
	 */
public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

      	HttpSession session = request.getSession(false);

	String next = "/error.html";

	if(session!=null){
      		User user= (User)session.getAttribute("user");
	
		if(user != null){
			next = "/nuevo-tablon.jsp"; //JSP destino
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
