package arc;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import java.lang.String.*;

public class LoginMobile extends HttpServlet {
  /**
	 * 
	 */
public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
	
	PrintWriter out = response.getWriter();
	HttpSession session = request.getSession(false);

	User user = new User();
	System.out.println(request.getParameter("alias"));
	user.setNick(request.getParameter("alias"));

/*la cosa aquí es que no esté repetido el alias en la base de datos*/
	if(user.existsSameNick()){
		session=request.getSession();
		out.println("Pasa");
	}else{
		out.println("No existe, debe registrarse para entrar");
	}

	

  }

  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
  
    doGet(request,response);

    }
}
