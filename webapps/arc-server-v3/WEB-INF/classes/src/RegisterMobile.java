package arc;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import java.lang.String.*;

public class RegisterMobile extends HttpServlet {
  /**
	 * 
	 */
public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
	
	PrintWriter out = response.getWriter();
	HttpSession session = request.getSession(false);

	User user = new User();
	user.setNick(request.getParameter("alias"));

/*la cosa aquí es que no esté repetido el alias en la base de datos*/
	if(user.existsSameNick()){
		session=request.getSession();
		out.println("El nick ya existe, prueba con otro.");
	}else{

		System.out.println(request.getParameter("alias"));
		System.out.println(request.getParameter("age"));
		System.out.println(request.getParameter("work"));
		System.out.println(request.getParameter("genre"));
		
		user.setAge(request.getParameter("age"));
		user.setWork(request.getParameter("work"));
		user.setGenre(request.getParameter("genre"));
		user.saveRegister();
		out.println("ok");

	}

  }

  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
  
    doGet(request,response);

    }
}
