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
import java.util.List;
import java.util.Iterator;


import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
 

public class SendImage extends HttpServlet {
  /**
	 * 
	 */
public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

	String nextServlet = "/getAfterMessagesMobile"; //Servlet destino

	HttpSession session = request.getSession(false);

	System.out.println("el id de sesion es: " + session.getId());

	/*String message = URLDecoder.decode(request.getParameter("message"),"UTF-8");
	String tablonId=request.getParameter("tablonId").trim();
	String format = request.getParameter("format");
	String lastMessageId = request.getParameter("messageId");//lastMessageId
	*/

	    User sessionUser = (User)session.getAttribute("user");

	    FileItemFactory fileItemFactory = new DiskFileItemFactory();
	    ServletFileUpload servletFileUpload = new ServletFileUpload(fileItemFactory);


	    try {
                List fileItems = servletFileUpload.parseRequest(request);
                Iterator iterator = fileItems.iterator();
                while (iterator.hasNext()) {
		         FileItem fileItem = (FileItem)iterator.next();
                         if(fileItem.getFieldName().equals("image")) {
                                 String fileName = request.getRealPath("") + "/user-content/"+ fileItem.getName();

				 System.out.println("String fileName : "+ fileName);
                                 OutputStream outputStream = new FileOutputStream(fileName);
                                 InputStream inputStream = fileItem.getInputStream();
                                       
                                 int readBytes = 0;
                                 byte[] buffer = new byte[10000];
                                 while ((readBytes = inputStream.read(buffer, 0, 10000)) != -1) {
                                          outputStream.write(buffer, 0, readBytes);
                                 }
                                 outputStream.close();
                                 inputStream.close();
		   	
                        }else if (fileItem.getFieldName().equals("format")){
				System.out.println("El formato que ha llegado es : "+fileItem.getFieldName());
			}
		}
            } catch (Exception e) {
                        e.printStackTrace();
                }
        }

	    /*for (FileItem item : items) {
        	if (item.getFieldName().equals("image")) {
	            String fileName = FilenameUtils.getName(item.getName());
	            String fileContentType = item.getContentType();
	            InputStream fileContent = item.getInputStream();

		    System.out.println("Me ha llegado un formato");
            // ... (do your job here)
		    File file = new File(fileName);
		    try{		    
			item.write(file);
		    }catch(Exception e) {
			System.out.println(e);
		    }

        	}
    	     }
	} catch (FileUploadException e) {
	    throw new ServletException("Cannot parse multipart request.", e);
	}

	//System.out.println("El mensaje que se envía: Mensaje: "+ message );
	/**************************************************/
	/*if(sessionUser != null){
		Message msg = new Message();
		Tablon tablon = new Tablon();
	/*
		msg.setMsg(message);
		msg.setCreator(sessionUser);
		msg.setFormat(Integer.parseInt(format));

		tablon.setId(Integer.parseInt(tablonId));
		System.out.println("El tablon es: "+ tablonId);

		if(msg.getFormat() == 1){//if image

			System.out.println("He enviado una imagen.");
		}
*/
	/*}


	RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextServlet);
	dispatcher.forward(request,response);

  }*/

  public void doPost(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
  
    doGet(request,response);

    }
}
