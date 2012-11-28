package arc;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import java.util.Vector;

public class GetTablon extends HttpServlet {

	 
public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

	HttpSession session = request.getSession(false);
	User user = null;
	String next="/error.html";
	Tablon tablon = null;

	if(session != null){
		user= (User)session.getAttribute("user");

		if(user!=null){
      			
			tablon = new Tablon();
			
			tablon.getSoftTablonInformation(Integer.parseInt(request.getParameter("tablonId")));//requiere un id, se le pasará con la petición
		}

	}
	RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(next);
	dispatcher.forward(request,response);

  }

  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
  
    doGet(request,response);

    }
}
