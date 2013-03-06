package com.example.asdf;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
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
import com.actionbarsherlock.view.Window;
import com.example.asdf.MainActivity;
import com.example.asdf.TablonActivity;
import com.example.asdf.User;
import com.example.asdf.SendMessageDialog;
import com.example.asdf.SendMessageDialog.SendMessageDialogListener;
import com.example.asdf.Message;
import com.example.asdf.MyDefaultHttpClient;
import com.example.asdf.R;
import com.example.asdf.Tablon;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
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
	
	Context context = this;
	
	TextView tablonSubtitle;
	public TabListener listener;
	public ActionBar actionBar;
	RatingBar ratingBar;
	LinearLayout layout;
	ScrollView sv;
	public Vector<Tablon> tablones;
	
	Tablon tablon;
	Message messageSended;
	String higherMessageId;
	
	MyDefaultHttpClient myDefaultHttp;
	public HttpClient httpclient = null;
	
	
	String qrdecodified = null;//format space,predefinedMessage
	public String space = null;
	public String predefinedMessage = "";
	
	String jsonUser = null;

	Tablon tablonSelected = null;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_PROGRESS);
        setContentView(R.layout.activity_tablon);
        
        
        actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(false);
        
        listener = new TabListener() {
			@Override
			public void onTabSelected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub
    			tablonSelected = tablones.elementAt(tab.getPosition());
    			
    			tablonSelected.printTablon(tablonSubtitle, ratingBar, layout, context, TablonActivity.this);//getApplicationContext());
    			sendScroll();
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
		        	AsyncTask<String, Integer, String> sendRate =new SendRate(TablonActivity.this);//lanzo el hilo
		        	sendRate.execute();
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
    	
    	AsyncTask<String, Integer, String> getTablon = new GetTablon(this);
    	getTablon.execute();

        
    }    

    
    
    @Override
 public boolean onCreateOptionsMenu(Menu menu){   // TODO Auto-generated method stub
    	MenuInflater inflater = getSupportMenuInflater();
    	inflater.inflate(R.menu.activity_main, menu);
    	
    	return super.onCreateOptionsMenu(menu);
        
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	
    	Intent intent;
    	
      switch (item.getItemId()) {
            case R.id.menu_update:         

            	String higherMessageId = tablonSelected.searchHighMessageId()+"";
            	AsyncTask<String, Integer, String> getMoreMessages = new GetMoreMessages(this);
            	getMoreMessages.execute(higherMessageId);
            	
            	return true;
            	
            case R.id.menu_write:

            	showSendDialog(predefinedMessage);
            	
            	return true;
            
            case R.id.new_picture:
            	
            	//intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Media.EXTERNAL_CONTENT_URI);
            	intent = new Intent(Intent.ACTION_GET_CONTENT);
            	intent.setType("image/* video/* audio/*");
            	startActivityForResult(intent, 1);
            	
            	return true;
                        
            case R.id.camera_access:
            	//Toast.makeText(getApplicationContext(), "De momento nada...", Toast.LENGTH_LONG).show();
            	intent = new Intent("com.google.zxing.client.android.SCAN");
        		intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
        		startActivityForResult(intent, 0);		
            	
            	return true;
            	 
            case R.id.logout:

            	AsyncTask<String, Integer, String> logout = new Logout(this);
            	logout.execute();
            	
            	return true;
            	
            default:
                return super.onOptionsItemSelected(item);
            }  
        }
    
    
    
    
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		   
		if (requestCode == 0) {//camera
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
		else if(requestCode == 1){//multimedia
			
			if(resultCode == RESULT_OK){
				
							    
				Uri targetUri = intent.getData();
				String format = getFormatFromUri(targetUri);
				
				File file = new File(getPath(targetUri));
				
				AsyncTask<Object, Integer, String> send = new SendMultiMedia(this);
				send.execute(file,format,tablonSelected.searchHighMessageId()+"");
				
				/*Toast toast2 = Toast.makeText(getApplicationContext(), targetUri.toString() , Toast.LENGTH_SHORT);
    			toast2.show();
    			*/
			}
		}
		
	}
	
	public String getFormatFromUri(Uri uri){
		String format = "";
		String uriString = uri.toString();
		int lastIndex = uriString.lastIndexOf(".");
		String substring = uriString.substring(lastIndex+1);
		
		if(substring.equalsIgnoreCase("AAC") || substring.equalsIgnoreCase("MP3") 
				|| substring.equalsIgnoreCase("WMA") || substring.equalsIgnoreCase("WAV")
				|| substring.equalsIgnoreCase("MIDI")){
			format = "2";//audio
		}else if(substring.equalsIgnoreCase("JPEG") || substring.equalsIgnoreCase("PNG") 
				|| substring.equalsIgnoreCase("JPG") || substring.equalsIgnoreCase("BMP")
				|| substring.equalsIgnoreCase("GIF")){
			format = "1";//imagen
		}else if(substring.equalsIgnoreCase("avi") || substring.equalsIgnoreCase("mov") 
				|| substring.equalsIgnoreCase("3gp") || substring.equalsIgnoreCase("m4v")
				|| substring.equalsIgnoreCase("wmv")){
			format = "3";//video
		}
		
		return format;
		
	}
	
    /*returns the path of the Uri you pass*/
	public String getPath(Uri uri) {
	    String[] projection = { MediaStore.Images.Media.DATA };
	    Cursor cursor = managedQuery(uri, projection, null, null, null);
	    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
	    cursor.moveToFirst();
	    return cursor.getString(column_index);
	}
	
    
    
	

	public void sendScroll(){
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
	
	public void showSendDialog(String predefinedMessage) {

		  FragmentManager fm = getSupportFragmentManager();
	      SendMessageDialog messageDialog = new SendMessageDialog();
	      Bundle args = new Bundle();
	      args.putString("predefinedMessage", predefinedMessage);
	      messageDialog.setArguments(args);
	  
	      messageDialog.show(fm, "fragment_edit_name");
	      
		
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

    	String higherMessageId = tablonSelected.searchHighMessageId()+"";
    	int format = 0;//texto
    	
		AsyncTask<String, Integer, String> sendMessage = new SendMessage(this);
		sendMessage.execute(inputText,format+"", higherMessageId);
		
		messageSended = new Message();

		Gson gson = new Gson();
		User user = gson.fromJson(jsonUser, User.class);
		//String nick = user.getNick();
		
		messageSended.setCreator(user);
		messageSended.setMsg(inputText);		
	}
	
	public void printBar(ActionBar actionBar,TabListener listener){
		
		for(int i=0;i<tablones.size();i++){
		
			actionBar.addTab(actionBar.newTab().
					setText(tablones.elementAt(i).getName()).
					setContentDescription(tablones.elementAt(i).getSubtitle()).
					setTabListener(listener));
		}
		
	}
	
}