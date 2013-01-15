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
	Tablon tablon = null;
	PrintWriter out = response.getWriter();
   	tablon= new Tablon();	
	
	tablon = tablon.getTablonDDBB(request.getParameter("tablonSpace"));
	System.out.println("el último messageId que tiene el cliente es: "+request.getParameter("messageId"));
	Vector<Message> msgs = tablon.getAfterMessages(Integer.parseInt(request.getParameter("messageId")),tablon.getId());
	System.out.println("el parámetro que llega: "+request.getParameter("tablonSpace"));
	Gson gson = new Gson();
	String messagesJson = gson.toJson(msgs);
	System.out.println("Mas mensajes pedidos son: "+messagesJson);
	out.println(messagesJson);

  }

  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
  
    doGet(request,response);

    }
}
