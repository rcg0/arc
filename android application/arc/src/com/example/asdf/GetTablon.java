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
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.actionbarsherlock.view.Menu;
import com.example.asdf.Tablon;
import com.example.asdf.TablonActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

class GetTablon extends AsyncTask<String, Integer, String> {
    	

	  	public TablonActivity activity;	
	  	public ProgressDialog dialog;
	  	
	    public GetTablon(TablonActivity a, ProgressDialog p)
	    {
	        activity = a;
	        dialog = p;
	    }
	
	 
	    protected void onPreExecute() {
	        dialog.show(); 
	    }

    	
    	@SuppressLint({ "NewApi", "NewApi", "NewApi" }) //ojo con esto
		@Override
    	protected void onPostExecute(String result) {

    		
    		if(result != null){
    			Context context = activity.getApplicationContext();
    			Gson gson = new Gson();
    			
    			activity.tablones = gson.fromJson(result, new TypeToken<Vector<Tablon>>(){}.getType());
    			
    			activity.printBar(activity.actionBar,activity.listener);
    			
    		}
    		/*MIRAR COMO SE PODRÍA HACER ESTO CON LO NUEVO
    		if(!activity.predefinedMessage.equals("")){//predefinedMessage Exists
    			activity.showSendDialog(activity.predefinedMessage);
    			activity.predefinedMessage = "";//lo reseteo para que no lo coja la siguiente vez
    		}*/
    		dialog.dismiss();
    		
    	}

protected String doInBackground(String... parameter) {
			int myProgress = 0;
	// 	[... Perform background processing task, update myProgress ...]
			publishProgress(myProgress);
	// 		[... Continue performing background processing task ...]
	// 	Return the value to be passed to onPostExecute
			
			HttpPost httppost = new HttpPost("http://bruckner.gast.it.uc3m.es:8080/arc-server-v3/getTablon");

			Vector<BasicNameValuePair> l = new Vector<BasicNameValuePair>();
			//Añadimos todos los parámetros que queramos enviar
			l.add(new BasicNameValuePair("tablonSpace",activity.space));
    		
	    			
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
