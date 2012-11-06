

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import java.util.Vector;

public class ejemplo extends HttpServlet {
  /**
	 * 
	 */
public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

	PrintWriter out = response.getWriter();


	
	
	out.println("Hola mundosdsdf");

  }

  public void doPost(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
  
    doGet(request,response);

    }
}
