package arc;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import java.lang.String.*;
import java.util.Vector;

public class Access extends HttpServlet {
  /**
	 * 
	 */
public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
	
	String nextServlet = "/profile"; //Servlet destino

	HttpSession session = request.getSession(false);
	User user = new User();
	
	String nameAndMore = new String(request.getParameter("name"));
	String [ ] names= nameAndMore.split("\\ ");


	user.setName(names[0]);
	user.setSurName1(names[1]);
	user.setSurName2(names[2]);

	User dataBaseUser = user.checkUser(user);

	if(dataBaseUser.getName().compareTo(user.getName())==0 && dataBaseUser.getSurName1().compareTo(user.getSurName1())==0 && dataBaseUser.getSurName2().compareTo(user.getSurName2())==0){

		session=request.getSession();
		session.setAttribute("user",dataBaseUser);

		/*Voy a necesitar saber que tablones modera este usuario en las demás páginas, así que lo averiguo y obtengo sus 'soft tablones'*/

		/*
		Vector<Tablon> tablones = new Vector<Tablon>();//vector de tablones que incluyen todo salvo los mensajes ya que si no no escalaría
    
		
		Vector<Integer> tablonId = dataBaseUser.getModerators(); //obtengo el vector de enteros de tablones que modera el usuario

		for(int i=0; i<tablonId.size();i++){
				Tablon tablon= new Tablon();
				tablones.addElement(tablon.getSoftTablonInformation(tablonId.elementAt(i).intValue()));
		}
		session.setAttribute("tablones",tablones);//guardo el soft tablon (sin mensajes :) que sino no escala)
		*/

	}

	
	RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextServlet);
	dispatcher.forward(request,response);

  }

  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
  
    doGet(request,response);

    }
}
