package com.example.prueba;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Vector;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class TablonActivity extends Activity {

	EditText text;
	TextView tv;
	Button sendButton;
	Vector<TextView> vectorTv;
	Gson gson;
	String json;
	Tablon tablon;
	int numeroMensajes;//variable que cuenta el numero de mensajes para luego determinar el tamaño del array de textviews
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablon);
        text = (EditText)findViewById(R.id.editText1);
        sendButton = (Button)findViewById(R.id.button1);
        tv = new TextView(this);
    	new MyAsyncTask().execute();//lanzo el hilo
    	
        
        this.sendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	//tv.setText(text.getText().toString());
            	//new MyAsyncTask().execute();
            	//tv.setText(json);
            }
      });
    }
        class MyAsyncTask extends AsyncTask<String, Integer, String> {
        	@Override
        	protected void onProgressUpdate(Integer... progress) {
    // [... Update progress bar, Notification, or other UI element ...]
       	}	
        	@Override
        	protected void onPostExecute(String result) {
    // [... Report results via UI update, Dialog, or notification ...]
        		/*En principio estaba puesto que soltara un toast*/
        		Context context = getApplicationContext();
        		int duration = Toast.LENGTH_SHORT;
        		Toast toast = Toast.makeText(context, result, duration);
        		toast.show();
        		
        		
        		Gson gson = new Gson();
        		Tablon tablonRecibido = gson.fromJson(result, Tablon.class);
        		numeroMensajes = tablonRecibido.getMsg().size();
        		vectorTv = new Vector<TextView>(numeroMensajes);
        		
        		for(int i = 0; i<numeroMensajes; i++){
        			TextView t = vectorTv.elementAt(i);
        			t.setText((CharSequence) tablon.getMsg().elementAt(i));
        		}
        		

        	}

    @Override
    		protected String doInBackground(String... parameter) {
    			int myProgress = 0;
    	// 	[... Perform background processing task, update myProgress ...]
    			publishProgress(myProgress);
    	// 		[... Continue performing background processing task ...]
    	// 	Return the value to be passed to onPostExecute

    			HttpClient httpclient = new DefaultHttpClient();
    			HttpPost httppost = new HttpPost("http://163.117.141.70:8080/prueba/ejemplo1");

    			Vector<BasicNameValuePair> l = new Vector<BasicNameValuePair>();
    			//Añadimos todos los parámetros que queramos enviar
    			//En este caso usuario y contraseña
    			//l.add(new BasicNameValuePair("tablon",json));
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
    				tv.setText("No ha funcionado");
    			}
    	
    			httpclient.getConnectionManager().shutdown();
    	
    			return res;
    		}
            
            
        }	

        
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_tablon, menu);
        return true;
    }
    

}
