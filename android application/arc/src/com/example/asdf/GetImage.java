package com.example.asdf;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.gson.Gson;

/*
 * Si no encuentro la imagen en la galleria la pido, y la guardo.
 * 
 * */
class GetImage extends AsyncTask<ImageButton, Void, Bitmap> {
	
	  public ImageButton imageButton;
	  public TablonActivity activity;	

	    public GetImage(TablonActivity a)
	    {
	        activity = a;
	    }

    	@SuppressLint({ "NewApi", "NewApi", "NewApi" })
		@Override
    	protected void onPostExecute(Bitmap result) {
    	
    		String stringUri = MediaStore.Images.Media.insertImage(activity.getContentResolver(), result, imageButton.getTag().toString(), "");
   	 		
    		Bitmap thumbnail = ThumbnailUtils.extractThumbnail(result, 96, 96);

    		final Uri uri = Uri.parse(stringUri);
    		
   	 		imageButton.setImageBitmap(thumbnail);
   	 		
   	 		imageButton.setOnClickListener(new View.OnClickListener() {
	        public void onClick(View v) {

	        	Intent intent = new Intent(Intent.ACTION_VIEW, uri);
	        	activity.context.startActivity(intent);

	        }
   	 		});
   	 		
    	}

   protected Bitmap doInBackground(ImageButton... parameter) {
	
			this.imageButton = parameter[0];
	        
			FileHelper fileHelper = new FileHelper();

			Bitmap	bitmap = fileHelper.downloadImage("http://bruckner.gast.it.uc3m.es:8080/arc-server-v3/user-content/"+imageButton.getTag(), imageButton);

			return bitmap;
			
			
		}
   

   
}
	
