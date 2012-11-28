package arc;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import java.util.Vector;
import java.util.Map;

import com.google.gson.Gson;


public class GetTablonNames extends HttpServlet {

	 
public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

	HttpSession session = request.getSession(false);
	User user = null;
	String next="/error.html";
	Tablon tablon = null;
	PrintWriter out = response.getWriter();
	Map<Vector, Vector> mapa;

	if(session != null){
		user= (User)session.getAttribute("user");

		if(user!=null){
      			
			tablon = new Tablon();
			Vector<Integer> tablonId = user.getModerators(); //obtengo el vector de enteros de tablones que modera el usuario
			Vector<String> tablonNames =  tablon.getTablonNames(tablonId);
			//lo paso a Json y lo envío
			Gson gson = new Gson();
 			mapa.put("id",tablonId);
 			mapa.put("name",tablonNames);



			//flush it to the page
			out.println(gson.toJson(mapa));
			

		}

	}

  }

  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
  
    doGet(request,response);

    }
}