package arc;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import java.util.Vector;

public class DeleteTablon extends HttpServlet {
  /**
	 * 
	 */
public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

      

	HttpSession session = request.getSession(false);
	String next = "/error.html";

	if(session!=null){
      
		User user= (User)session.getAttribute("user");
		Vector<Tablon> tablones = (Vector<Tablon>)session.getAttribute("tablones");
		int tablon = Integer.parseInt(request.getParameter("tablonId"));//identificador en el jsp, no equivale a id de bbdd, 0,1...    

		if(user!=null){
		
			tablones.elementAt(tablon).deleteTablon();//lo elimino en la base de datos
			tablones.remove(tablon);
			//request.setAttribute("tablon",-1);
			next = "/mis-tablones.jsp"; //destino
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
