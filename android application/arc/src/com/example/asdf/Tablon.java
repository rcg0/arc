package com.example.asdf;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.Vector;
import java.util.regex.Pattern;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;



public class Tablon {

	private int id;
	private String spaceId;
	private String name;
	private String subtitle;
	private Vector <User> users = new Vector<User>();
	private int permission;//?
	private int visibility;
	private Vector <User> targetUsers = new Vector<User>();
	private Vector <Message> msg = new Vector<Message>();
	private String rate;

	public void setId(int id){
		
		this.id = id;
		
	}
	
	public int getId (){
		
		return id;
	}

	public void setSpaceId(String spaceId){
		
		this.spaceId = spaceId;
		
	}
	
	public String getSpaceId (){
		
		return spaceId;
	}

	public void setName(String name){
		
		this.name=name;
		
	}
	
	public String getName(){
		
		return name;
	}
	
	public void setSubtitle(String subtitle){
		
		this.subtitle = subtitle;
		
	}
	
	public String getSubtitle (){
		
		return subtitle;
	}

	public void setRate(String rate){
		
		this.rate=rate;
		
	}
	
	public String getRate(){
		
		return rate;
	}
	
	public void setAllUsers(Vector<User> users){
		
		this.users = users; 
		
	}
	
	public void setUser(User user){
		
		users.addElement(user);
		
	}

	public Vector<User> getUsers(){
	  
	    return users;
      
	}

	public void setPermission(int permission){
		
		this.permission = permission;
		
	}
	
	public int getPermission (){
		
		return permission;
	}

	public void setVisibility(int visibility){
		
		this.visibility = visibility;
		
	}
	
	public int getVisibility (){
		
		return visibility;
	}
	
	public void setAllTargetUser(Vector<User> u){
		
		targetUsers = u;
		
		
	}

	public void setTargetUser(User user){
		
		targetUsers.addElement(user);
		
	}
	
	public Vector<User> getTargetUsers(){		
		
		return 	targetUsers;
		
	}

	public Vector<Message> getAllMsg(){		
		
		return 	msg;
		
	}
	
	public void setAllMsg(Vector<Message> m){
		
		msg=m;
		
	}
	
	public void setMsg(Message mensaje){

		msg.addElement(mensaje);
	}
	
	public void setSomeMsg(Vector<Message> m){
		for(int i = 0; i<m.size(); i++){
			this.setMsg(m.elementAt(i));
		}
	}
	
	public void printTablon(TablonActivity tablonActivity){
		
		Vector<Message> messages = this.getAllMsg();
		
		tablonActivity.mainLayout.removeView(tablonActivity.ratingBar);		
		tablonActivity.messagesLayout.removeAllViews();




		//tablonSubtitle.setText(this.getSubtitle());
		if(this.rate != null){//rate is an configurable element by tablon
			printRate(tablonActivity.ratingBar, tablonActivity);
		}
	
		
		printSomeMessages(messages, tablonActivity.messagesLayout, tablonActivity.context, tablonActivity);
		
	}
	
	public void printSomeMessages(Vector<Message> messages, LinearLayout layout,Context context,  TablonActivity tablonActivity){
		
		for(int i = 0; i<messages.size(); i++){	
			this.printMessage(messages.elementAt(i),layout,context, tablonActivity);
		}
	}
	
	public void printMessage(Message message, LinearLayout layout, Context context,  final TablonActivity tablonActivity){
		
		LinearLayout l = new LinearLayout(context);
		l.setOrientation(LinearLayout.HORIZONTAL);			
		TextView author = new TextView(context);
		TextView text = new TextView(context);

		final ImageButton imageButton = new ImageButton(context);
		final Button button2 = new Button(context);
		final Button button1 = new Button(context);
		
		int lastIndex = message.getMsg().lastIndexOf("/");
		
		if(message.getFormat() == 0){//texto
			text.setText(message.getMsg());
			text.setTextColor(Color.BLACK);
			
		}else if(message.getFormat() == 1){//image
			
			String name = message.getMsg().substring(lastIndex+1);//el nombre del archivo, necesito ruta + nombre del archivo para confeccionar el link que lleve al archivo
			imageButton.setImageResource(R.drawable.arc_logo);
			
			//File file = new File(context.getExternalFilesDir(null), name);
			//File file = new File(Environment.getExternalStorageDirectory()+"/ARC/"+name);
			
			String path = Environment.getExternalStorageDirectory()+File.separator+"ARC"+File.separator+name;
			
			
			final File imgFile = new File(path);
			
			if(imgFile.exists()){
		
				final Uri uri;
				String uriString = null;
				
				try {
					uriString = android.provider.MediaStore.Images.Media.insertImage(tablonActivity.getContentResolver(),	imgFile.getAbsolutePath(), null, null);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				uri = Uri.parse(uriString);
				
			    FileInputStream in;
				
			    try {
					in = new FileInputStream(imgFile);

				     // Note that the buffer size is in bytes, so 24 in your question is very low
				     BufferedInputStream buf = new BufferedInputStream(in, 8192);
				     Bitmap bMap = BitmapFactory.decodeStream(buf);
				     Bitmap resizedBitmap = Bitmap.createScaledBitmap(bMap, 96, 96, false);
				     imageButton.setImageBitmap(resizedBitmap);
   					 imageButton.setTag(name);//si no tiene tag no se pinta
   					 
   					imageButton.setOnClickListener(new View.OnClickListener() {
   				        public void onClick(View v) {

   				        	Intent intent = new Intent(Intent.ACTION_VIEW, uri);
   				        	tablonActivity.context.startActivity(intent);

   				        }
   			   	 		});

				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	
			}else {
				
		   	 	AsyncTask<ImageButton, Void, Bitmap> getImage = new GetImage(tablonActivity);
				imageButton.setTag(name);
				getImage.execute(imageButton);	
			}
			
		}else if(message.getFormat() == 2){//audio
			
			String name = message.getMsg().substring(lastIndex+1);//el nombre del archivo, necesito ruta + nombre del archivo para confeccinar el link que lleve al archivo

	   		File fileDirectory = new File(Environment.getExternalStorageDirectory()+"/ARC/");
	   		
	   		File existFile = new File(Environment.getExternalStorageDirectory()+"/ARC/"+name);
	   		
	   		if(existFile.exists()){
	   			button2.setText("Escuchar clip de audio: "+name);
				button2.setTag(name);

				button2.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
    		        	
    		        	Intent intent = new Intent();	     
    		        	File file = new File(Environment.getExternalStorageDirectory()+"/ARC/"+button2.getTag());
    		        	intent.setDataAndType(Uri.fromFile(file), "audio/*");
    		        	intent.setAction(Intent.ACTION_VIEW);
    		        	tablonActivity.startActivity(intent);
    		        }
    		    });
	   		}else{
	   			button2.setText("Descargar clip de audio: "+ name);
    			button2.setTag(name);
    			button2.setOnClickListener(new View.OnClickListener() {
    		        public void onClick(View v) {

    		        	AsyncTask<Object, Void, Void> getAudio = new GetAudio(tablonActivity);
    					getAudio.execute(button2);	
    					
    		        }
    		    });
	   			
	   		}
	   		
			
		}else if(message.getFormat() == 3){//video
			
			String name = message.getMsg().substring(lastIndex+1);//el nombre del archivo, necesito ruta + nombre del archivo
		
			button2.setTag(name);
			imageButton.setTag(name);		
			imageButton.setVisibility(4);//INVISIBLE
			

	   		File fileDirectory = new File(Environment.getExternalStorageDirectory()+"/ARC/");
	        
	   		File fileExists = new File(Environment.getExternalStorageDirectory()+"/ARC/"+name);
	   		
	   		if(fileExists.exists()){
	   			Bitmap bMap = ThumbnailUtils.createVideoThumbnail(fileExists.getAbsolutePath(), MediaStore.Video.Thumbnails.MICRO_KIND);//null

		    	button2.setVisibility(4);//INVISIBLE
		    	imageButton.setVisibility(0);//VISIBLE
		    	/**/
		    	ImageHelper imageHelper = new ImageHelper();
		    	
		    	Bitmap bMapPlay = BitmapFactory.decodeResource(tablonActivity.getResources(), R.drawable.play);
		    	
		    	Bitmap videoBitmap = imageHelper.overlay(bMap, bMapPlay);
		    	
	    		imageButton.setImageBitmap(videoBitmap);
	    		/**/
				imageButton.setOnClickListener(new View.OnClickListener() {
    		        public void onClick(View v) {
    		        	
    		        	Intent intent = new Intent();	     
    		        	File file = new File(Environment.getExternalStorageDirectory()+"/ARC/"+imageButton.getTag());
    		        	intent.setDataAndType(Uri.fromFile(file), "video/*");
    		        	intent.setAction(Intent.ACTION_VIEW);
    		        	tablonActivity.startActivity(intent);
    		        
    		        }
    		    });
				
	   		}else{
	   			button2.setText("Descargar video: "+ name);
    			
    			button2.setOnClickListener(new View.OnClickListener() {
    		        public void onClick(View v) {
    		        	
    		        	AsyncTask<Object, Void, Void> getVideo = new GetVideo(tablonActivity);
    					getVideo.execute(button2, imageButton);	
    		        
    		        }
    		    });
	   		}
	   					
		}
		
		author.setText(message.getCreator().getNick()+ ": ");
		author.setPadding(30, 0, 0, 0);
		author.setTypeface(null, Typeface.BOLD);
		author.setTextColor(Color.BLACK);
		
		l.addView(author);
		l.addView(text);
		
		if(imageButton.getTag() != null){
			l.addView(imageButton);
		}
		if(button2.getTag() != null){
			l.addView(button2);
		}
		
		
		layout.addView(l);
		
	}
	
	
		
	/*returns the highmessageid or -1 if there isn't messages*/
	public int searchHighMessageId(){
		
		int result=-1;
		
		if(this.msg.size() > 0){
			result = this.msg.elementAt(msg.size()-1).getId();
		}
		
		return result; 
	}
	
	public void printRate(RatingBar ratingBar, final TablonActivity tablonActivity){

		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.CENTER_HORIZONTAL);
		
		ratingBar.setId(R.id.ratingBar1);
		tablonActivity.mainLayout.addView(ratingBar, params);

		

		ratingBar.setOnTouchListener(new OnTouchListener() {
			
			public boolean onTouch(View v, MotionEvent event) {

		        if (event.getAction() == MotionEvent.ACTION_UP) {
		        	AsyncTask<String, Integer, String> sendRate =new SendRate(tablonActivity);//lanzo el hilo
		        	sendRate.execute();
		        }
				return false;
			}
		});
		
		float rate = Float.parseFloat(this.rate);
		ratingBar.setRating(rate);
		
	}
	
	public void printRate(RatingBar ratingBar, float rate){
		ratingBar.setRating(rate);
	}
	

	public void sendScroll(final TablonActivity tablonActivity){
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {Thread.sleep(130);} catch (InterruptedException e) {}
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        tablonActivity.sv.fullScroll(View.FOCUS_DOWN);
                    }
                });
            }
        }).start();
    }
	
}
