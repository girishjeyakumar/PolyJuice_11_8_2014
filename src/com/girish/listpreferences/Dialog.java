package com.girish.listpreferences;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.example.switcher.R;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Dialog extends Base {
	  //Bundle b=this.getIntent().getExtras();
      //String[] a=b.getStringArray("Pref");
      //int i=getIntent().getIntExtra("Count",0);

	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
      
		
	   
	    // Add action buttons
	    final EditText t1=(EditText) findViewById(R.id.app1);
	    final EditText t2=(EditText) findViewById(R.id.app2);
	    
	    Button b1;b1=(Button) findViewById(R.id.button1);
	    b1.setOnClickListener(new View.OnClickListener() {
	    			
	    			@Override
	    			public void onClick(View v) {add(t1.getText().toString()+" and "+t2.getText().toString()+"\n");Intent start = new Intent(Dialog.this, Load.class);
		 			
	 				   startActivity(start);}
	    });
	    Button b2;b2=(Button) findViewById(R.id.button2);
	    b2.setOnClickListener(new View.OnClickListener() {
	    			
	    			@Override
	    			public void onClick(View v) {
	 				Intent start = new Intent(Dialog.this, Preferences.class);
	 			
	 				   startActivity(start);}
	    });
	           
	    
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dialog, menu);
		return true;
	}
	public void add (String s){Preferences.a[Preferences.i]=s;Preferences.i+=1;genSD(s);}
	 public void genSD(String s)
	    {
	    	try
	    	{
	    		File root = new File(Environment.getExternalStorageDirectory(),file1);
	    		if(!root.exists()) root.mkdirs();
	    		File gpx = new File(root, hfile1);
	    		FileWriter writer = new FileWriter(gpx, true);
	    		writer.append(s);
	    		writer.close();
	    		
	    	}
	    	catch (IOException e)
	    	{
	    		e.printStackTrace();
	    		Toast.makeText(this, "Unable to update preferences", Toast.LENGTH_SHORT).show();
	    	}
	    }
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
