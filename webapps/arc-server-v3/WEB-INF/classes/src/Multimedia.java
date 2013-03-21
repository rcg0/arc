package arc;

import javax.servlet.*;
import javax.servlet.http.*;
import java.util.Iterator;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.*;
import java.util.List;


import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class Multimedia{
	
	public Tablon receiveMultimedia(HttpServletRequest request){

		FileItemFactory fileItemFactory = new DiskFileItemFactory();
		ServletFileUpload servletFileUpload = new ServletFileUpload(fileItemFactory);

		Message message = new Message();
		Tablon tablon = new Tablon();
		tablon.setMsg(message);

		try {
        	List fileItems = servletFileUpload.parseRequest(request);
        	Iterator iterator = fileItems.iterator();
        	
			while (iterator.hasNext()) {
		    
		        FileItem fileItem = (FileItem)iterator.next();
		        String fieldName = fileItem.getFieldName();
		        System.out.println("The field name is: "+fieldName);
            
            	if(fieldName.equals("image") || fieldName.equals("file")) {
            
                	String fileName = request.getRealPath("") + "/user-content/"+ fileItem.getName();
			System.out.println(fileName);
			String fileNameWithoutBlanks = fileName.replace(" ","-");			
			System.out.println("String fileName : "+ fileNameWithoutBlanks);
			message.setMsg(fileNameWithoutBlanks);
			writeMultimediaToDisk(fileNameWithoutBlanks, fileItem);
	                System.out.println("writeMultimediaToDisk success");
		   	
            	}else if(fieldName.equals("format")){
            	
					int format = Integer.parseInt(fileItem.getString().trim());
					message.setFormat(format);
					System.out.println("El valor que ha llegado es : "+format);

				}else if(fieldName.equals("tablonId")){
            		
            		int tablonId = Integer.parseInt(fileItem.getString().trim());
            		tablon.setId(tablonId);
					System.out.println("El valor que ha llegado es : "+tablonId);

				}else if(fieldName.equals("messageId")){//cambiar por "tablonId" en cliente y aquí
            		
            		int messageId = Integer.parseInt(fileItem.getString());
            		tablon.setLastMessageId(messageId);
            	
				}
			}
    	}catch (Exception e) {
       		e.printStackTrace();
    	}
		return tablon;
	}

	public void writeMultimediaToDisk(String fileName, FileItem fileItem){
		
		try{
			OutputStream outputStream = new FileOutputStream(fileName);
    		InputStream inputStream = fileItem.getInputStream();
                                       
        	int readBytes = 0;
        	byte[] buffer = new byte[10000];
        	while ((readBytes = inputStream.read(buffer, 0, 10000)) != -1) {
           		outputStream.write(buffer, 0, readBytes);
        	}
        	outputStream.close();
      		inputStream.close();
      	}catch(Exception e){
	      	e.printStackTrace();
      	}
	}


}
