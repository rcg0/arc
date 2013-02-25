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
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;


class Logout extends AsyncTask<String, Integer, String> {
	
	
	public TablonActivity activity;	

    public Logout(TablonActivity a)
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
		
		Context context = activity.getApplicationContext();
		int duration = Toast.LENGTH_SHORT;
		Toast toast;
		
		if (result == null){//no le ha llegado al servidor
			toast = Toast.makeText(context, "No ha sido posible conectar con el servidor.", duration);
			toast.show();
		}
		else if(result.equals("ok")){
			toast = Toast.makeText(context, "Usuario desconectado.", duration);
			toast.show();
			Intent intent = new Intent(activity, MainActivity.class);
		    activity.startActivity(intent);
		    activity.finish();
		}
	}

protected String doInBackground(String... parameter) {
		int myProgress = 0;
// 	[... Perform background processing task, update myProgress ...]
		publishProgress(myProgress);
// 		[... Continue performing background processing task ...]
// 	Return the value to be passed to onPostExecute

		

		HttpPost httppost = new HttpPost("http://bruckner.gast.it.uc3m.es:8080/arc-server-v3/logoutMobile");

		Vector<BasicNameValuePair> l = new Vector<BasicNameValuePair>();
		//Añadimos todos los parámetros que queramos enviar
				
		HttpResponse response = null;
		HttpEntity resEntity = null;
		String res = null;
		try {
			UrlEncodedFormEntity data = new UrlEncodedFormEntity(l,"utf-8");
			httppost.setEntity(data);
	
			response = activity.httpclient.execute(httppost);
			resEntity = response.getEntity();
			BufferedReader b = new BufferedReader(new InputStreamReader(resEntity.getContent()));
			//Leeríamos la respuesta y haríamos algo con ella, en este caso únicamente leemos la primera linea
			res = b.readLine().trim();
			resEntity.consumeContent();
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		//httpclient.getConnectionManager().shutdown();

		return res;
	}


}
