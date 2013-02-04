package com.example.asdf;

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
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

public class SendMessageDialog extends DialogFragment implements OnEditorActionListener{
	
	 EditText mEditText;
	 	 
	 MyDefaultHttpClient myDefaultHttp;
	 HttpClient httpclient = null;
	 
	 public interface SendMessageDialogListener {
	        void onFinishEditDialog(String inputText);
	 }
	 
	 public SendMessageDialog() {
	        // Empty constructor required for DialogFragment
	 }
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_send_message, container);
        
        Bundle bundle = getArguments();
        
        mEditText = (EditText) view.findViewById(R.id.txt_msg);
        
        mEditText.setText(bundle.getString("predefinedMessage"));
        // Show soft keyboard automatically
        mEditText.requestFocus();
        getDialog().getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        mEditText.setOnEditorActionListener(this);
        
       
        return view;
	}
    
    @Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
    	 if (EditorInfo.IME_ACTION_DONE == actionId) {
             // Return input text to activity
             SendMessageDialogListener activity = (SendMessageDialogListener) getActivity();
             activity.onFinishEditDialog(mEditText.getText().toString());
             this.dismiss();
             return true;
         }
         return false;
	}
    
    
}
