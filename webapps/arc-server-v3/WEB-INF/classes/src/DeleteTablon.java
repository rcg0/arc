package arc;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import java.util.Vector;

public class DeleteTablon extends HttpServlet {
  /**
	 * 
	 */
public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

      

	HttpSession session = request.getSession(false);
	String next = "/error.html";
	Tablon tablon;

	if(session!=null){
      
		User user= (User)session.getAttribute("user");
		//Vector<Tablon> tablones = (Vector<Tablon>)session.getAttribute("tablones");
		tablon = new Tablon();

		int tablonId = Integer.parseInt(request.getParameter("tablonId"));//identificador en el jsp, no equivale a id de bbdd, 0,1...    
		tablon.setId(tablonId);

		if(user!=null){
			System.out.println("tablon id para eliminar:" + tablonId);

			//tablon.elementAt(tablonId).deleteTablon();//lo elimino en la base de datos
			//tablones.remove(tablon);
			//request.setAttribute("tablon",-1);

			tablon.deleteTablon();
			//next = "/mis-tablones.jsp"; //destino
			next= "/showModifyTablones";
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
