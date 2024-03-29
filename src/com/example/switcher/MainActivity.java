package com.example.switcher;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.applist.ListInstalledApps;
public class MainActivity extends Activity {
	final static int APP1=0;
	final static int APP2=1;
	static String APP1packagename="com.example.silenttoggle";
	static String APP2packagename="com.whatsapp";
	static boolean fromnotify = false;
	static boolean closeappsonclose = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent=getIntent();
        if(intent.getStringExtra("FromPopup")!=null)
        {
        	Intent listIntent = new Intent(this, ListInstalledApps.class);
        	startActivity(listIntent);
        }
        if(intent.getStringExtra("APP1name")!=null)
        {
        	APP1packagename=intent.getStringExtra("APP1name");
        	startchathead(this.getCurrentFocus());
        }
        if(intent.getStringExtra("APP2name")!=null)
        	{
        		APP2packagename=intent.getStringExtra("APP2name");
        		startchathead(this.getCurrentFocus());
        	}
        if(fromnotify) 
        {
        	startchathead(this.getCurrentFocus());
        	fromnotify=false;
        	File root = new File(Environment.getExternalStorageDirectory(), "PolyJuice");
    		File file = new File(root,"buf.txt");
    		file.delete();
        }
        
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
    	return super.onOptionsItemSelected(item);
    }
    static int currApp=APP1;
    public void switchapps(View view){
    	Intent intent;
    	if(currApp==APP1) {
    		intent=getPackageManager().getLaunchIntentForPackage(APP1packagename);
    		startActivity(intent);
    		currApp=APP2;
    	}else{
    		intent=getPackageManager().getLaunchIntentForPackage(APP2packagename);
    		startActivity(intent);
    		currApp=APP1;
    	}
    	
    }
    public void changeTargets(View view){
    	Intent intent=new Intent(this,ListInstalledApps.class);
    	startActivity(intent);
    }
    public void gettargets(View view){
    	EditText app1=(EditText) findViewById(R.id.app_1_btn);
    	EditText app2=(EditText) findViewById(R.id.app_2_btn);
    	System.out.println("appnames:"+app1.getText().toString()+" "+app2.getText().toString());
    	setAppTargets(app1.getText().toString(),app2.getText().toString());
    }
    public void createhead(){
    	Intent intent=new Intent(this,ChatheadService.class);
    	intent.putExtra("APP1name", APP1packagename);
    	intent.putExtra("APP2name", APP2packagename);
    	stopService(intent);
    	startService(intent);
    }
    public void setAppTargets(String app1,String app2){
    	APP1packagename=app1;
    	APP2packagename=app2;
    }
    public void onDestroy(){
    	super.onDestroy();
    	//Toast.makeText(this, "destroyed", Toast.LENGTH_SHORT).show();
    	Intent intent=new Intent(this,ChatheadService.class);
    	stopService(intent);
    }
    public void startchathead(View view){
    	System.out.println("start");
    	createhead();
    }
    public void stopchathead(View view){
    	Intent intent=new Intent(this,ChatheadService.class);
    	stopService(intent);
    }
}
