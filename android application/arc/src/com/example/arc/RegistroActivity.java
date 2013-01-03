package com.example.arc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.example.arc.MainActivity.MyAsyncTask;

import android.os.AsyncTask;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class RegistroActivity extends Activity {

	Button buttonEntrar;
	EditText alias;
	CheckBox generoMasculino;
	CheckBox generoFemenino;
	Spinner edad;
	Spinner trabajo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registro);
		
		alias = (EditText)findViewById(R.id.editText1);
		buttonEntrar = (Button)findViewById(R.id.button1);
		generoFemenino = (CheckBox)findViewById(R.id.checkBox1);
		generoMasculino = (CheckBox)findViewById(R.id.checkBox2);
		edad = (Spinner)findViewById(R.id.spinner1);
		trabajo = (Spinner)findViewById(R.id.Spinner01);
	
		
		this.buttonEntrar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	
            	new MyAsyncTask().execute();//lanzo el hilo
            	
            }
      });
		
		
	}
    class MyAsyncTask extends AsyncTask<String, Integer, String> {
    	@Override
    	protected void onProgressUpdate(Integer... progress) {
// [... Update progress bar, Notification, or other UI element ...]
   	}	
    	@SuppressLint({ "NewApi", "NewApi", "NewApi" }) //ojo con esto
		@Override
    	protected void onPostExecute(String result) {
// [... Report results via UI update, Dialog, or notification ...]
    		/*En principio estaba puesto que soltara un toast*/
    		Context context = getApplicationContext();
    		int duration = Toast.LENGTH_SHORT;
    		Toast toast = Toast.makeText(context, result, duration);
    		toast.show();
    		
    		/*If the server says ok We access to the camera*/
    		if(result.equals("ok")){
    			Intent intent = new Intent("com.google.zxing.client.android.SCAN");
        		intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
        		startActivityForResult(intent, 0);
    		}

    	}

protected String doInBackground(String... parameter) {
			int myProgress = 0;
	// 	[... Perform background processing task, update myProgress ...]
			publishProgress(myProgress);
	// 		[... Continue performing background processing task ...]
	// 	Return the value to be passed to onPostExecute

			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost("http://192.168.1.3:8080/arc-server-v3/registerMobile");

			Vector<BasicNameValuePair> l = new Vector<BasicNameValuePair>();
			//Añadimos todos los parámetros que queramos enviar
			
			l.add(new BasicNameValuePair("alias", alias.getText().toString()));
			
			/*case genre*/
			if(generoMasculino.isSelected()){
				l.add(new BasicNameValuePair("genre", "Masculino"));
			}else{
				l.add(new BasicNameValuePair("genre", "Femenino"));
			}
			
			l.add(new BasicNameValuePair("age", edad.getSelectedItem().toString()));
			l.add(new BasicNameValuePair("work", trabajo.getSelectedItem().toString()));
	    			
			HttpResponse response = null;
			HttpEntity resEntity = null;
			String res = null;
			try {
				UrlEncodedFormEntity data = new UrlEncodedFormEntity(l);
				httppost.setEntity(data);
		
				response = httpclient.execute(httppost);
				resEntity = response.getEntity();
		
				BufferedReader b = new BufferedReader(new InputStreamReader(resEntity.getContent()));
				//Leeríamos la respuesta y haríamos algo con ella, en este caso únicamente leemos la primera linea
				res = b.readLine().trim();
				resEntity.consumeContent();
				
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
				//tv.setText("No ha funcionado");
			}
	
			httpclient.getConnectionManager().shutdown();
	
			return res;
		}
    
    
}
	
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_registro, menu);
		return true;
	}

	/*Obtener los resultados desde la misma actividad*/
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		   if (requestCode == 0) {
		      if (resultCode == RESULT_OK) {
		         String contents = intent.getStringExtra("SCAN_RESULT");
		         String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
		         
		         
		         Context context = getApplicationContext();
		    	 int duration = Toast.LENGTH_SHORT;
		    	 Toast toast = Toast.makeText(context, contents, duration);
		    	 toast.show();
		         /*textView.setText(contents);*/
		         //textView.append(format);
		         // Handle successful scan
		         
		         /*para pruebas del tablón*/

		         /*intent2= new Intent(this, TablonActivity.class);
		         startActivity(intent2);*/
		         
		      } else if (resultCode == RESULT_CANCELED) {
		         // Handle cancel
		      }
		   }
		}
	
	protected void onSaveInstanceState(Bundle savedInstanceState){
		super.onSaveInstanceState(savedInstanceState);
		savedInstanceState.putString("alias",alias.getText().toString());
		savedInstanceState.putBoolean("masculino",generoMasculino.isChecked());
		savedInstanceState.putBoolean("femenino",generoFemenino.isChecked());
		savedInstanceState.putInt("edad",edad.getPositionForView(edad));
		savedInstanceState.putInt("profesion",trabajo.getPositionForView(trabajo));
	}
	
	protected void onRestoreInstanceState(Bundle savedInstanceState){
		super.onRestoreInstanceState(savedInstanceState);
		
		alias.setText(savedInstanceState.getString("alias"));
		if(savedInstanceState.getBoolean("masculino")==true){
			generoMasculino.setChecked(true);
		}
		if(savedInstanceState.getBoolean("femenino")==true){
			generoFemenino.setChecked(true);
		}
		edad.setSelection(savedInstanceState.getInt("edad"));
		trabajo.setSelection(savedInstanceState.getInt("profesion"));
	}

	
}
