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

import com.example.arc.RegistroActivity.MyAsyncTask;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class TablonActivity extends Activity {
	
	RatingBar ratingBar;
	ImageButton imageButton;
	Button buttonComentarios;
	Button buttonPuntua;
	LinearLayout layout;
	ScrollView sv;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tablon_activity);
		
		ratingBar = (RatingBar)findViewById(R.id.ratingBar1);
		buttonComentarios = (Button)findViewById(R.id.button1);
		buttonPuntua =(Button)findViewById(R.id.button2);
		imageButton = (ImageButton)findViewById(R.id.imageButton1);
		layout = (LinearLayout)findViewById(R.id.messageLayout);
		sv = (ScrollView) findViewById(R.id.scrollView1);
    	new GetComments().execute();
    	sendScroll();
    	 
		this.buttonPuntua.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	
            	new SendRate().execute();//lanzo el hilo         
            }
      });
	}
		class SendRate extends AsyncTask<String, Integer, String> {
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
	    		Toast toast;
	    		String message;
	    		/*If the server says ok muestro un toast*/
	    		
	    		if(result.equals("ok")){
		    		message = "Puntuación enviada correctamente";
	    		}else{
	    			message = result;
	    		}
	    		toast = Toast.makeText(context, "Puntuación enviada correctamente" , duration);
	    		toast.show();
	    		/*DESABILITO EL rating bar*/
	    		ratingBar.setEnabled(false);
	    		buttonPuntua.setEnabled(false);
	    		
	    	}

	protected String doInBackground(String... parameter) {
				int myProgress = 0;
		// 	[... Perform background processing task, update myProgress ...]
				publishProgress(myProgress);
		// 		[... Continue performing background processing task ...]
		// 	Return the value to be passed to onPostExecute

				HttpClient httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost("http://192.168.1.3:8080/arc-server-v3/sendRateMobile");

				Vector<BasicNameValuePair> l = new Vector<BasicNameValuePair>();
				//Añadimos todos los parámetros que queramos enviar
				
				l.add(new BasicNameValuePair("rate", ratingBar.getRating()+""));
				l.add(new BasicNameValuePair("tablon_id", 2+""));/*FIX*/

				
		    			
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
				}
		
				httpclient.getConnectionManager().shutdown();
		
				return res;
			}
		
		
		}
		
		
		class GetComments extends AsyncTask<String, Integer, String> {
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
	    		Toast toast;
	    		String message;
	    		
	    		
	    		for(int i = 0; i<30; i++){
        			TextView author = new TextView(context);
        			TextView text = new TextView(context);
        			
        			author.setText("test"+i);
        			author.setTextColor(Color.BLACK);
        			text.setText("Menuda pedazo de charla charla charla charla charla charla charla charla charla charla charla charla"+i);
        			author.setPadding(50, 0, 50, 0);
        			text.setPadding(50, 0, 50, 5);
        			text.setTextColor(Color.GRAY);
        			//t.setY(50*i);
        			
        			layout.addView(author);
        			layout.addView(text);
        		}
        		
	    		
	    		
	    	}

	protected String doInBackground(String... parameter) {
				int myProgress = 0;
		// 	[... Perform background processing task, update myProgress ...]
				publishProgress(myProgress);
		// 		[... Continue performing background processing task ...]
		// 	Return the value to be passed to onPostExecute

						
				return null;
			}
		
		
		}
	
		protected void onSaveInstanceState(Bundle savedInstanceState){	
			super.onSaveInstanceState(savedInstanceState);

			savedInstanceState.putBoolean("rateSelected",ratingBar.isEnabled());
			savedInstanceState.putFloat("rateValue", ratingBar.getRating());
		}
		
		protected void onRestoreInstanceState(Bundle savedInstanceState){
			super.onRestoreInstanceState(savedInstanceState);
			
			ratingBar.setRating(savedInstanceState.getFloat("rateValue"));
			if(savedInstanceState.getBoolean("rateSelected") == false){
				ratingBar.setEnabled(false);
				buttonPuntua.setEnabled(false);
			}	
		}
		
	
		private void sendScroll(){
	        final Handler handler = new Handler();
	        new Thread(new Runnable() {
	            @Override
	            public void run() {
	                try {Thread.sleep(100);} catch (InterruptedException e) {}
	                handler.post(new Runnable() {
	                    @Override
	                    public void run() {
	                        sv.fullScroll(View.FOCUS_DOWN);
	                    }
	                });
	            }
	        }).start();
	    }
		
		
}
