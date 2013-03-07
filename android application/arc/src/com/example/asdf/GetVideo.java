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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;

/*
 * Si no encuentro la imagen en la galleria la pido, y la guardo.
 * 
 * */
class GetVideo extends AsyncTask<Button, Void, Bitmap> {
	
	  public Button button;
	  public TablonActivity activity;	

	    public GetVideo(TablonActivity a)
	    {
	        activity = a;
	    }

    	@SuppressLint({ "NewApi", "NewApi", "NewApi" })
		@Override
    	protected void onPostExecute(Bitmap result) {
    		//imageView.setImageBitmap(result);
    	/*String stringUri = MediaStore.Images.Media.insertImage(activity.getContentResolver(), result, imageView.getTag().toString(), "");
    	Uri uri = Uri.parse(stringUri);
    	
    	Context context = activity.getApplicationContext();
   	 	int duration = Toast.LENGTH_SHORT;
   	 	Toast toast = Toast.makeText(context, stringUri, duration);
   	 	toast.show();
    	
   	 	Bitmap thumbnail = MediaStore.Images.Thumbnails.getThumbnail(activity.getContentResolver(), Long.parseLong(uri.getLastPathSegment()), 3, null);//type = 1 -> MINI_KIND -> 512x384
   	 	imageView.setImageBitmap(thumbnail);*/
    	}

   protected Bitmap doInBackground(Button... parameter) {
	   		
			
	   		this.button = parameter[0];
			 
			try {
				return downloadVideo("http://bruckner.gast.it.uc3m.es:8080/arc-server-v3/user-content/"+button.getTag());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
			
		}
   
   private Bitmap downloadVideo(String url) throws IOException {
	    //---------------------------------------------------
	   final int TIMEOUT_CONNECTION = 5000;//5sec
	   final int TIMEOUT_SOCKET = 30000;//30sec


	               URL url1 = null;
				try {
					url1 = new URL(url);
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	               long startTime = System.currentTimeMillis();
	              // Log.i(TAG, "image download beginning: "+imageURL);

	               //Open a connection to that URL.
	               URLConnection ucon = url1.openConnection();

	               //this timeout affects how long it takes for the app to realize there's a connection problem
	               ucon.setReadTimeout(TIMEOUT_CONNECTION);
	               ucon.setConnectTimeout(TIMEOUT_SOCKET);


	               //Define InputStreams to read from the URLConnection.
	               // uses 3KB download buffer
	               InputStream is = ucon.getInputStream();
	               BufferedInputStream inStream = new BufferedInputStream(is, 1024 * 5);
	               //FileOutputStream outStream = new FileOutputStream(file);
	               //byte[] buff = new byte[5 * 1024];

	               //Read bytes (and store them) until there is nothing more to read(-1)
	               //int len;
	               /*while ((len = inStream.read(buff)) != -1)
	               {
	                   outStream.write(buff,0,len);
	               }

	               //clean up
	               outStream.flush();*/
	               /*outStream.close();*/
	               try {
					inStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;

	               //Log.i(TAG, "download completed in " + ((System.currentTimeMillis() - startTime) / 1000)+ " sec");5
	}
}
	
