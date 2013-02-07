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
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.ActionBar.TabListener;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.example.asdf.TablonActivity.GetMoreMessages;
import com.example.asdf.MainActivity;
import com.example.asdf.TablonActivity;
import com.example.asdf.User;
import com.example.asdf.TablonActivity.SendMessage;
import com.example.asdf.SendMessageDialog;
import com.example.asdf.SendMessageDialog.SendMessageDialogListener;
import com.example.asdf.TablonActivity.GetTablon;
import com.example.asdf.Message;
import com.example.asdf.MyDefaultHttpClient;
import com.example.asdf.R;
import com.example.asdf.Tablon;
import com.example.asdf.TablonActivity.SendRate;
import com.google.gson.Gson;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class TablonActivity extends SherlockFragmentActivity implements SendMessageDialogListener{
	
	TextView tablonSubtitle;
	TabListener listener;
	ActionBar actionBar;
	RatingBar ratingBar;
	LinearLayout layout;
	ScrollView sv;
	Tablon tablon;
	Message messageSended;
	String higherMessageId;
	
	MyDefaultHttpClient myDefaultHttp;
	HttpClient httpclient = null;
	
	
	String qrdecodified = null;//format space,predefinedMessage
	String space = null;
	String predefinedMessage = "";
	
	String jsonUser = null;

	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablon);
        
        actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(false);
        
        listener = new TabListener() {
			
			
			@Override
			public void onTabSelected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub
            	//Toast.makeText(getApplicationContext(), "De momento nada...", Toast.LENGTH_LONG).show();

			}

			@Override
			public void onTabUnselected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTabReselected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub
				
			}
		};
		
		tablonSubtitle = (TextView)findViewById(R.id.subtitle);
		ratingBar = (RatingBar)findViewById(R.id.ratingBar1);
		layout = (LinearLayout)findViewById(R.id.messageLayout);
		sv = (ScrollView) findViewById(R.id.scrollView1);
    	
    	jsonUser = getIntent().getStringExtra("jsonUser");
    	
		myDefaultHttp = ((MyDefaultHttpClient)getApplicationContext());
        httpclient = myDefaultHttp.getHttpClient();
		
        this.ratingBar.setOnTouchListener(new OnTouchListener() {
			
			public boolean onTouch(View v, MotionEvent event) {

		        if (event.getAction() == MotionEvent.ACTION_UP) {
	            	new SendRate().execute();//lanzo el hilo         
		        }
				return false;
			}
		});
        
        qrdecodified = getIntent().getStringExtra("qrdecodified");
    	String [] qr = qrdecodified.split(",");
    	space = qr[0];
    	if(qr.length == 2){
    		predefinedMessage = qr[1];
    	}
    	new GetTablon().execute(); 

        
    }    

    
    
    @Override
 public boolean onCreateOptionsMenu(Menu menu){   // TODO Auto-generated method stub
    	MenuInflater inflater = getSupportMenuInflater();
    	inflater.inflate(R.menu.activity_main, menu);
    	
    	return super.onCreateOptionsMenu(menu);
        
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
      switch (item.getItemId()) {
            case R.id.menu_update:         

            	String higherMessageId = tablon.searchHighMessageId()+"";
            	new GetMoreMessages().execute(higherMessageId);
            	
            	return true;
            case R.id.menu_write:

            	showSendDialog(predefinedMessage);
            	
            	return true;
            case R.id.camera_access:
            	//Toast.makeText(getApplicationContext(), "De momento nada...", Toast.LENGTH_LONG).show();
            	Intent intent = new Intent("com.google.zxing.client.android.SCAN");
        		intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
        		startActivityForResult(intent, 0);		
            	
            	
            	return true;
            case R.id.logout:

            	new Logout().execute();
            	
            	return true;
            default:
                return super.onOptionsItemSelected(item);
            }  
        }
    
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
		    	 
		        Intent intent2= new Intent(TablonActivity.this, TablonActivity.class);
		 		intent2.putExtra("jsonUser", jsonUser);
		 		intent2.putExtra("qrdecodified", contents);
		 		startActivity(intent2);
			    finish();

		      } else if (resultCode == RESULT_CANCELED) {
		         // Handle cancel
		      }
		   }
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
    		String message = null;
    		/*If the server says ok muestro un toast*/
    		if(result == null){
    			message = "La puntuación no se pudo enviar en estos momentos";
    		}else{
	    		float media = Float.parseFloat(result);
    			
	    		if(media >= 0 || media<=5){
	    			tablon.printRate(ratingBar, media);
    				message = "La puntuación media del evento es de: "+media;
    			}
    		}
    		toast = Toast.makeText(context, message , duration);
    		toast.show();
    		
    		
    	}

protected String doInBackground(String... parameter) {
			int myProgress = 0;
	// 	[... Perform background processing task, update myProgress ...]
			publishProgress(myProgress);
	// 		[... Continue performing background processing task ...]
	// 	Return the value to be passed to onPostExecute

			HttpPost httppost = new HttpPost("http://bruckner.gast.it.uc3m.es:8080/arc-server-v3/sendRateMobile");
			
			Vector<BasicNameValuePair> l = new Vector<BasicNameValuePair>();
			//Añadimos todos los parámetros que queramos enviar
			
			l.add(new BasicNameValuePair("rate", ratingBar.getRating()+""));
			l.add(new BasicNameValuePair("spaceId", space));

	    			
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
			}
	
			//httpclient.getConnectionManager().shutdown();
	
			return res;
		}
    
    }
    class GetTablon extends AsyncTask<String, Integer, String> {
    	@Override
    	protected void onProgressUpdate(Integer... progress) {
// [... Update progress bar, Notification, or other UI element ...]
   	}	
    	@SuppressLint({ "NewApi", "NewApi", "NewApi" }) //ojo con esto
		@Override
    	protected void onPostExecute(String result) {
// [... Report results via UI update, Dialog, or notification ...]
    		/*En principio estaba puesto que soltara un toast*/
    		
    		if(result != null){
    			Context context = getApplicationContext();
    			Gson gson = new Gson();
/**/
    			
				tablon = gson.fromJson(result, Tablon.class);
				
    			tablon.printTablon(actionBar,listener, tablonSubtitle, ratingBar, layout, context);
    			sendScroll();
    		}
    		if(!predefinedMessage.equals("")){//predefinedMessage Exists
    			showSendDialog(predefinedMessage);
    			predefinedMessage = "";//lo reseteo para que no lo coja la siguiente vez
    		}
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
			l.add(new BasicNameValuePair("tablonSpace",space));
    		
	    			
			HttpResponse response = null;
			HttpEntity resEntity = null;
			String res = null;
			try {
				UrlEncodedFormEntity data = new UrlEncodedFormEntity(l, "utf-8");
				httppost.setEntity(data);
		
				response = httpclient.execute(httppost);
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
	
	private void showSendDialog(String predefinedMessage) {

		  FragmentManager fm = getSupportFragmentManager();
	      SendMessageDialog messageDialog = new SendMessageDialog();
	      Bundle args = new Bundle();
	      args.putString("predefinedMessage", predefinedMessage);
	      messageDialog.setArguments(args);
	  
	      messageDialog.show(fm, "fragment_edit_name");
	      
		
}

	class SendMessage extends AsyncTask<String, Integer, String> {
    	@Override
    	protected void onProgressUpdate(Integer... progress) {
// [... Update progress bar, Notification, or other UI element ...]
   	}	
    	@SuppressLint({ "NewApi", "NewApi", "NewApi" }) //ojo con esto
		@Override
    	protected void onPostExecute(String result) {
    		if (result != null){//no le ha llegado al servidor
    		/*este método hace lo mismo que el onpostexecute de getAfterMessages --> REFACTOR */
    			Context context = getApplicationContext();
    			Gson gson = new Gson();
    			Tablon tablonReceived = gson.fromJson(result, Tablon.class);
    			if(!tablonReceived.getAllMsg().isEmpty()){
    				tablon.printSomeMessages(tablonReceived.getAllMsg() ,layout ,context);	
    				tablon.setSomeMsg(tablonReceived.getAllMsg());
    				sendScroll();
    			}
    		}
    	}

   protected String doInBackground(String... parameter) {
			int myProgress = 0;
	// 	[... Perform background processing task, update myProgress ...]
			publishProgress(myProgress);
	// 		[... Continue performing background processing task ...]
	// 	Return the value to be passed to onPostExecute

			

			HttpPost httppost = new HttpPost("http://bruckner.gast.it.uc3m.es:8080/arc-server-v3/sendMessageMobile");

			Vector<BasicNameValuePair> l = new Vector<BasicNameValuePair>();
			//Añadimos todos los parámetros que queramos enviar
			
			l.add(new BasicNameValuePair("tablonId", tablon.getId()+""));
			l.add(new BasicNameValuePair("tablonSpace", tablon.getSpaceId()));
			l.add(new BasicNameValuePair("message", parameter[0] ));
			l.add(new BasicNameValuePair("messageId", parameter[1]));
			
			HttpResponse response = null;
			HttpEntity resEntity = null;
			String res = null;
			try {
				UrlEncodedFormEntity data = new UrlEncodedFormEntity(l,"utf-8");
				
				httppost.setEntity(data);
		
				response = httpclient.execute(httppost);
				Log.i("javi", "checkpoint1");
				resEntity = response.getEntity();
				BufferedReader b = new BufferedReader(new InputStreamReader(resEntity.getContent()));
				//Leeríamos la respuesta y haríamos algo con ella, en este caso únicamente leemos la primera linea
				res = b.readLine().trim();
				resEntity.consumeContent();
				Log.i("javi", "checkpoint2");
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	
			//httpclient.getConnectionManager().shutdown();
	
			return res;
		}
	
	
	}

	class GetMoreMessages extends AsyncTask<String, Integer, String> {
    	@Override
    	protected void onProgressUpdate(Integer... progress) {
// [... Update progress bar, Notification, or other UI element ...]
   	}	
    	@SuppressLint({ "NewApi", "NewApi", "NewApi" }) //ojo con esto
		@Override
    	protected void onPostExecute(String result) {
// [... Report results via UI update, Dialog, or notification ...]
    		/*En principio estaba puesto que soltara un toast*/
    		
    		if(result != null){
    			Context context = getApplicationContext();
    			Gson gson = new Gson();
    			Tablon tablonReceived = gson.fromJson(result, Tablon.class);
    			if(!tablonReceived.getAllMsg().isEmpty()){
    				tablon.printSomeMessages(tablonReceived.getAllMsg() ,layout ,context);	
    				tablon.setSomeMsg(tablonReceived.getAllMsg());
    				sendScroll();
    			}else{
					int duration = Toast.LENGTH_SHORT;
					Toast toast = Toast.makeText(context, "No hay más mensajes que mostrar.", duration);
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
			
			HttpPost httppost = new HttpPost("http://bruckner.gast.it.uc3m.es:8080/arc-server-v3/getAfterMessagesMobile");

			Vector<BasicNameValuePair> l = new Vector<BasicNameValuePair>();
			//Añadimos todos los parámetros que queramos enviar
			l.add(new BasicNameValuePair("tablonSpace",space));
			l.add(new BasicNameValuePair("messageId",parameter[0]));

	    			
			HttpResponse response = null;
			HttpEntity resEntity = null;
			String res = null;
			try {
				UrlEncodedFormEntity data = new UrlEncodedFormEntity(l, "utf-8");
				httppost.setEntity(data);
		
				response = httpclient.execute(httppost);
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
	
    class Logout extends AsyncTask<String, Integer, String> {
    	@Override
    	protected void onProgressUpdate(Integer... progress) {
// [... Update progress bar, Notification, or other UI element ...]
   	}	
    	@SuppressLint({ "NewApi", "NewApi", "NewApi" }) //ojo con esto
		@Override
    	protected void onPostExecute(String result) {
    		
    		Context context = getApplicationContext();
			int duration = Toast.LENGTH_SHORT;
			Toast toast;
			
    		if (result == null){//no le ha llegado al servidor
    			toast = Toast.makeText(context, "No ha sido posible conectar con el servidor.", duration);
				toast.show();
    		}
    		else if(result.equals("ok")){
    			toast = Toast.makeText(context, "Usuario desconectado.", duration);
				toast.show();
				Intent intent = new Intent(TablonActivity.this, MainActivity.class);
			    startActivity(intent);
			    finish();
    		}
    	}

   protected String doInBackground(String... parameter) {
			int myProgress = 0;
	// 	[... Perform background processing task, update myProgress ...]
			publishProgress(myProgress);
	// 		[... Continue performing background processing task ...]
	// 	Return the value to be passed to onPostExecute

			

			HttpPost httppost = new HttpPost("http://bruckner.gast.it.uc3m.es:8080/arc-server-v3/logoutMobile");

			Vector<BasicNameValuePair> l = new Vector<BasicNameValuePair>();
			//Añadimos todos los parámetros que queramos enviar
					
			HttpResponse response = null;
			HttpEntity resEntity = null;
			String res = null;
			try {
				UrlEncodedFormEntity data = new UrlEncodedFormEntity(l,"utf-8");
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
    
    
	protected void onSaveInstanceState(Bundle savedInstanceState){	
		super.onSaveInstanceState(savedInstanceState);

		savedInstanceState.putFloat("rateValue", ratingBar.getRating());
		savedInstanceState.putString("jsonUser", jsonUser);
	}
	
	protected void onRestoreInstanceState(Bundle savedInstanceState){
		super.onRestoreInstanceState(savedInstanceState);
		
		ratingBar.setRating(savedInstanceState.getFloat("rateValue"));
		jsonUser = savedInstanceState.getString("jsonUser");
		predefinedMessage = "";
	}

	@Override
	public void onBackPressed() {
	    super.onBackPressed();
	    Intent intent = new Intent(TablonActivity.this, MainActivity.class);
	    intent.putExtra("jsonUser", jsonUser);
	    startActivity(intent);
	}

	@Override
	public void onFinishEditDialog(String inputText) {

    	String higherMessageId = tablon.searchHighMessageId()+"";

		new SendMessage().execute(inputText, higherMessageId);
		Gson gson = new Gson();
		User user = gson.fromJson(jsonUser, User.class);
		String nick = user.getNick();
		
		messageSended = new Message();
		messageSended.setCreator(user);
		messageSended.setMsg(inputText);		
	}
}