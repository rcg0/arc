package arc;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import java.util.Vector;

public class DeleteMessage extends HttpServlet {
  /**
	 * 
	 */
public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

      

	HttpSession session = request.getSession(false);
	
	Tablon tablon= new Tablon();

	if(session!=null){
      
		User user= (User)session.getAttribute("user");
	
		

		if(user!=null){
			System.out.println("el parametro: "+request.getParameter("messageId"));
			tablon.deleteMessageFromTablon(request.getParameter("messageId"));
		}
	}
	
  }

  public void doPost(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
  
    doGet(request,response);

    }
}
