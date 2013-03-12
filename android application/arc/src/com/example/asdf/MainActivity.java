package com.example.asdf;

import java.io.BufferedReader;
import java.io.File;
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
import android.os.Environment;
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



public class MainActivity extends Activity {

	Button buttonRegistrate;
	Button buttonAccede;
	TextView textAlias;
	EditText editAlias;
	Intent intent2;
		
	MyDefaultHttpClient myDefaultHttp;
	HttpClient httpclient = null;
	
	String user = null; //json user, it's important because other activity needs to know who is login in.
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		 File testDirectory = new File(Environment.getExternalStorageDirectory()+"/ARC/");
	     if(!testDirectory.exists()){
	       	testDirectory.mkdir();
	     }
		
		buttonAccede = (Button)findViewById(R.id.button1);
		buttonRegistrate = (Button)findViewById(R.id.button2);
	    
		textAlias = (TextView)findViewById(R.id.TextView01);
		editAlias = (EditText)findViewById(R.id.editText2);
		
		 myDefaultHttp = ((MyDefaultHttpClient)getApplicationContext());
         httpclient = myDefaultHttp.getHttpClient();
		
        
        if(getIntent().getExtras() != null){
        	user = getIntent().getExtras().getString("jsonUser");
        	callCamera();
        }
         
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
            	
            	AsyncTask<String,Integer,String> login = new Login(MainActivity.this);
            	login.execute();
            }
      });
        
        
        
	}
        
        
    
    void callCamera() {
        	Intent intent = new Intent("com.google.zxing.client.android.SCAN");
    		intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
    		startActivityForResult(intent, 0);				
   	}
        
    protected void onSaveInstanceState(Bundle bundle){
      	/*bundle.putBoolean("sessionOpen", sessionOpen);
        Context context = getApplicationContext();
   	 	int duration = Toast.LENGTH_SHORT;
   	 	Toast toast = Toast.makeText(context, "onpause", duration);
   	 	toast.show();*/
		Log.i("javi","onSaveInstanceState");

    }
	
	/*Obtener los resultados desde la misma actividad*/
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		   if (requestCode == 0) {
		      if (resultCode == RESULT_OK) {
		         String contents = intent.getStringExtra("SCAN_RESULT");
		         String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
		         
		         /*debug*/
		         /*Context context = getApplicationContext();
		    	 int duration = Toast.LENGTH_SHORT;
		    	 Toast toast = Toast.makeText(context, contents, duration);
		    	 toast.show();*/
		         /******/
		    	 
		    	 callTablonActivity(contents);
		    	 
		      } else if (resultCode == RESULT_CANCELED) {
		         // Handle cancel
		      }
		   }
		}


	
	private void callTablonActivity(String contents){
		intent2= new Intent(MainActivity.this, TablonActivity.class);
		intent2.putExtra("jsonUser", user);
		intent2.putExtra("qrdecodified", contents);
		startActivity(intent2);
		//finish();	
	}

}
