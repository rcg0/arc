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
	
	System.out.println("el id de sesion es: "+session.getId());

	System.out.println("Checkpoint 0");

	User user = (User)session.getAttribute("user");
	if(user == null){
		System.out.println("el user es null");
	}
		System.out.println("checkpoint 0.5");

	if(user != null){

		float newRate = Float.parseFloat(request.getParameter("rate"));
		String spaceId = request.getParameter("spaceId");

		System.out.println("checkpoint 1");
		Tablon tablon = new Tablon();
		tablon.setSpaceId(spaceId);
		Rate rate = new Rate();
		rate.setUserId(user.getId());
		rate.setSpaceId(spaceId);
		rate.setRate(newRate);
		System.out.println("checkpoint 2");
		
		rate.setRateDDBB();
		System.out.println("checkpoint 3");

		tablon.setMediaToTablon(newRate);
	
		System.out.println("Rate enviada " + newRate);
		System.out.println("Al tablon "+ spaceId);

		out.println("ok");
	}



  }

  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
  
    doGet(request,response);

    }
}
