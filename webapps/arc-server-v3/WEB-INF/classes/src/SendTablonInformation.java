package arc;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import java.lang.String.*;
import java.util.Vector;

public class SendTablonInformation extends HttpServlet {
  /**
	 * 
	 */
public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
	
	String nextServlet = "/profile"; //Servlet destino

	HttpSession session = request.getSession(false);


	System.out.println("Hemos enviado algo? "+ request.getParameter("tablon_id"));
	

	

	
	RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextServlet);
	dispatcher.forward(request,response);

  }

  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
  
    doGet(request,response);

    }
}
