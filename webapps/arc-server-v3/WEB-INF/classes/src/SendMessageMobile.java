package arc;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.net.URLDecoder;
import java.util.Vector;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Random;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import javax.imageio.ImageIO;
 
import sun.misc.BASE64Decoder;


public class SendMessageMobile extends HttpServlet {
  /**
	 * 
	 */
public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

	String nextServlet = "/getAfterMessagesMobile"; //Servlet destino

	HttpSession session = request.getSession(false);

	System.out.println("el id de sesion es: " + session.getId());

	String message = URLDecoder.decode(request.getParameter("message"),"UTF-8");
	String tablonId=request.getParameter("tablonId").trim();
	String format = request.getParameter("format");
	String lastMessageId = request.getParameter("messageId");//lastMessageId

	User sessionUser = (User)session.getAttribute("user");

	System.out.println("El mensaje que se envía: Mensaje: "+ message );
	/**************************************************/
	if(sessionUser != null){
		Message msg = new Message();
		Tablon tablon = new Tablon();
	
		msg.setMsg(message);
		msg.setCreator(sessionUser);
		msg.setFormat(Integer.parseInt(format));

		tablon.setId(Integer.parseInt(tablonId));
		System.out.println("El tablon es: "+ tablonId);

		if(msg.getFormat() == 0){//if text
			//envía el mensaje a la base de datos
			long writeMessageId = tablon.sendMessage(msg);
			//msg.setVisibility();
			System.out.println("El mensaje que he enviado ha sido el siguiente: "+ msg.getMsg());
		}
		else if(msg.getFormat() == 1){//if image

			/*Random r = new Random();
			java.util.Date date= new java.util.Date();
			new Timestamp(date.getTime());

			byte[] imageByteArray = Base64.decode(message);
			FileOutputStream f = new FileOutputStream("/home/apt/jrafael/apache-tomcat-6.0.35/webapps/arc-server-v3/IMG/pruebaaaa.jpg");
			f.write(imageByteArray);
			f.close();
						System.out.println("El mensaje que he enviado ha sido una imagen");
			//Base64.decodeToFile(message, "IMG/test.jpg");
			//System.out.println("la ruta es: "+"usersImages/"+new Timestamp(date.getTime())+""+r.nextInt()+".png");
			*/


       			BASE64Decoder decoder = new BASE64Decoder();
        		byte[] decodedBytes = decoder.decodeBuffer(message);

 			System.out.println("hola caracola");
           
           
		        String uploadFile = "IMG/test.png";


		        BufferedImage image = ImageIO.read(new ByteArrayInputStream(decodedBytes));
 			if (image == null) {
		              System.out.println("Buffered Image is null");
		        }
 			System.out.println("El mensaje que he enviado ha sido una imagen");
		        File f = new File(uploadFile);
 
		         // write the image
		        ImageIO.write(image, "png", f);
      			System.out.println("El mensaje que he enviado ha sido una imagen");
		}
	}


	RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextServlet);
	dispatcher.forward(request,response);

  }

  public void doPost(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
  
    doGet(request,response);

    }
}
