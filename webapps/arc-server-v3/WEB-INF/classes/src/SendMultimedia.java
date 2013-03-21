package arc;

import javax.servlet.*;
import javax.servlet.http.*;
import java.net.URLDecoder;
import java.util.Vector;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Random;
import java.util.List;
 import java.io.*;


public class SendMultimedia extends HttpServlet {
  /**
	 * 
	 */
public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

	String nextServlet = "/getTablones"; //Servlet destino

	HttpSession session = request.getSession(false);
	User sessionUser = (User)session.getAttribute("user");

	System.out.println("el id de sesion es: " + session.getId());
	
	Multimedia multimedia = new Multimedia();
	
	Tablon tablon;
	Message message;

	tablon = multimedia.receiveMultimedia(request);

	message = tablon.getAllMsg().elementAt(0);
	message.setCreator(sessionUser);
	long writeMessageId = tablon.sendMessage(message);

	RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextServlet+"?tablonId="+tablon.getId());
	dispatcher.forward(request,response);

}  

  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
  
    doGet(request,response);

    }
}
