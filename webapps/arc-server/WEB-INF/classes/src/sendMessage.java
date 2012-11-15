package arc;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import java.util.Vector;

public class sendMessage extends HttpServlet {
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
			Message msg = new Message();
			dataBaseManager manager = new dataBaseManager();
			int messageId=0;
			String tablonId=request.getParameter("tablonId");
			
			msg.setCreator(user);
			msg.setMsg(request.getParameter("mensaje"));
			//msg.setFormat();
			//msg.setVisibility();
			manager.createMessage(msg);
			messageId = manager.getMessageId(msg.getMsg());
			//manager.setMessageToTablon(messageId,tablonId);
			next = "/getTablones?tablonId="+tablonId; //destino
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
