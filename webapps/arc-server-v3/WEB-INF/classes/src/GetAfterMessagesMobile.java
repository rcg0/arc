package arc;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.Vector;

import com.google.gson.Gson;

public class GetAfterMessagesMobile extends HttpServlet {

	 
public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

	HttpSession session = request.getSession(false);
	User user = null;
	String next="/error.html";
	Tablon tablon = new Tablon();	
	PrintWriter out = response.getWriter();
   	Tablon returnTablon = new Tablon();
	
	tablon = tablon.getTablonDDBB(request.getParameter("tablonSpace"));
	System.out.println("el último messageId que tiene el cliente es: "+request.getParameter("messageId"));
	
	returnTablon = tablon.getAfterMessages(Integer.parseInt(request.getParameter("messageId")),tablon.getId());
	System.out.println("el parámetro que llega: "+request.getParameter("tablonSpace"));
	Gson gson = new Gson();
	String tablonJson = gson.toJson(returnTablon);
	System.out.println("Mas mensajes pedidos son: "+ tablonJson);
	out.println(tablonJson);

  }

  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
  
    doGet(request,response);

    }
}
