package com.example.asdf;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;

/*
 * Si no encuentro la imagen en la galleria la pido, y la guardo.
 * 
 * */
class GetVideo extends AsyncTask<ImageView, Void, Bitmap> {
	
	  public ImageView imageView;
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

   protected Bitmap doInBackground(ImageView... parameter) {
	   		return null;
			/*this.imageView = parameter[0];
			 
			return downloadImage("http://bruckner.gast.it.uc3m.es:8080/arc-server-v3/user-content/"+imageView.getTag());
			*/
		}
   
   private Bitmap downloadVideo(String url) {
	    //---------------------------------------------------
	    Bitmap bm = null;
	    try {
	        URL aURL = new URL(url);
	        URLConnection conn = aURL.openConnection();
	        conn.connect();
	        InputStream is = conn.getInputStream();
	        BufferedInputStream bis = new BufferedInputStream(is);
	        //bm = Factory.decodeStream(bis);
	        bis.close();
	        is.close();
	    } catch (IOException e) {
	        Log.e("Hub","Error getting the image from server : " + e.getMessage().toString());
	    } 
	    return bm;
	    //---------------------------------------------------
	}
}
	
