package arc;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import java.util.Vector;

public class LogoutMobile extends HttpServlet {
  /**
	 * 
	 */
public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

	PrintWriter out = response.getWriter();      
	HttpSession session = request.getSession(false);

		if(session!=null){
			User user= (User)session.getAttribute("user");
			session.removeAttribute("user");
			session.invalidate();
			out.println("ok");
		}
  }

  public void doPost(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
  
    doGet(request,response);

    }
}
