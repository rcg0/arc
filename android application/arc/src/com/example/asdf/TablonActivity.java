package com.example.asdf;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Vector;

import org.apache.http.client.HttpClient;

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
import com.example.asdf.Message;
import com.example.asdf.MyDefaultHttpClient;
import com.example.asdf.R;
import com.example.asdf.Tablon;
import com.google.gson.Gson;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.actionbarsherlock.view.ActionMode;


@SuppressLint("NewApi")
public class TablonActivity extends SherlockFragmentActivity{
	
    ActionMode mMode;

	
	Context context = this;
	
	TextView tablonSubtitle;
	public TabListener listener;
	public ActionBar actionBar;
	RatingBar ratingBar;
	RelativeLayout mainLayout;
	LinearLayout messagesLayout;
	ScrollView sv;
	
	public Vector<Tablon> tablones;
	
	Tablon tablon;
	Message messageSended;
	String higherMessageId;
	
	MyDefaultHttpClient myDefaultHttp;
	public HttpClient httpclient = null;
	
	ProgressDialog firstDialog = null;
	
	String qrdecodified = null;//format space,predefinedMessage
	public String space = null;
	public String predefinedMessage = "";
	
	String jsonUser = null;

	Tablon tablonSelected = null;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        
        setContentView(R.layout.activity_tablon);
        
        firstDialog = new ProgressDialog(this);
        firstDialog.setMessage("Cargando...");
        firstDialog.setCanceledOnTouchOutside(false);//by touch outside
        firstDialog.setCancelable(false);//by back key
        
        actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(false);
        setSupportProgressBarIndeterminateVisibility(false);
        
        ratingBar = new RatingBar(context);

        listener = new TabListener() {
			@Override
			public void onTabSelected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub
    			tablonSelected = tablones.elementAt(tab.getPosition());
    			tablonSelected.printTablon(TablonActivity.this);
    			tablonSelected.sendScroll(TablonActivity.this);
    			invalidateOptionsMenu();

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
		
		mainLayout = (RelativeLayout)findViewById(R.id.mainLayout);
		tablonSubtitle = (TextView)findViewById(R.id.subtitle);
		messagesLayout = (LinearLayout)findViewById(R.id.messageLayout);
		sv = (ScrollView) findViewById(R.id.scrollView1);
    	
		
		
    	jsonUser = getIntent().getStringExtra("jsonUser");
    	
		myDefaultHttp = ((MyDefaultHttpClient)getApplicationContext());
        httpclient = myDefaultHttp.getHttpClient();
		
        
        qrdecodified = getIntent().getStringExtra("qrdecodified");
    	String [] qr = qrdecodified.split(",");
    	space = qr[0];
    	if(qr.length == 2){
    		predefinedMessage = qr[1];
    	}

    	
    	
    	AsyncTask<String, Integer, String> getTablon = new GetTablon(this, firstDialog);
    	getTablon.execute();

    }    

    private final class ActionModeToSendMessage implements ActionMode.Callback {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {

            menu.add("EditText").setActionView(R.layout.collapsible_edittext).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
            
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            //Toast.makeText(ActionModes.this, "Got click: " + item, Toast.LENGTH_SHORT).show();
            mode.finish();
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
        }
    }

    @Override
   public boolean onPrepareOptionsMenu(Menu menu){
	
       if(tablonSelected != null){//nullpointerException if not check that
    	   
    	   boolean operationBitExample;
    	   int permission = tablonSelected.getPermission();
    	   
    	   if(permission != -1){//-1 means all permissions.
    		   
    		   operationBitExample= ((permission & 0x000001) == 1);

    	       if(!operationBitExample){
    	    	   menu.getItem(0).setVisible(false);//update messages
    	       }
    	   
    	       operationBitExample = ((permission & 0x000002) == 0x00000002);
    	   
    	       if(!operationBitExample){
    	    	   menu.getItem(1).setVisible(false);
    	    	   menu.getItem(2).setVisible(false);
    	    	   menu.getItem(4).setVisible(false);
    	    	   menu.getItem(5).setVisible(false);
    	    	   menu.getItem(6).setVisible(false);
    	       }
       		}
       }
       
    	
	   
	   return true;
	   
   }
    
    @Override
 public boolean onCreateOptionsMenu(Menu menu){  
    	
    	MenuInflater inflater = getSupportMenuInflater();
    	inflater.inflate(R.menu.activity_main, menu);
    	
    	return super.onCreateOptionsMenu(menu);
        
    }
    
    @SuppressLint("NewApi")
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

                mMode = startActionMode(new ActionModeToSendMessage());
                int doneButtonId = Resources.getSystem().getIdentifier("action_mode_close_button", "id", "android");
                EditText editTextSend = (EditText)findViewById(R.id.editTextSend);
                editTextSend.requestFocus();
                View doneButton = findViewById(doneButtonId);
                if(doneButton == null){
                	doneButton = findViewById(R.id.abs__action_mode_close_button); 
                }
                doneButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                    	String higherMessageId = tablonSelected.searchHighMessageId()+"";
                    	int format = 0;//texto
                    	EditText editText = (EditText)findViewById(R.id.editTextSend);
                    	String inputText = editText.getText().toString();

                		AsyncTask<String, Integer, String> sendMessage = new SendMessage(TablonActivity.this);
                		sendMessage.execute(inputText,format+"", higherMessageId);
                		
                		messageSended = new Message();

                		Gson gson = new Gson();
                		User user = gson.fromJson(jsonUser, User.class);
                		
                		messageSended.setCreator(user);
                		messageSended.setMsg(inputText);
                		
                		mMode.finish();
                    }
                });
            
            	return true;
            
            case R.id.new_picture:
            	
            	intent = new Intent(Intent.ACTION_GET_CONTENT);
            	intent.setType("image/* video/* audio/*");
            	startActivityForResult(intent, 1);
            	
            	return true;
                        
            case R.id.camera_access:

            	intent = new Intent("com.google.zxing.client.android.SCAN");
        		intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
        		startActivityForResult(intent, 0);		
            	
            	return true;
            
            case R.id.device_camera_access:
            	
            	intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            	startActivityForResult(intent, 4);  

            	return true;
            	
            case R.id.device_video_camera_access:
            	
            	intent = new Intent(android.provider.MediaStore.ACTION_VIDEO_CAPTURE);
            	startActivityForResult(intent, 5);  

            	return true;
            	
            case R.id.device_access_mic:
            	
            	intent = new Intent(TablonActivity.this, AudioRecorderActivity.class);
            	startActivityForResult(intent, 6);

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
				FileHelper fileHelper = new FileHelper();
							    
				Uri targetUri = intent.getData();
				
				String format = fileHelper.getFormatFromUri(targetUri);
				if(format.compareTo("") == 0){
					format = fileHelper.getFormatFromUriContainsIfContainsFormat(targetUri);
					
				}
				/**/
				File file = new File(getPath(targetUri));
				File fileDst = new File(Environment.getExternalStorageDirectory()+"/ARC/"+file.getName());
				try {
					fileHelper.copy(file, fileDst);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				/**/
				AsyncTask<Object, Integer, String> send = new SendMultiMedia(this);
				send.execute(file,format,tablonSelected.searchHighMessageId()+"");
			}
		}
		if(requestCode == 4){//capture photo
			if(resultCode == RESULT_OK){
			
				Bitmap bitmap = (Bitmap) intent.getExtras().get("data"); 

				FileHelper fileHelper = new FileHelper();
				ImageButton imageButton = new ImageButton(context);

				imageButton.setTag(Long.toHexString(Double.doubleToLongBits(Math.random())));//totally random
				
				fileHelper.saveBitmap(bitmap, imageButton);
		    
				String format = "1";//imagen
		
				File file = new File(Environment.getExternalStorageDirectory()+"/ARC/"+imageButton.getTag());
			
				AsyncTask<Object, Integer, String> send = new SendMultiMedia(this);
				send.execute(file,format,tablonSelected.searchHighMessageId()+"");
			
			}
		}
		if(requestCode == 5){//capture video
			if(resultCode == RESULT_OK){
		
			      Uri targetUri = intent.getData();
			      
			      FileHelper fileHelper = new FileHelper();
				    					
				  String format = fileHelper.getFormatFromUri(targetUri);
				  if(format.compareTo("") == 0){
					format = fileHelper.getFormatFromUriContainsIfContainsFormat(targetUri);
						
			   	  }
					
				  File file = new File(getPath(targetUri));
				  File fileDst = new File(Environment.getExternalStorageDirectory()+"/ARC/"+file.getName());
				  try {
						fileHelper.copy(file, fileDst);
				  } catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					/**/
					AsyncTask<Object, Integer, String> send = new SendMultiMedia(this);
					send.execute(file,format,tablonSelected.searchHighMessageId()+"");
			
				}
			
		}
		if(requestCode == 6){//audio recorder
			
			if(resultCode == RESULT_OK){
				
				File file = new File(intent.getStringExtra("recordName"));//incluye la ruta
				String format = "2" ;
				AsyncTask<Object, Integer, String> send = new SendMultiMedia(this);
				send.execute(file,format,tablonSelected.searchHighMessageId()+"");
				
			}
		}
	}
	


	
    /*returns the path of the Uri you pass*/
	public String getPath(Uri uri) {
	    String[] projection = { MediaStore.Images.Media.DATA };
	    Cursor cursor = managedQuery(uri, projection, null, null, null);
	    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
	    cursor.moveToFirst();
	    return cursor.getString(column_index);
	}
	
    
    
    
	protected void onSaveInstanceState(Bundle savedInstanceState){	
		super.onSaveInstanceState(savedInstanceState);

		if(ratingBar != null){
			savedInstanceState.putFloat("rateValue", ratingBar.getRating());
		}
		savedInstanceState.putString("jsonUser", jsonUser);
	}
	
	protected void onRestoreInstanceState(Bundle savedInstanceState){
		super.onRestoreInstanceState(savedInstanceState);
		
		if(ratingBar != null){
			ratingBar.setRating(savedInstanceState.getFloat("rateValue"));
		}
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

	
	
	public void printBar(ActionBar actionBar,TabListener listener){
		
		for(int i=0;i<tablones.size();i++){
		
			actionBar.addTab(actionBar.newTab().
					setText(tablones.elementAt(i).getName()).
					setContentDescription(tablones.elementAt(i).getSubtitle()).
					setTabListener(listener));
		}
		
	}
	
}