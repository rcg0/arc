package arc;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import java.util.Vector;

public class ShowModifyTablones extends HttpServlet {
  /**
	 * 
	 */
public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

      

	HttpSession session = request.getSession(false);
	String next = "/error.html";
	User user;
	Vector<Tablon> tablones;
	Vector<User> users;


	if(session!=null){
      
		user= (User)session.getAttribute("user");

		if(user!=null){
      		/*	tablones=(Vector<Tablon>)session.getAttribute("tablones");
			if(tablones!=null){
			    
				if(request.getParameter("tablonId")==null || request.getParameter("tablonId").compareTo("0")==0){//o he pinchado en el primer tablón o he entrado por primera vez
					request.setAttribute("tablon", 0); //para volver al mismo tablón!
				}  
				else{
					for(int e=1; e<tablones.size()+1; e++){//empiezo en 1 para mantener la lógica de la condición anterior.
						if(request.getParameter("tablonId").compareTo(Integer.toString(e))==0){

						      request.setAttribute("tablon", e);
						}
					}

				}
			    
			}
			//necesito todos los usuarios para mostrarlo en el jsp
			*/
			users = user.getAllUsers();
			request.setAttribute("allUsers",users);
  
			next = "/mis-tablones.jsp"; //JSP destino

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
