package com.example.asdf;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Vector;

import org.apache.http.HttpEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.FileEntity;
import org.apache.http.message.BasicNameValuePair;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;

/*
 * Si no encuentro la imagen en la galleria la pido, y la guardo.
 * 
 * */
class GetAudio extends AsyncTask<Object, Void, Void> {
	
	  public Button button;
	  
	  public TablonActivity activity;	

	    public GetAudio(TablonActivity a)
	    {
	        activity = a;
	    
	    }
	    

	    protected void onPreExecute() {
	        activity.setSupportProgressBarIndeterminateVisibility(true); 
	    }

    	@SuppressLint({ "NewApi", "NewApi", "NewApi" })
		protected void onPostExecute(Void result) {
    	
    	File xternal = Environment.getExternalStorageDirectory();
    	
    	final Uri uri = Uri.parse(Environment.getExternalStorageDirectory()+"/ARC/"+button.getTag());
    	    	
    	File file = new File(Environment.getExternalStorageDirectory()+"/ARC/"+button.getTag());
    	
    	String path = file.getAbsolutePath();
    	
    	
    	button.setText("Escuchar clip de audio: "+button.getTag());
    	    	
    	button.setOnClickListener(new View.OnClickListener() {
	        public void onClick(View v) {
	        	Intent intent = new Intent();	     
	        	File file = new File(Environment.getExternalStorageDirectory()+"/ARC/"+button.getTag());
	        	intent.setDataAndType(Uri.fromFile(file), "audio/*");
	        	intent.setAction(Intent.ACTION_VIEW);
	        	activity.startActivity(intent);
	        }
	    });   
    	
   	 	Toast toast = Toast.makeText(activity.getApplicationContext(), "Archivo "+button.getTag()+" descargado", Toast.LENGTH_SHORT);
   	 	toast.show();
    	
   	 	
        activity.setSupportProgressBarIndeterminateVisibility(false);
   	 	
    	}

   protected Void doInBackground(Object... parameter) {
	   		
			
	   		this.button = (Button)parameter[0];
	   		
			downloadData("http://bruckner.gast.it.uc3m.es:8080/arc-server-v3/user-content/"+button.getTag());
	   	
			return null;
			
		}
   
   
   private void downloadData(String url){
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
}