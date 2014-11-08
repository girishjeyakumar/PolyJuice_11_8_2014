package com.girish.listpreferences;

import android.app.Activity;
import android.content.Intent;
import android.view.MenuItem;

public abstract class Base extends Activity
{
	String file1="SwApp1";String file2="SwApp1";
	String hfile1="SwApp_Log1.txt";String hfile2="SwApp_Log1.txt";
	static int i=0;
	 static String[] a=new String[50];
	 static int count =0;
	public boolean onOptionsItemSelected1(MenuItem item) {
		switch (item.getItemId())  {
		case 0:
			   {startActivity(new Intent(this, Dialog.class));
			return true;}
	}
	return false;
	}
	 
  //static variables
  // static methods
}