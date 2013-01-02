package arc;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import java.lang.String.*;

public class SendRateMobile extends HttpServlet {
  /**
	 * 
	 */
public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
	
	PrintWriter out = response.getWriter();
	HttpSession session = request.getSession(false);

	Tablon tablon = new Tablon();
	tablon.setId(Integer.parseInt(request.getParameter("tablon_id")));
	tablon.setRate(request.getParameter("rate"));
	tablon.setRateDDBB();
	
	System.out.println(request.getParameter("rate"));
	System.out.println(request.getParameter("tablon_id"));

	out.println("ok");
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
  
    doGet(request,response);

    }
}
