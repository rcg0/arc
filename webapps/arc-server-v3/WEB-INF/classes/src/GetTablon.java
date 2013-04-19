package arc;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.Vector;

import com.google.gson.Gson;

public class GetTablon extends HttpServlet {

	 
public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

	response.setHeader("Content-Type", "text/plain; charset=UTF-8");
	HttpSession session = request.getSession(false);
	User user = null;
	String next="/error.html";
	Vector<Tablon> tablones = new Vector<Tablon>();
	PrintWriter out = response.getWriter();

   	Tablon tablon= new Tablon();	
   	user = (User)session.getAttribute("user");
	tablones = tablon.getTablonDDBB(request.getParameter("tablonSpace"), user);

	System.out.println("Nombre de tablón: "+tablones.elementAt(0).getName());

	System.out.println("el parámetro que llega: "+request.getParameter("tablonSpace"));
	
	Gson gson = new Gson();
	System.out.println(gson.toJson(tablones));

	out.println(gson.toJson(tablones));

  }

  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
  
    doGet(request,response);

    }
}
