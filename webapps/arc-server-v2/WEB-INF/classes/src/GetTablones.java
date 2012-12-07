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
	Vector<Tablon> tablones;
	String next="/error.html";
	Tablon tablon;

	if(session != null){
		user= (User)session.getAttribute("user");

		if(user!=null){
      			
			String attribute = request.getParameter("tablonId");
			if(attribute!=null){
				tablon= new Tablon();
				tablon.setId(Integer.parseInt(attribute));
				tablon.getSomeMsg(2);
				
				System.out.println("id de tablon incrustado: "+ tablon.getId());

				request.setAttribute("tablon", tablon);
			}



      			/*tablones=(Vector<Tablon>)session.getAttribute("tablones");
				if(tablones!=null){

				     
					
					if(request.getParameter("tablonId") == null || request.getParameter("tablonId").compareTo("0") == 0){//o he pinchado sobre el primer tablon en mensajes.jsp o le he dado a Tus tablones por lo que automáticamente se me cargan esos.
						  //Vector<Message> msg = tablones.elementAt(0).getSomeMsg(100);
						 //DataBaseManager manager = new DataBaseManager(); 
						 //Vector<Message> msg = manager.getMessagesFromTablon(t.getId());
						  if(tablones.elementAt(0) != null){
						    tablones.elementAt(0).getSomeMsg(50);//limit=50
						  }
						 
						 request.setAttribute("tablon",0);
						  
					}else{

						for(int e=1; e<tablones.size()+1; e++){//empiezo en 1 para mantener la lógica de la condición anterior.
							if(request.getParameter("tablonId").compareTo(Integer.toString(e))==0){
							    
							      tablones.elementAt(e).getSomeMsg(50) ;//cojo los 10 primeros mensajes del primer tablon
							    
							      request.setAttribute("tablon", e);
							}
						}
					
				    }

				}
			*/
			next = "/mensajes.jsp"; //JSP destino

		}

	}
	RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(next);
	dispatcher.forward(request,response);

  }

  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
  
    doGet(request,response);

    }
}
