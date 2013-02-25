package com.example.asdf;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
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
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

class SendMultiMedia extends AsyncTask<Object, Integer, String> {
	
	
	  public TablonActivity activity;	

	    public SendMultiMedia(TablonActivity a)
	    {
	        activity = a;
	    }

    	@Override
    	protected void onProgressUpdate(Integer... progress) {
// [... Update progress bar, Notification, or other UI element ...]
   	}	
    	@SuppressLint({ "NewApi", "NewApi", "NewApi" }) //ojo con esto
		@Override
    	protected void onPostExecute(String result) {
    		if (result != null){//no le ha llegado al servidor
    		/*este método hace lo mismo que el onpostexecute de getAfterMessages --> REFACTOR */
    		/*	Context context = getApplicationContext();
    			Gson gson = new Gson();
    			Tablon tablonReceived = gson.fromJson(result, Tablon.class);
    			if(!tablonReceived.getAllMsg().isEmpty()){
    				tablonSelected.printSomeMessages(tablonReceived.getAllMsg() ,layout ,context);	
    				tablonSelected.setSomeMsg(tablonReceived.getAllMsg());
    				sendScroll();
    			}
    			*/
    		}
    	}

   protected String doInBackground(Object... parameter) {
			int myProgress = 0;
	// 	[... Perform background processing task, update myProgress ...]
			publishProgress(myProgress);
	// 		[... Continue performing background processing task ...]
	// 	Return the value to be passed to onPostExecute

			 

			HttpPost httppost = new HttpPost("http://bruckner.gast.it.uc3m.es:8080/arc-server-v3/sendImage");

			/*Vector<BasicNameValuePair> l = new Vector<BasicNameValuePair>();
			//Añadimos todos los parámetros que queramos enviar
			
			
			/*l.add(new BasicNameValuePair("tablonId", activity.tablonSelected.getId()+""));
			//l.add(new BasicNameValuePair("tablonSpace", tablon.getSpaceId()));
			
			l.add(new BasicNameValuePair("message", (String)parameter[0] ));
			
			
			l.add(new BasicNameValuePair("format", (String)parameter[1]));
			l.add(new BasicNameValuePair("messageId", (String)parameter[2]));
			*/
			
			
			HttpResponse response = null;
			HttpEntity resEntity = null;
			String res = null;
			try {
				//UrlEncodedFormEntity data = new UrlEncodedFormEntity(l,"utf-8");
				
				 
				MultipartEntity entity = new MultipartEntity();
				
				entity.addPart("format", new StringBody((String)parameter[1]));
                entity.addPart("image", new FileBody((File)parameter[0]));  
                //entity.addPart("messageId", new StringBody((String)parameter[0]));
                entity.addPart("messageId", new StringBody(activity.tablonSelected.getId()+""));
                         
                httppost.setEntity(entity); 
               
				response = activity.httpclient.execute(httppost);
				

			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	
			//httpclient.getConnectionManager().shutdown();
	
			return res;
		}
}
	