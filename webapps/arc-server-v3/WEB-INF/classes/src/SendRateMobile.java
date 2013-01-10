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
System.out.println("checkpoint 1");
	Tablon tablon = new Tablon();
	tablon.setId(Integer.parseInt(request.getParameter("tablon_id")));
	tablon.setRate(request.getParameter("rate"));
	System.out.println("checkpoint 2");
	tablon.setRateDDBB();
	
	System.out.println("Rate enviada " + request.getParameter("rate"));
	System.out.println("Al tablon "+request.getParameter("tablon_id"));

	out.println("ok");
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
  
    doGet(request,response);

    }
}
