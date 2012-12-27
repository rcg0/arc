package arc;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import java.util.Vector;

public class SendMessage extends HttpServlet {
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
			//Vector<Tablon> tablones = (Vector<Tablon>)session.getAttribute("tablones");
			Message msg = new Message();
			Tablon tablon = new Tablon();
			//int messageId=0;
			

			/*Mensaje */
			String message = request.getParameter("mensaje");
			String format = request.getParameter("format");
			String visibility = request.getParameter("visibility");
			System.out.println("El mensaje que se envía: Mensaje: "+ message + "visibilidad: "+visibility+ "formato: "+format);
			/**************************************************/
			String tablonId=request.getParameter("tablonId").trim();
			System.out.println("El parámetro oculto es: "+ tablonId);
			
			msg.setMsg(message);
			msg.setCreator(user);
			msg.setVisibility(Integer.parseInt(visibility));
			msg.setFormat(Integer.parseInt(format));

			tablon.setId(Integer.parseInt(tablonId));

			//envía el mensaje a la base de datos
			tablon.sendMessage(msg);

			//msg.setVisibility();
			System.out.println("El mensaje que he enviado ha sido el siguiente: "+ msg.getMsg());

			//tablones.elementAt(tablonId).createMessage(msg, Integer.parseInt(tablonId));
			
			next="/getTablones?tablonId="+tablonId;//he tenido que hacer el trim porque sino me saltaba una excepción al pasarlo a entero luego.
			System.out.println("La url a la que voy es : "+next );
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
