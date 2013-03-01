package com.example.asdf;

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
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;

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
	EditText trabajo;

	Intent intent;
	Intent intent2;
	
	MyDefaultHttpClient myDefaultHttp;
	HttpClient httpclient = null;
	
	String jsonUser = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registro);
		
		alias = (EditText)findViewById(R.id.editText1);
		buttonEntrar = (Button)findViewById(R.id.button1);
		generoFemenino = (CheckBox)findViewById(R.id.checkBox2);
		generoMasculino = (CheckBox)findViewById(R.id.checkBox1);
		edad = (Spinner)findViewById(R.id.spinner1);
		trabajo = (EditText)findViewById(R.id.Spinner01);
	
		myDefaultHttp = ((MyDefaultHttpClient)getApplicationContext());
        httpclient = myDefaultHttp.getHttpClient();
		
        if(getIntent().getExtras() != null){
        	jsonUser = getIntent().getExtras().getString("jsonUser");
        	callCamera();
        }
		
		this.buttonEntrar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	
            	if(alias.getText().toString().equals("")){
            		Toast toast = Toast.makeText(getApplicationContext(), "Es necesario que escribas un alias.", Toast.LENGTH_SHORT);
            		toast.show();
            	}
            	else{
            		AsyncTask<String, Integer, String> registro = new Registro(RegistroActivity.this);
            		registro.execute();
            	}
            }
      });
		
		
	}


	private void callTablonActivity(String contents){
		intent2= new Intent(RegistroActivity.this, TablonActivity.class);
		intent2.putExtra("jsonUser", jsonUser);
		intent2.putExtra("qrdecodified", contents);
		startActivity(intent2);
		//finish();	
	}
	
    void callCamera() {
    	Intent intent = new Intent("com.google.zxing.client.android.SCAN");
		intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
		startActivityForResult(intent, 0);				
	}
	
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
    	 	
    	       if (requestCode == 0) {
    	          if (resultCode == RESULT_OK) {    		  	
    	        	  String contents = intent.getStringExtra("SCAN_RESULT");
    	        	  String format = intent.getStringExtra("SCAN_RESULT_FORMAT");  	
    	        	  Context context = getApplicationContext();
    		  	
    	        	  /*int duration = Toast.LENGTH_SHORT;
    	          	  Toast toast = Toast.makeText(context, contents, duration);   		  	
    	        	  toast.show();
    	        	  */
    	        	  callTablonActivity(contents);
    	        	  
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
		savedInstanceState.putString("profesion",trabajo.getText().toString());
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
