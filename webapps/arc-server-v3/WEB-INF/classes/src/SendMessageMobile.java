package arc;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import java.util.Vector;

public class SendMessageMobile extends HttpServlet {
  /**
	 * 
	 */
public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

      
	PrintWriter out = response.getWriter();

	HttpSession session = request.getSession(false);
	String next = "/error.html";
	User user = new User();

	System.out.println("el id de sesion es: " + session.getId());
	user= (User)session.getAttribute("user");


	//Vector<Tablon> tablones = (Vector<Tablon>)session.getAttribute("tablones");
	Message msg = new Message();
	Tablon tablon = new Tablon();
	//int messageId=0;
			

	/*Mensaje */
	String message = request.getParameter("message");
	String tablonId=request.getParameter("tablonId").trim();

	System.out.println("El mensaje que se envía: Mensaje: "+ message );
	/**************************************************/
	
	
	msg.setMsg(message);
	msg.setCreator((User)session.getAttribute("user"));
	tablon.setId(Integer.parseInt(tablonId));
	System.out.println("El tablon es: "+ tablonId);

	//envía el mensaje a la base de datos
	long writeMessageId = tablon.sendMessage(msg);
	//msg.setVisibility();
	
	System.out.println("El mensaje que he enviado ha sido el siguiente: "+ msg.getMsg());
	//tablones.elementAt(tablonId).createMessage(msg, Integer.parseInt(tablonId));
	out.println(writeMessageId+"");
	
  }

  public void doPost(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
  
    doGet(request,response);

    }
}
