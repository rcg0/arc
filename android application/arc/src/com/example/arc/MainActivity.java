package com.example.arc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import android.os.AsyncTask;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
;



public class MainActivity extends Activity {

	Button buttonRegistrate;
	Button buttonAccede;
	TextView textAlias;
	EditText editAlias;
	Intent intent2;
	
	Boolean sessionOpen = false;
	
	MyDefaultHttpClient myDefaultHttp;
	HttpClient httpclient = null;
	
	String user = null; //json user, it's important because other activity needs to know who is login in.
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		buttonAccede = (Button)findViewById(R.id.button1);
		buttonRegistrate = (Button)findViewById(R.id.button2);
	    
		textAlias = (TextView)findViewById(R.id.TextView01);
		editAlias = (EditText)findViewById(R.id.editText2);
		
		 myDefaultHttp = ((MyDefaultHttpClient)getApplicationContext());
         httpclient = myDefaultHttp.getHttpClient();
		
		Log.i("javi","Oncreate");
		
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
        		
        		/*If user is found We access to the camera*/
        		if(!result.equals("nok")){
            		Gson gson = new Gson();
            		User userLogin = gson.fromJson(result, User.class);
            		user = result;
        			sessionOpen = true;
        			Intent intent = new Intent("com.google.zxing.client.android.SCAN");
            		intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
            		startActivityForResult(intent, 0);
        		}else{
        			Context context = getApplicationContext();
            		int duration = Toast.LENGTH_SHORT;
            		Toast toast = Toast.makeText(context, "El usuario no existe.", duration);
            		toast.show();
        		}
        		
        		
        	}

    protected String doInBackground(String... parameter) {
    			int myProgress = 0;
    	// 	[... Perform background processing task, update myProgress ...]
    			publishProgress(myProgress);
    	// 		[... Continue performing background processing task ...]
    	// 	Return the value to be passed to onPostExecute

    			
    			//HttpPost httppost = new HttpPost("http://bruckner.gast.it.uc3m.es:8080/arc-server-v3/loginMobile");
    			HttpPost httppost = new HttpPost("http://192.168.1.3:8080/arc-server-v3/loginMobile");
    			
    			Vector<BasicNameValuePair> l = new Vector<BasicNameValuePair>();
    			//Añadimos todos los parámetros que queramos enviar
    			l.add(new BasicNameValuePair("alias",editAlias.getText().toString()));
        		
    	    			
    			HttpResponse response = null;
    			HttpEntity resEntity = null;
    			String res = null;
    			try {
    				UrlEncodedFormEntity data = new UrlEncodedFormEntity(l, "utf-8");
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
    	
    			//httpclient.getConnectionManager().shutdown();
    	
    			return res;
    		}
        
        
    }
     
        
    protected void onSaveInstanceState(Bundle bundle){
      	/*bundle.putBoolean("sessionOpen", sessionOpen);
        Context context = getApplicationContext();
   	 	int duration = Toast.LENGTH_SHORT;
   	 	Toast toast = Toast.makeText(context, "onpause", duration);
   	 	toast.show();*/
		Log.i("javi","onSaveInstanceState");

    }
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
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


		    	 intent2= new Intent(MainActivity.this, TablonActivity.class);
		    	 intent2.putExtra("jsonUser", user);
		         startActivity(intent2);
		         
		      } else if (resultCode == RESULT_CANCELED) {
		         // Handle cancel
		      }
		   }
		}

}
