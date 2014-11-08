package com.example.switcher;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import com.girish.listpreferences.Preferences;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

public class ChatheadService extends Service{
	private WindowManager windowManager;
	  private ImageView chatHead;
	  final static int imgsize=65;
	  String app1,app2;
	  String applist[];
	  static boolean fav=true;
	  
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	String sFileName = "/PolyJuice/eciuj_ylop.txt",pref_file="/PolyJuice/pref_user.txt";
	int ctr=0;
	boolean notify=true;
	public void onCreate(){
		super.onCreate();
		
		if(notify) readSD(sFileName);
		
		//Toast.makeText(getApplicationContext(), MainActivity.APP1packagename, Toast.LENGTH_SHORT).show();
		//Toast.makeText(getApplicationContext(), MainActivity.APP2packagename, Toast.LENGTH_SHORT).show();
	    windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
	    
	    chatHead = new ImageView(this);
	    chatHead.setBackgroundColor(Color.parseColor("#00000000"));
	    chatHead.setImageResource(R.drawable.icon);
	    try {
	    	Drawable b=getPackageManager().getApplicationInfo(MainActivity.APP2packagename,0).loadIcon(getPackageManager());
			chatHead.setImageBitmap(Bitmap.createScaledBitmap(drawableToBitmap(b), dpToPx(imgsize), dpToPx(imgsize), true));	
			Intent intent=getPackageManager().getLaunchIntentForPackage(MainActivity.APP1packagename);
    		startActivity(intent);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    Intent intent=getPackageManager().getLaunchIntentForPackage(MainActivity.APP1packagename);
		startActivity(intent);
		MainActivity.currApp=MainActivity.APP2;
	     final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
	        WindowManager.LayoutParams.WRAP_CONTENT,
	        WindowManager.LayoutParams.WRAP_CONTENT,
	        WindowManager.LayoutParams.TYPE_PHONE,
	        WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL|
	        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|
	        WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
	        PixelFormat.TRANSLUCENT);

	    params.gravity = Gravity.TOP | Gravity.LEFT;
	    params.x = 0;
	    params.y = 100;

	    windowManager.addView(chatHead, params);
	    chatHead.setOnTouchListener(new View.OnTouchListener() {
	    	  private int initialX;
	    	  private int initialY;
	    	  private float initialTouchX;
	    	  private float initialTouchY;
	    	  private int f=0,cx,cy,t=0;
	    	  private long then;

	    	  @Override public boolean onTouch(View v, MotionEvent event) {
	    		  if(event.getActionMasked()!=MotionEvent.ACTION_OUTSIDE)
	    		  {
	    			  switch (event.getActionMasked()) {
		    	      case MotionEvent.ACTION_DOWN:
		    	        initialX = params.x;
		    	        initialY = params.y;
		    	        initialTouchX = event.getRawX();
		    	        initialTouchY = event.getRawY();
		    	        then = (long) System.currentTimeMillis();
		    	        
		    	        if (t == 30)
		    	        {
		    	        	showpopup();
		    	        	f = -1;
		    	        	t = 0;
		    	        }
		    	        else ++t;
		    	        return true;
		    	      
		    	      case MotionEvent.ACTION_MOVE:
		    	    	cx=(int) (event.getRawX() - initialTouchX);
		    	    	cy=(int) (event.getRawY() - initialTouchY);
		    	        params.x = initialX + cx;
		    	        params.y = initialY + cy;
		    	        windowManager.updateViewLayout(chatHead, params);
		    	        if(Math.abs(cx)>3 && Math.abs(cy)>3)
		    	        	f=1;
		    	        else if (t == 30)
		    	        {
		    	        	showpopup();
		    	        	f = -1;
		    	        	t = 0;
		    	        }
		    	        else ++t;
		    	        return true;
		  
		    	    }
	    			  
	    		  }
	    		  if(event.getActionMasked()==MotionEvent.ACTION_UP)
	    		  {
	    			  if(f==0) switchapps();
	    			  else f=0;
	    			  return false;
	    		  }
	    	    
	    		  
	    	    return false;
	    	  }
	    	});

	}
	
	 public void readSD(String sFileName) 
	    {
	    	
	    		try {
	    		File sdcard = Environment.getExternalStorageDirectory();

	    		File file = new File(sdcard,sFileName);

	    		FileInputStream fis = new FileInputStream(file);
	    	    InputStreamReader isr = new InputStreamReader(fis, "UTF8");
	    	    BufferedReader r = new BufferedReader(isr);
	    	    
	    		    String strLine,app1,app2;
	    		    String[] spinp;
	    		    boolean f=sFileName.equals("buf.txt");
	    		        		    
	    		        while ((strLine = r.readLine()) != null) 
	    		        {  		        	
	    		            spinp=strLine.split(strLine);
	    		            ctr=Integer.getInteger(spinp[0]);
	    		            app1=spinp[1];
	    		            app2=spinp[2];
	    		            if(ctr==0)
	    		            {
	    		            	MainActivity.APP1packagename=app1;
	    		            	MainActivity.APP2packagename=app2;
	    		            } 
	    		            if(f && spinp[0].equals("fromnotify"))
	    		            {
	    		            	MainActivity.fromnotify=true;
	    		            }
	    		            
	    		                		            
	    		        } 
	    		        if(strLine==null)
	    	            {
	    	            		//call setting up references
	    	            }
	    		        r.close();
	    		     } 
	    			catch (IOException e) {
	    		   			     e.printStackTrace();
	    		   			  //Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
	    		    }
	    }
	 /*PopupMenu prefmenu = new PopupMenu(ChatheadService.this, chatHead); 
	 String[] packinfo;
	 int size_pref;
	 
	 void show_pref_popup()
	 {
		 gen_pref_from_file();
		 
		 prefmenu.getMenuInflater().inflate(R.menu.popupprefer, prefmenu.getMenu());
	     prefmenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				int i=0,id;
				String[] spinp;
				
				/*if(item.getItemId()==0)
				{
					spinp=packinfo[i].split(" ");
					MainActivity.APP1packagename=spinp[1];
					MainActivity.APP2packagename=spinp[2];
				}
				else if(item.getItemId()==0)
				{
					spinp=packinfo[i].split(" ");
					MainActivity.APP1packagename=spinp[1];
					MainActivity.APP2packagename=spinp[2];
				}*/
				
				/*id=item.getItemId();
				for(;i<size_pref;i++)
				{
					spinp=packinfo[i].split(" ");
					if(Integer.getInteger(spinp[0])==i)
					{
						MainActivity.APP1packagename=spinp[1];
						MainActivity.APP2packagename=spinp[2];
					}
				}
				
				
				return false;
			}
		});
		 prefmenu.show();
	 }

	 void gen_pref_from_file()
	 {
		 try {
	    		File sdcard = Environment.getExternalStorageDirectory();

	    		File file = new File(sdcard,pref_file);

	    		FileInputStream fis = new FileInputStream(file);
	    	    InputStreamReader isr = new InputStreamReader(fis, "UTF8");
	    	    BufferedReader r = new BufferedReader(isr);
	    	    
	    		    String strLine,app1,app2,app12;
	    		    int ctr,i=0;
	    		    String[] spinp;
	    		    
	    		    while ((strLine = r.readLine()) != null) 
	    		    {
	    		    	
	    		    	spinp=strLine.split(" ");
	    		    	packinfo[i++]=strLine;
	    		    	app1=(String) getPackageManager().getApplicationInfo(spinp[1], 0).loadLabel(getPackageManager());
	    		    	app2=(String) getPackageManager().getApplicationInfo(spinp[2], 0).loadLabel(getPackageManager());
	    		    	ctr=Integer.getInteger(spinp[0]);
	    		    	if(ctr==0)
	    		    	{
	    		    		app1.toUpperCase();
	    		    		app2.toUpperCase();
	    		    		app12=app1 + " AND " + app2;
	    		    	}
	    		    	else
	    		    	{
	    		    		app12=app1 + " and " + app2;
	    		    	}
	    		    	prefmenu.getMenu().add(app12);
	    		    }
	    		    size_pref=i;
	    		    r.close();
		 }
		 catch(Exception e)
		 {
			 e.printStackTrace();
		 }
	 }*/
	    public void generateNoteOnSD(String sFileName){
	        try
	        {	
	        	File root = new File(Environment.getExternalStorageDirectory(), "PolyJuice");
	            if (!root.exists()) {
	                root.mkdirs();
	            }
	            File gpxfile = new File(root, sFileName);
	            FileWriter writer = new FileWriter(gpxfile,true);
	            readSD(sFileName);
	            if(fav)	ctr=0;
	            else ctr++;
	            String wropt = Integer.toString(ctr) + " " + MainActivity.APP1packagename + " " + MainActivity.APP2packagename;
	            writer.write(wropt);
	            writer.append('\n');
	            if(sFileName.equals("buf.txt"))
	            		{
	            			writer.append("fromnotify");
	            			writer.append('\n');
	            		}
	            writer.flush();
	            writer.close();
	            //Toast.makeText(this, "Recorded..", Toast.LENGTH_SHORT).show();
	            
	        }
	        catch(IOException e)
	        {
	             e.printStackTrace();
	             Toast.makeText(this, "Error--generation", Toast.LENGTH_SHORT).show();
	    	    }
	       }  

	
	public void showpopup()
	{
    	PopupMenu popup = new PopupMenu(ChatheadService.this, chatHead);
    	String nextAppName = (MainActivity.currApp == MainActivity.APP1) ? 
    			MainActivity.APP1packagename : MainActivity.APP2packagename;
    	// code for setting next app name starts
    	PackageManager pm = getPackageManager();
    	List<PackageInfo> packs = pm.getInstalledPackages(0);
    	for(int i = 0; i < packs.size(); i++) {
            PackageInfo p = packs.get(i);
            if (p.packageName.equals(nextAppName))
            {
            	nextAppName = p.applicationInfo.loadLabel(pm).toString();
            	break;
            }
         }
    	final String switchItemName = getText(R.string.popup_switch) + " " + nextAppName;
    	// item for switching apps is "Switch to WhatsApp" if next app is WhatsApp, for e.g.
    	// next app name is now set
        popup.getMenuInflater().inflate(R.menu.popup, popup.getMenu());
        popup.getMenu().findItem(R.id.switch_app).setTitle(switchItemName);
       
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {  
         public boolean onMenuItemClick(MenuItem item) {  
          if (item.getTitle() == getText(R.string.popup_hide))
          {
        	  ctr=0;
        	  generateNoteOnSD("buf.txt");
        	  Intent intent=new Intent(getApplicationContext(),ChatheadService.class);
        	  stopService(intent);
        	  notify_chathead();        	  
          }
          else if (item.getTitle() == switchItemName)
          {
        	  switchapps();
          }
          else if (item.getTitle() == getText(R.string.popup_change))
          {
        	  Intent tintent=new Intent(getApplicationContext(),ChatheadService.class);
        	  stopService(tintent);
        	  Intent intent=getPackageManager().getLaunchIntentForPackage("com.example.switcher");
        	  intent.putExtra("FromPopup", "Called From Popup");
        	  startActivity(intent);
          }
          else if (item.getTitle() == getText(R.string.popup_preferences))
          {
        	 Intent z=new Intent (ChatheadService.this,com.girish.listpreferences.Preferences.class);
        	 startActivity(z);
          }
          else if (item.getTitle() == getText(R.string.popup_close))
          {
        	  Intent intent=new Intent(getApplicationContext(),ChatheadService.class);
        	  stopService(intent);
        	  //intent=getPackageManager().getLaunchIntentForPackage(MainActivity.APP1packagename);
        	  //stopService(intent);
        	  
        	  //intent=getPackageManager().getLaunchIntentForPackage(MainActivity.APP2packagename);
        	  //stopService(intent);
          }
          return true;  
         }  
        });  

        popup.show();  
    }
	public void notify_chathead()
	{
		notify=false;
		NotificationCompat.Builder mBuilder =
		        new NotificationCompat.Builder(this)
		        .setSmallIcon(R.drawable.icon)
		        .setContentTitle("PolyJuice is running...")
		        .setContentText("Touch to show appheads");
		// Creates an explicit intent for an Activity in your app
		Intent resultIntent = new Intent(this, MainActivity.class);
		// The stack builder object will contain an artificial back stack for the
		// started Activity.
		// This ensures that navigating backward from the Activity leads out of
		// your application to the Home screen.
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
		// Adds the back stack for the Intent (but not the Intent itself)
		stackBuilder.addParentStack(MainActivity.class);
		// Adds the Intent that starts the Activity to the top of the stack
		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
		//resultIntent.putExtra("Notify","Notify");
		
		MainActivity.fromnotify=true;
		
		mBuilder.setContentIntent(resultPendingIntent);
		mBuilder.setAutoCancel(true);
		NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		// mId allows you to update the notification later on.
		mNotificationManager.notify(0, mBuilder.build());
		readSD("buf.txt");
	}
	public void switchapps(){
    	Intent intent;
 	    Drawable b;
 	    
    	if(MainActivity.currApp==MainActivity.APP1) {
    		intent=getPackageManager().getLaunchIntentForPackage(MainActivity.APP1packagename);
    		startActivity(intent);
    		try {
				b=getPackageManager().getApplicationInfo(MainActivity.APP2packagename,0).loadIcon(getPackageManager());
				chatHead.setImageBitmap(Bitmap.createScaledBitmap(drawableToBitmap(b), dpToPx(imgsize), dpToPx(imgsize), true));
				
			} catch (NameNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		MainActivity.currApp=MainActivity.APP2;
    	}else{
    		intent=getPackageManager().getLaunchIntentForPackage(MainActivity.APP2packagename);
    		startActivity(intent);
    		try {
    			b=getPackageManager().getApplicationInfo(MainActivity.APP1packagename,0).loadIcon(getPackageManager());
				chatHead.setImageBitmap(Bitmap.createScaledBitmap(drawableToBitmap(b), dpToPx(70), dpToPx(70), true));
			} catch (NameNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		MainActivity.currApp=MainActivity.APP1;
    	}
    	System.out.println(MainActivity.currApp);
	}
	@Override
	  public void onDestroy() {
	    super.onDestroy();
	    if (chatHead != null) windowManager.removeView(chatHead);
	  }
	public static Bitmap drawableToBitmap (Drawable drawable) {
	    if (drawable instanceof BitmapDrawable) {
	        return ((BitmapDrawable)drawable).getBitmap();
	    }

	    Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Config.ARGB_8888);
	    Canvas canvas = new Canvas(bitmap); 
	    drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
	    drawable.draw(canvas);
	    return bitmap;
	}
	public int dpToPx(int dp)
	 {
	     float density = getApplicationContext().getResources().getDisplayMetrics().density;
	     return Math.round((float)dp * density);
	 }
}
