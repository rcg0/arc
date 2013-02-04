package com.example.asdf;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Application;

public class MyDefaultHttpClient extends Application {
    
	private HttpClient http = new DefaultHttpClient(); 
	
	public HttpClient getHttpClient(){
		
		return http;
		
	}
	
}
	

