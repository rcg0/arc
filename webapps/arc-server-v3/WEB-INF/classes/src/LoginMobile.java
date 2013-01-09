package arc;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import java.lang.String.*;

import com.google.gson.Gson;


public class LoginMobile extends HttpServlet {
  /**
	 * 
	 */
public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
	
	PrintWriter out = response.getWriter();
	HttpSession session = request.getSession(false);
	Gson gson;
	String json;
	User user = new User();
	System.out.println(request.getParameter("alias"));
	user.setNick(request.getParameter("alias"));

/*la cosa aquí es que no esté repetido el alias en la base de datos*/
	User databaseUser = user.existsSameNick();
	if(databaseUser != null){
		session=request.getSession();
		System.out.println("el id de sesion es: " + session.getId());
		gson = new Gson();
    	json = gson.toJson(databaseUser); 
		out.println(json);
		session.setAttribute("user",databaseUser);
		System.out.println("El usuario ha hecho login y este es su json completo: "+json);
	}else{
		out.println("nok");
	}

	

  }

  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
  
    doGet(request,response);

    }
}
