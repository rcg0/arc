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
import android.widget.Toast;

class SendRate extends AsyncTask<String, Integer, String> {
	
	public TablonActivity activity;	

    public SendRate(TablonActivity a)
    {
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
//[... Report results via UI update, Dialog, or notification ...]
		/*En principio estaba puesto que soltara un toast*/
		Context context = activity.getApplicationContext();
		int duration = Toast.LENGTH_SHORT;
		Toast toast;
		String message = null;
		/*If the server says ok muestro un toast*/
		if(result == null){
			message = "La puntuación no se pudo enviar en estos momentos";
		}else{
    		float media = Float.parseFloat(result);
			
    		if(media >= 0 || media<=5){
    			activity.tablonSelected.printRate(activity.ratingBar, media);
				message = "La puntuación media del evento es de: "+media;
			}
		}
		toast = Toast.makeText(context, message , duration);
		toast.show();
		
		activity.setSupportProgressBarIndeterminateVisibility(false);
	}

protected String doInBackground(String... parameter) {

		HttpPost httppost = new HttpPost("http://bruckner.gast.it.uc3m.es:8080/arc-server-v3/sendRateMobile");
		
		Vector<BasicNameValuePair> l = new Vector<BasicNameValuePair>();
		//Añadimos todos los parámetros que queramos enviar
		
		l.add(new BasicNameValuePair("rate", activity.ratingBar.getRating()+""));
		l.add(new BasicNameValuePair("tablonId", activity.tablonSelected.getId()+""));

    			
		HttpResponse response = null;
		HttpEntity resEntity = null;
		String res = null;
		try {
			UrlEncodedFormEntity data = new UrlEncodedFormEntity(l, "utf-8");
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
