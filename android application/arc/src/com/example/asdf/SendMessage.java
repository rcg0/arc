package com.example.asdf;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

class SendMessage extends AsyncTask<String, Integer, String> {
	

	  public TablonActivity activity;	

	  public SendMessage(TablonActivity a){
	        activity = a;
	  }
	  
	  protected void onPreExecute() {
          activity.setSupportProgressBarIndeterminateVisibility(true); 
      }
	  
	  
	@Override
	protected void onProgressUpdate(Integer... progress) {
//[... Update progress bar, Notification, or other UI element ...]
	}	
	@SuppressLint({ "NewApi", "NewApi", "NewApi" }) //ojo con esto
	@Override
	protected void onPostExecute(String result) {
		if (result != null){//no le ha llegado al servidor
		/*este método hace lo mismo que el onpostexecute de getAfterMessages --> REFACTOR */
			Context context = activity.getApplicationContext();
			Gson gson = new Gson();
			Tablon tablonReceived = gson.fromJson(result, Tablon.class);
			if(!tablonReceived.getAllMsg().isEmpty()){
				activity.tablonSelected.printSomeMessages(tablonReceived.getAllMsg() ,activity.messagesLayout ,context,  activity);	
				activity.tablonSelected.setSomeMsg(tablonReceived.getAllMsg());
				activity.tablonSelected.sendScroll(activity);
			}
		}
        activity.setSupportProgressBarIndeterminateVisibility(false);
	}

protected String doInBackground(String... parameter) {
		
		HttpPost httppost = new HttpPost("http://bruckner.gast.it.uc3m.es:8080/arc-server-v3/sendMessageMobile");

		Vector<BasicNameValuePair> l = new Vector<BasicNameValuePair>();
		//Añadimos todos los parámetros que queramos enviar
		
		
		l.add(new BasicNameValuePair("tablonId", activity.tablonSelected.getId()+""));
		//l.add(new BasicNameValuePair("tablonSpace", tablon.getSpaceId()));
		
		l.add(new BasicNameValuePair("message", parameter[0] ));
		
		
		l.add(new BasicNameValuePair("format", parameter[1] ));
		l.add(new BasicNameValuePair("messageId", parameter[2]));
		
		
		HttpResponse response = null;
		HttpEntity resEntity = null;
		String res = null;
		try {
			UrlEncodedFormEntity data = new UrlEncodedFormEntity(l,"utf-8");
			
			httppost.setEntity(data);
	
			response = activity.httpclient.execute(httppost);
			Log.i("javi", "checkpoint1");
			resEntity = response.getEntity();
			BufferedReader b = new BufferedReader(new InputStreamReader(resEntity.getContent()));
			//Leeríamos la respuesta y haríamos algo con ella, en este caso únicamente leemos la primera linea
			res = b.readLine().trim();
			resEntity.consumeContent();
			Log.i("javi", "checkpoint2");
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		//httpclient.getConnectionManager().shutdown();

		return res;
	}


}
