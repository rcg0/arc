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
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SendMessageDialog extends DialogFragment {
	
	 EditText mEditText;
	 Button sendButton;
	 
	 int tablonId;
	 
	MyDefaultHttpClient myDefaultHttp;
	HttpClient httpclient = null;
	 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_send_message, container);
        mEditText = (EditText) view.findViewById(R.id.txt_msg);
        sendButton = (Button) view.findViewById(R.id.aceptar);
        
        myDefaultHttp = ((MyDefaultHttpClient)getActivity().getApplicationContext());

        httpclient = myDefaultHttp.getHttpClient();
        
        this.sendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	
            	new SendMessage().execute();
            	
            }
      });
        
        return view;
	}
    
    @Override
    public void setArguments(Bundle args) {
     super.setArguments(args);
     this.tablonId = args.getInt("tablonId");
     
    }
    
    class SendMessage extends AsyncTask<String, Integer, String> {
    	@Override
    	protected void onProgressUpdate(Integer... progress) {
// [... Update progress bar, Notification, or other UI element ...]
   	}	
    	@SuppressLint({ "NewApi", "NewApi", "NewApi" }) //ojo con esto
		@Override
    	protected void onPostExecute(String result) {
    		
    		//If the server says ok muestro un toast
    		Context context = getActivity().getApplicationContext();
    		int duration = Toast.LENGTH_SHORT;
    		Toast toast;
    		String message;
    		message = result;
    		toast = Toast.makeText(context, message, duration);

    		toast.show();
    	}

   protected String doInBackground(String... parameter) {
			int myProgress = 0;
	// 	[... Perform background processing task, update myProgress ...]
			publishProgress(myProgress);
	// 		[... Continue performing background processing task ...]
	// 	Return the value to be passed to onPostExecute

			

			HttpPost httppost = new HttpPost("http://192.168.1.3:8080/arc-server-v3/sendMessageMobile");

			Vector<BasicNameValuePair> l = new Vector<BasicNameValuePair>();
			//Añadimos todos los parámetros que queramos enviar
			
			l.add(new BasicNameValuePair("tablonId", tablonId+""));
			l.add(new BasicNameValuePair("message", mEditText.getText().toString()));

			
	    			
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
	
			//httpclient.getConnectionManager().shutdown();
	
			return res;
		}
	
	
	}
    
    
}
