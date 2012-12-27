package arc;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import java.util.Vector;
import com.google.gson.Gson;


public class GetTablonNames extends HttpServlet {

	 
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
      			
			tablon = new Tablon();
			Vector<Integer> tablonId = user.getModerators(); //obtengo el vector de enteros de tablones que modera el usuario
			Vector<String> tablonNames =  tablon.getTablonNames(tablonId);
			//lo paso a Json y lo envío
			Gson gson = new Gson();

			//flush it to the page
			out.println(gson.toJson(tablonId));
			out.println(gson.toJson(tablonNames));

		}

	}

  }

  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
  
    doGet(request,response);

    }
}