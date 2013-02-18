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


	User user = (User)session.getAttribute("user");
	if(user == null){
		System.out.println("el user es null");
	}
		System.out.println("checkpoint 0.5");

	if(user != null){

		float newRate = Float.parseFloat(request.getParameter("rate"));
		int tablonId = Integer.parseInt(request.getParameter("tablonId"));

		Tablon tablon = new Tablon();
		tablon.setId(tablonId);

		Rate rate = new Rate();
		rate.setUserId(user.getId());
		rate.setTablonId(tablonId);
		rate.setRate(newRate);
		
		rate.setRateDDBB();

		System.out.println("checkpoint 1");

		float media = tablon.setMediaToTablon(newRate);
	
		System.out.println("checkpoint 1.5");

		System.out.println("Rate enviada " + newRate);
		System.out.println("Al tablon "+ tablonId);


		System.out.println("SendRateMobile imprime: "+media);
		out.println(media);
	}



  }

  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
  
    doGet(request,response);

    }
}
