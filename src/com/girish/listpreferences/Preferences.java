package com.girish.listpreferences;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.switcher.R;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;




public class Preferences  extends Base{
	
	
	
	List<Map<String, String>> prefList = new ArrayList<Map<String,String>>(); 
	
	
	
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);
        final SimpleAdapter simpleAdpt = new SimpleAdapter(this, prefList, android.R.layout.simple_list_item_1, new String[] {"preferences"}, new int[] {android.R.id.text1});
        File sd = Environment.getExternalStorageDirectory();
        File file = new File(sd, "/"+file1+"/"+hfile1);
        if (file.length() == 0) 
        
        	{String s="Facebook and Whatsapp";
        	genSD(s);
        a[0]=s;count=count+1;++i;
        }
    else{readSD();}
        initList();
        if(count!=0){Toast.makeText(Preferences.this,"Preferences Updated", Toast.LENGTH_SHORT).show();}
		
        ListView lv = (ListView) findViewById(R.id.listView);
        registerForContextMenu(lv);
         
      
       
       
        
     
        lv.setAdapter(simpleAdpt);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        	 
        	public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
                     
       
                     
             
              
                TextView clickedView = (TextView) view;
                String s1="";String s2="";String sm[];
                s1=(String) clickedView.getText();
                sm=s1.split(" ");s1=sm[0];s2=sm[2];
        
                Toast.makeText(Preferences.this,s1+" "+s2, Toast.LENGTH_SHORT).show();
        
            }});
         
        
        
   
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
            ContextMenuInfo menuInfo) {
         
        super.onCreateContextMenu(menu, v, menuInfo);
        AdapterContextMenuInfo aInfo = (AdapterContextMenuInfo) menuInfo;
         
        // We know that each row in the adapter is a Map
        final SimpleAdapter simpleAdpt = new SimpleAdapter(this, prefList, android.R.layout.simple_list_item_1, new String[] {"preferences"}, new int[] {android.R.id.text1});
		HashMap map =  (HashMap) simpleAdpt.getItem(aInfo.position);
         
        menu.setHeaderTitle("Options");
        menu.add(1,0,0,"Add Preference");
        menu.add(1, 1, 1, "Open");
        menu.add(1, 2, 2, "Delete "+ map.get("preferences"));
         
    }
    
    public void genSD(String s)
    {
    	try
    	{
    		File root = new File(Environment.getExternalStorageDirectory(),file1);
    		if(!root.exists()) root.mkdirs();
    		File gpx = new File(root, hfile1);
    		FileWriter writer = new FileWriter(gpx, true);
    		writer.append(s+"\n");
    		writer.close();
    		Toast.makeText(this, "Preferences Updated", Toast.LENGTH_SHORT).show();
    	}
    	catch (IOException e)
    	{
    		e.printStackTrace();
    		Toast.makeText(this, "Unable to update preferences", Toast.LENGTH_SHORT).show();
    	}
    }
    public void update(String s)
    {File root = new File(Environment.getExternalStorageDirectory(),file1);
    	File gpx = new File(root, hfile1);
    	gpx.delete();
    	/*File root2 = new File(Environment.getExternalStorageDirectory(),file2);
    	File gpx2 = new File(root2, hfile2);
    	gpx2.delete();*/
    
    	try
    	{
    	File root1 = new File(Environment.getExternalStorageDirectory(),file1);
    	File gpx1 = new File(root1, hfile1);
    	
    		if(!root1.exists()) root1.mkdirs();
    		
    		FileWriter writer = new FileWriter(gpx1, true);
    		for(int j=0;j<i;j++)
    		{if(s.contentEquals(a[j].toString()))continue;else writer.write(a[j].toString()+"\n");}
    		writer.close();
    		--i;
    		
    		readSD();
    		initList();
    		Intent z=new Intent(this,Load.class);
    		startActivity(z);
    		Toast.makeText(Preferences.this,"Preferences Updated", Toast.LENGTH_SHORT).show();
    		
    	}
    	catch (IOException e)
    	{
    		e.printStackTrace();
    		
    	}
    }
    public void readSD()
    {
    	try
    	{
    		File sd = Environment.getExternalStorageDirectory();
    		File file = new File(sd, "/"+file1+"/"+hfile1);
    		FileInputStream fis = new FileInputStream(file);
    		InputStreamReader isr = new InputStreamReader(fis, "UTF8");
    		BufferedReader r = new BufferedReader(isr);
    		
    		int g=0;
    		String line;
    		while((line = r.readLine()) != null)
    		{
    			
    			try {
    	            a[g]=line;
    	           // Toast.makeText(this,a[g], Toast.LENGTH_SHORT).show();
    	            g=g+1;
    	         }
    	         catch (ArrayIndexOutOfBoundsException e) {
    	        	 //Toast.makeText(Preferences.this,"No hello", Toast.LENGTH_SHORT).show();
    	        	 }
    			
    			
    			
    		}
    		fis.close();
    		isr.close();
    		r.close();
    		
    i=g;
    } 
    	catch (IOException e)
    	{
    		e.printStackTrace();
    		Toast.makeText(this, "Unable to read from preferences", Toast.LENGTH_SHORT).show();
    	}
    }
    
	public boolean onContextItemSelected(MenuItem item) {
		int p=item.getItemId(); 
		if(p==0)
			   {
			Intent start = new Intent(this, Dialog.class);
			
			   startActivity(start);
			return true;}
		if(p==1){return true;}
		if(p== 2){String[] words = item.getTitle().toString().split(" "); ;update(words[1]+" "+words[2]+" "+words[3]);return true;}
		else
	return false;
    }
	/*public void deletef(String s)
	{
		
		for(int j=0;j<i;i++)
		{if(a[j]==s)
		{for(int k=j;k<i;k++)a[k]=a[k+1];break;}}
		--i;
		
		
	}*/
	
    private void initList() {
    	
    	final SimpleAdapter simpleAdpt = new SimpleAdapter(this, prefList, android.R.layout.simple_list_item_1, new String[] {"preferences"}, new int[] {android.R.id.text1});
    		for(int j=0;j<i;j++)
    		{
    			String[] words = a[j].toString().split(" ");//Toast.makeText(this, a[j], Toast.LENGTH_SHORT).show();
    			prefList.add(createPref("preferences", words[0]+" "+words[1]+" "+words[2]));
    			simpleAdpt.notifyDataSetChanged();
    			
    			
    		}
    		
    		simpleAdpt.notifyDataSetChanged();
 
         
    }
    private HashMap<String, String> createPref(String key, String name) {
    	HashMap<String, String> pref = new HashMap<String, String>();
    	pref.put(key, name);
    	return pref;
    	}
    
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	menu.add(Menu.NONE, 0, 0, "Add Preferences");
    	menu.add(Menu.NONE,1,1,"Back");
    	return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected1(MenuItem item) {
    	switch (item.getItemId()) {
    		case 0:
    			   {startActivity(new Intent(this, Dialog.class));
    			return true;}
    		case 1:{startActivity(new Intent(this, com.example.switcher.ChatheadService.class));
			return true;}
    	}
    	return false;
    }

    
    public boolean onOptionsItemSelected(MenuItem item) {
        
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected1(item);
    }
}
