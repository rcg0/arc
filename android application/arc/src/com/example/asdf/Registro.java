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

class Registro extends AsyncTask<String, Integer, String> {
	

	public RegistroActivity activity;	

    public Registro(RegistroActivity a)
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
		Context context = activity.getApplicationContext();
		int duration = Toast.LENGTH_SHORT;
		Toast toast;
		
		if(result == null){
			toast = Toast.makeText(context, "No ha sido posible conectar con el servidor.", duration);
			toast.show();
		}else{
			if(!result.equals("nok")){
				Gson gson = new Gson();
				activity.jsonUser = result;
				User userLogin = gson.fromJson(result, User.class);
				activity.callCamera();
				
			}else{
				
				toast = Toast.makeText(context, "El nick ya está elegido, pruebe de nuevo", duration);
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

		HttpPost httppost = new HttpPost("http://bruckner.gast.it.uc3m.es:8080/arc-server-v3/registerMobile");

		Vector<BasicNameValuePair> l = new Vector<BasicNameValuePair>();
		//Añadimos todos los parámetros que queramos enviar
		
		l.add(new BasicNameValuePair("alias", activity.email.getText().toString()));
		
		Boolean masc = activity.generoMasculino.isChecked();
		Boolean fem = activity.generoFemenino.isChecked();
		/*case genre*/
		if(activity.generoMasculino.isChecked()){
			l.add(new BasicNameValuePair("genre", "Masculino"));
		}
		if(activity.generoFemenino.isChecked()){
			l.add(new BasicNameValuePair("genre", "Femenino"));
		}
		
		l.add(new BasicNameValuePair("age", activity.edad.getSelectedItem().toString()));
		l.add(new BasicNameValuePair("work", activity.trabajo.getText().toString()));
    			
		HttpResponse response = null;
		HttpEntity resEntity = null;
		String res = null;
		try {
			UrlEncodedFormEntity data = new UrlEncodedFormEntity(l, HTTP.UTF_8);
			httppost.setEntity(data);
	
			response = activity.httpclient.execute(httppost);
			resEntity = response.getEntity();
	
            res = EntityUtils.toString(resEntity, "UTF-8");
			/*BufferedReader b = new BufferedReader(new InputStreamReader(resEntity.getContent()));
			//Leeríamos la respuesta y haríamos algo con ella, en este caso únicamente leemos la primera linea
			res = b.readLine().trim();
			resEntity.consumeContent();*/
			
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
