package arc;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import com.google.gson.Gson;

public class GetTablon extends HttpServlet {

	 
public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

	HttpSession session = request.getSession(false);
	User user = null;
	String next="/error.html";
	Tablon tablon = null;
	PrintWriter out = response.getWriter();

	if(session != null){
		user= (User)session.getAttribute("user");

		if(user!=null){
      		tablon= new Tablon();	
			tablon = tablon.getTablonDDBB(Integer.parseInt(request.getParameter("tablonId")));
			System.out.println("el parámetro que llega: "+request.getParameter("tablonId"));
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
