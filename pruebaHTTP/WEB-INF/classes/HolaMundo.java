

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import java.util.Vector;

import com.google.gson.Gson;


public class HolaMundo extends HttpServlet {
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

	PrintWriter out = response.getWriter();
	String json;
	Gson gson;
/*prueba de tablon---> esto habría que hacerlo en estático, claro y luego añadir una lógica que dependiendo del perfil mande unas cosas u otras, esto es 
unicamente de ejemplo, una prueba de funcionamiento*/

    	Tablon tablon = new Tablon();
    	tablon.setId("1");
    	Mensaje mensaje1 = new Mensaje();
    	mensaje1.setId("1");
    	mensaje1.setMsg("Mensaje 1");
    	Mensaje mensaje2 = new Mensaje();
    	mensaje2.setId("2");
    	mensaje2.setMsg("Mensaje 2");
    	tablon.setMsg(mensaje1);
    	tablon.setMsg(mensaje2);
/******************/    	
    	gson = new Gson();
    	json = gson.toJson(tablon);  

	
	
	out.println(json);

  }

  public void doPost(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
  
    doGet(request,response);

    }
}

