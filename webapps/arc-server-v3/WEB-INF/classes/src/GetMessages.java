package arc;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.Vector;


import com.google.gson.Gson;

public class GetMessages extends HttpServlet {

	 
public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {


	response.setCharacterEncoding("UTF-8");


	HttpSession session = request.getSession(false);
	User user = null;
	String next="/error.html";
	Tablon tablon = null;
	PrintWriter out = response.getWriter();
	int limit=2;//este valor puede cambiar


	if(session != null){
		user= (User)session.getAttribute("user");

		if(user!=null){
      		tablon= new Tablon();	
//			System.out.println("el parámetro que llega: "+request.getParameter("messageId"));
			Vector<Message> msg = tablon.getBeforeMessages(Integer.parseInt(request.getParameter("messageId")),limit);//id del mensaje, y el limite de mensajes que voy a recuperar
			tablon.setAllMsg(msg);
			Gson gson = new Gson();
			System.out.println(gson.toJson(tablon));
			System.out.println(tablon.getId());
			out.println(gson.toJson(tablon));
		}

	}

  }

  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
  
    doGet(request,response);

    }
}
