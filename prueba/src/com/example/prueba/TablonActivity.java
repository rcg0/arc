package com.example.prueba;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class TablonActivity extends Activity {

	EditText text;
	Button sendButton;
	TextView tv;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablon);
        text = (EditText)findViewById(R.id.editText1);
        sendButton = (Button)findViewById(R.id.button1);
        tv = (TextView)findViewById(R.id.textView1);

        this.sendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	tv.setText(text.getText().toString());
            }
      });
        
		

        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_tablon, menu);
        return true;
    }
    

}
