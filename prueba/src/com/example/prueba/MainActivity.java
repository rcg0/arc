package com.example.prueba;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	Button button;
	TextView textView;
	Intent intent2;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        textView = (TextView)findViewById(R.id.textView1); 
        button = (Button)findViewById(R.id.button1);
    	

     

        this.button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	//button.setText("He pulsado");
            	Intent intent = new Intent("com.google.zxing.client.android.SCAN");
        		intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
        		startActivityForResult(intent, 0);
         
            }
      });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }


/*Obtener los resultados desde la misma actividad*/
public void onActivityResult(int requestCode, int resultCode, Intent intent) {
	   if (requestCode == 0) {
	      if (resultCode == RESULT_OK) {
	         String contents = intent.getStringExtra("SCAN_RESULT");
	         String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
	         textView.setText(contents);
	         //textView.append(format);
	         // Handle successful scan
	         
	         /*para pruebas del tablón*/

	         intent2= new Intent(this, TablonActivity.class);
	         startActivity(intent2);
	         
	      } else if (resultCode == RESULT_CANCELED) {
	         // Handle cancel
	      }
	   }
	}


}

