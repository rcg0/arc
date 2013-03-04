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


public class SendImage extends HttpServlet {
  /**
	 * 
	 */
public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

	String nextServlet = "/getAfterMessagesMobile"; //Servlet destino

	HttpSession session = request.getSession(false);
	User sessionUser = (User)session.getAttribute("user");

	System.out.println("el id de sesion es: " + session.getId());

	Multimedia multimedia = new Multimedia();
	
	Tablon tablon;
	Message message;
	/*String message = URLDecoder.decode(request.getParameter("message"),"UTF-8");
	String tablonId=request.getParameter("tablonId").trim();
	String format = request.getParameter("format");
	String lastMessageId = request.getParameter("messageId");//lastMessageId
	*/
	tablon = multimedia.receiveMultimedia(request);
	message = tablon.getAllMsg().elementAt(0);
	message.setCreator(sessionUser);
	long writeMessageId = tablon.sendMessage(message);

	System.out.println("asdfasdfasdf");
	RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextServlet+"?messageId="+tablon.getLastMessageId()+"&tablonId="+tablon.getId());
	dispatcher.forward(request,response);

}  

  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
  
    doGet(request,response);

    }
}
