package com.example.asdf;


import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.net.URL;
import java.net.URLConnection;

import net.sourceforge.cardme.vcard.VCard;
import net.sourceforge.cardme.vcard.exceptions.VCardBuildException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;

public class FileHelper {


	public void copy(File src, File dst) throws IOException {
	    InputStream in = new FileInputStream(src);
	    OutputStream out = new FileOutputStream(dst);

	    // Transfer bytes from in to out
	    byte[] buf = new byte[1024];
	    int len;
	    while ((len = in.read(buf)) > 0) {
	        out.write(buf, 0, len);
	    }
	    in.close();
	    out.close();
	}
	
	
	public String getFormatFromUriContainsIfContainsFormat(Uri uri){
		String format = "";
		String uriString = uri.toString();
		
		if(uriString.contains("images")){
			format = "1";
		}
		else if(uriString.contains("audio")){
			format = "2";
		}
		else if(uriString.contains("video")){
			format = "3";
		}
		
		return format;
	}
	
	
	public String getFormatFromUri(Uri uri){
		String format = "";
		String uriString = uri.toString();
		int lastIndex = uriString.lastIndexOf(".");
		String substring = uriString.substring(lastIndex+1);
		
		if(substring.equalsIgnoreCase("AAC") || substring.equalsIgnoreCase("MP3") 
				|| substring.equalsIgnoreCase("WMA") || substring.equalsIgnoreCase("WAV")
				|| substring.equalsIgnoreCase("MIDI")){
			format = "2";//audio
		}else if(substring.equalsIgnoreCase("JPEG") || substring.equalsIgnoreCase("PNG") 
				|| substring.equalsIgnoreCase("JPG") || substring.equalsIgnoreCase("BMP")
				|| substring.equalsIgnoreCase("GIF")){
			format = "1";//imagen
		}else if(substring.equalsIgnoreCase("avi") || substring.equalsIgnoreCase("mov") 
				|| substring.equalsIgnoreCase("3gp") || substring.equalsIgnoreCase("m4v")
				|| substring.equalsIgnoreCase("wmv")){
			format = "3";//video
		}
		
		return format;
		
	}
	
	  public void downloadData(String url, Button button){
		    try{
		    	
		        URL URL  = new URL(url);
		        URLConnection conn = URL.openConnection();
		        conn.connect();

		        InputStream is = URL.openStream();

		        File testDirectory = new File(Environment.getExternalStorageDirectory()+"/ARC/");

		        FileOutputStream fos = new FileOutputStream(testDirectory+"/"+button.getTag());

		        byte data[] = new byte[1024];

		        int count = 0;
		        
		        while ((count=is.read(data)) != -1)
		        {
		            fos.write(data, 0, count);
		        }

		        is.close();
		        fos.close();

		    }catch(Exception e){
		        e.printStackTrace();
		    }
	   }
	


	  public Bitmap downloadImage(String url, ImageButton imageButton) {
	    //---------------------------------------------------
	    Bitmap bm = null;
	    try {
	        URL aURL = new URL(url);
	        URLConnection conn = aURL.openConnection();
	        conn.connect();
	        InputStream is = conn.getInputStream();
	        
	        File testDirectory = new File(Environment.getExternalStorageDirectory()+"/ARC/");
	        	        
	        BufferedInputStream bis = new BufferedInputStream(is);
	        bm = BitmapFactory.decodeStream(bis);
	        bis.close();
	        is.close();
	        
	        saveBitmap(bm, imageButton);
	        
	    } catch (IOException e) {
	        Log.e("Hub","Error getting the image from server : " + e.getMessage().toString());
	    } 
	    return bm;
	}
   
	  public void saveBitmap(Bitmap bm, ImageButton imageButton){
       try{
           String mBaseFolderPath = Environment.getExternalStorageDirectory()+"/ARC/";
           String mFilePath = mBaseFolderPath + imageButton.getTag();

           FileOutputStream stream = new FileOutputStream(mFilePath);
           bm.compress(CompressFormat.JPEG, 100, stream);
           stream.flush();
           stream.close();
       }
       catch(Exception e){
           Log.e("Could not save", e.toString());
       }
   }
	  
	  public void saveVcardToFile( String fileName , net.sourceforge.cardme.vcard.VCardImpl vcard) throws VCardBuildException {
		    Writer output = null;   
		    File file = new File(fileName);   
		    try {
		        output = new BufferedWriter(new FileWriter(file));
		        net.sourceforge.cardme.io.VCardWriter writer = new net.sourceforge.cardme.io.VCardWriter();
		        writer.setVCard(vcard);
		        output.write(writer.buildVCardString());  
		        output.flush();  
		        output.close(); 
		    } catch (IOException e) {
		        // TODO Auto-generated catch block
		        e.printStackTrace();
		    }   
	}

	  
}
