package arc;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import java.util.Vector;

public class deleteMessage extends HttpServlet {
  /**
	 * 
	 */
public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

      

	HttpSession session = request.getSession(false);
	String next = "/error.html";

	if(session!=null){
      
		User user= (User)session.getAttribute("user");

		if(user!=null){
			dataBaseManager manager = new dataBaseManager();
			manager.deleteMessage(Integer.parseInt(request.getParameter("messageId")));
			
			next = "/getTablones?tablonId="+request.getParameter("tablonId"); //destino
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
