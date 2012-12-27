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

import android.os.AsyncTask;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	Button buttonRegistrate;
	Button buttonAccede;
	TextView textAlias;
	EditText editAlias;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		buttonAccede = (Button)findViewById(R.id.button1);
		buttonRegistrate = (Button)findViewById(R.id.button2);
	    
		textAlias = (TextView)findViewById(R.id.TextView01);
		editAlias = (EditText)findViewById(R.id.editText2);
		
        this.buttonRegistrate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	//button.setText("He pulsado");
            	Intent intent = new Intent(MainActivity.this, RegistroActivity.class);
        		
        		startActivity(intent);
         
            }
      });
        
        this.buttonAccede.setOnClickListener(new View.OnClickListener() {
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
        		
        		
        		/*Gson gson = new Gson();
        		Tablon tablonRecibido = gson.fromJson(result, Tablon.class);
        		Vector <Mensaje> msg = tablonRecibido.getMsg(); 
        		numeroMensajes = msg.size();
        		vectorTv = new Vector<TextView>(numeroMensajes);
        		
        		for(int i = 0; i<numeroMensajes; i++){
        			TextView t = new TextView(context);
        			t.setText(msg.elementAt(i).getMsg());
        			t.setPadding(50, 0, 0, 0);
        			t.setY(50*i);
        			
        			layout.addView(t, i, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
        			
        		}
        		*/
        		

        	}

    protected String doInBackground(String... parameter) {
    			int myProgress = 0;
    	// 	[... Perform background processing task, update myProgress ...]
    			publishProgress(myProgress);
    	// 		[... Continue performing background processing task ...]
    	// 	Return the value to be passed to onPostExecute

    			HttpClient httpclient = new DefaultHttpClient();
    			HttpPost httppost = new HttpPost("http://192.168.1.3:8080/arc-server-v2/loginMobile");

    			Vector<BasicNameValuePair> l = new Vector<BasicNameValuePair>();
    			//Añadimos todos los parámetros que queramos enviar
    			//En este caso usuario y contraseña
    			//String test = editAlias.getText().toString();
    			l.add(new BasicNameValuePair("alias",editAlias.getText().toString()));
    			//l.add(new BasicNameValuePair(“password”, password));
        		
    	    			
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
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
