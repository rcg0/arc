package com.example.asdf;

import java.io.IOException;
import java.util.Vector;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.gson.Gson;

class GetMoreMessages extends AsyncTask<String, Integer, String> {
	
	public TablonActivity activity;	

    public GetMoreMessages(TablonActivity a)
    {
        activity = a;
    }
	
	@Override
	protected void onProgressUpdate(Integer... progress) {
//[... Update progress bar, Notification, or other UI element ...]
	}	
	@SuppressLint({ "NewApi", "NewApi", "NewApi" }) //ojo con esto
	@Override
	protected void onPostExecute(String result) {
//[... Report results via UI update, Dialog, or notification ...]
		/*En principio estaba puesto que soltara un toast*/
		
		if(result != null){
			Context context = activity.getApplicationContext();
			Gson gson = new Gson();
			Tablon tablonReceived = gson.fromJson(result, Tablon.class);
			if(!tablonReceived.getAllMsg().isEmpty()){
				activity.tablonSelected.printSomeMessages(tablonReceived.getAllMsg() ,activity.layout ,context);	
				activity.tablonSelected.setSomeMsg(tablonReceived.getAllMsg());
				activity.sendScroll();
			}else{
				int duration = Toast.LENGTH_SHORT;
				Toast toast = Toast.makeText(context, "No hay más mensajes que mostrar.", duration);
				toast.show();
			}
		}
	}

protected String doInBackground(String... parameter) {
		int myProgress = 0;
// 	[... Perform background processing task, update myProgress ...]
		publishProgress(myProgress);
// 		[... Continue performing background processing task ...]
// 	Return the value to be passed to onPostExecute
		
		HttpPost httppost = new HttpPost("http://bruckner.gast.it.uc3m.es:8080/arc-server-v3/getAfterMessagesMobile");

		Vector<BasicNameValuePair> l = new Vector<BasicNameValuePair>();
		//Añadimos todos los parámetros que queramos enviar
		l.add(new BasicNameValuePair("tablonId", activity.tablonSelected.getId()+""));
		l.add(new BasicNameValuePair("messageId",parameter[0]));

    			
		HttpResponse response = null;
		HttpEntity resEntity = null;
		String res = null;
		try {
			UrlEncodedFormEntity data = new UrlEncodedFormEntity(l, "utf-8");
			httppost.setEntity(data);
	
			response = activity.httpclient.execute(httppost);
			resEntity = response.getEntity();
		    
			/*a ver si funciona*/
			res = EntityUtils.toString(resEntity, HTTP.UTF_8);

			//BufferedReader b = new BufferedReader(new InputStreamReader(resEntity.getContent()));
			//Leeríamos la respuesta y haríamos algo con ella, en este caso únicamente leemos la primera linea
		    //res = b.readLine().trim();
			//resEntity.consumeContent();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			//tv.setText("No ha funcionado");
		}

		//httpclient.getConnectionManager().shutdown();

		return res;
				
		
	}


}
