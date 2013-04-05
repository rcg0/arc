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
class GetVideo extends AsyncTask<Object, Void, Void> {
	
	  public Button button;
	  public ImageButton imageButton;
	  public ImageHelper imageHelper;
	  
	  public TablonActivity activity;	

	    public GetVideo(TablonActivity a)
	    {
	        activity = a;
	    
	    }
	    

	    protected void onPreExecute() {
	        activity.setSupportProgressBarIndeterminateVisibility(true); 
	    }

    	@SuppressLint({ "NewApi", "NewApi", "NewApi" })
		protected void onPostExecute(Void result) {
    	    	
    	File file = new File(Environment.getExternalStorageDirectory()+"/ARC/"+button.getTag());
    	
    	Bitmap bMap = ThumbnailUtils.createVideoThumbnail(file.getAbsolutePath(), MediaStore.Video.Thumbnails.MICRO_KIND);
    	//bmap = null -> .wmv not supported, i'm going to test with another format
    	imageHelper = new ImageHelper();
    	
    	Bitmap bMapPlay = BitmapFactory.decodeResource(activity.getResources(), R.drawable.play);
    	
    	Bitmap videoBitmap = imageHelper.overlay(bMap, bMapPlay);
    	
    	button.setVisibility(4);//INVISIBLE
    	imageButton.setVisibility(0);//VISIBLE
    	
    	if(bMap == null){
			imageButton.setImageResource(R.drawable.arc_logo);

    	}else{
    		
    		imageButton.setImageBitmap(videoBitmap);
    	}
    	
    	imageButton.setOnClickListener(new View.OnClickListener() {
	        public void onClick(View v) {
	        	Intent intent = new Intent();	     
	        	File file = new File(Environment.getExternalStorageDirectory()+"/ARC/"+imageButton.getTag());
	        	intent.setDataAndType(Uri.fromFile(file), "video/*");
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
	   		this.imageButton = (ImageButton)parameter[1];
	   		
	   		FileHelper fileHelper = new FileHelper();
	   		
			fileHelper.downloadData("http://bruckner.gast.it.uc3m.es:8080/arc-server-v3/user-content/"+button.getTag(), button);
	   		
			
			return null;
			
		}
}
