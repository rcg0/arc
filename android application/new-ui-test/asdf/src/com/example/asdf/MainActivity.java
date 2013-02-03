package com.example.asdf;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.ActionBar.TabListener;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;

public class MainActivity extends SherlockActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        /*View customNav = LayoutInflater.from(this).inflate(R.layout.upper, null);
        getSupportActionBar().setCustomView(customNav);
        */
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(false);
        
        TabListener listener = new TabListener() {
			
			

			@Override
			public void onTabSelected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub
				
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
        
		
		
        actionBar.addTab(actionBar.newTab()
        		.setText("Tab1")
        		.setTabListener(listener));
        
        actionBar.addTab(actionBar.newTab()
        		.setText("Tab2")
        		.setTabListener(listener));
        	

        actionBar.addTab(actionBar.newTab()
        		.setText("Tab3")
        		.setTabListener(listener));
        	

        actionBar.addTab(actionBar.newTab()
        		.setText("Tab3")
        		.setTabListener(listener));

        actionBar.addTab(actionBar.newTab()
        		.setText("asdfasdfadsfadsf")
        		.setTabListener(listener));
        

        actionBar.addTab(actionBar.newTab()
        		.setText("Tab3")
        		.setTabListener(listener));
    }    

    @Override
 public boolean onCreateOptionsMenu(Menu menu) 
        {   // TODO Auto-generated method stub
   MenuInflater inflater = getSupportMenuInflater();
   inflater.inflate(R.menu.activity_main, menu);
   return super.onCreateOptionsMenu(menu);
        }
}