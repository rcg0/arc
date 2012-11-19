package arc;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import java.util.Vector;

public class GetTablones extends HttpServlet {

	 
public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

	HttpSession session = request.getSession(false);
	User user;
	String next="/error.html";
	DataBaseManager manager = new DataBaseManager();
	Vector<Tablon> tablones = new Vector<Tablon>();//vector de tablones que incluyen su id y su nombre -> el id para coger los mensajes de cada uno y el nombre para mostrarlo.
	Vector<Message> messages = new Vector<Message>();

	if(session != null){
		user= (User)session.getAttribute("user");
	
		if(user!=null){
      		
			tablones = manager.getIdTablonUserModerates(user.getId());


			if(request.getParameter("tablon_id") == null || request.getParameter("tablon_id").compareTo("0") == 0){//o he pinchado sobre el primer tablon en mensajes.jsp o le he dado a Tus tablones por lo que automáticamente se me cargan esos.

				messages = manager.getMessagesFromTablon(tablones.elementAt(0).getId());


			}else{

				for(int e=1; e<tablones.size()+1; e++){//empiezo en 1 para mantener la lógica de la condición anterior.
					if(request.getParameter("tablon_id").compareTo(Integer.toString(e))==0){

						manager.getMessagesFromTablon(tablones.elementAt(e).getId());
						
					}
				}
			}
			
			request.setAttribute("tablones", tablones);//para saber el nombre de tablones en mensajes.jsp
			request.setAttribute("messages", messages);//para mostrar los mensajes que correspondan en mensajes.jsp

			next = "/mensajes.jsp"; //JSP destino
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
