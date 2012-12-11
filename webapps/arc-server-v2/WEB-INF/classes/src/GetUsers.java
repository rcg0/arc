package arc;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.Vector;


import com.google.gson.Gson;

public class GetUsers extends HttpServlet {

	 
public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

	HttpSession session = request.getSession(false);
	String next="/error.html";
	User user = null;
	PrintWriter out = response.getWriter();
	String patron = "";
	Vector<User> users;

	if(session != null){
		user= (User)session.getAttribute("user");

		if(user!=null){
      		patron = request.getParameter("patron");
			System.out.println("Quiero usuarios que empiecen por la letra/s: "+request.getParameter("patron"));
			
			users = user.getUsersStartsWith(patron);

			Gson gson = new Gson();
			System.out.println(gson.toJson(users));
			out.println(gson.toJson(users));
		}

	}

  }

  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
  
    doGet(request,response);

    }
}
