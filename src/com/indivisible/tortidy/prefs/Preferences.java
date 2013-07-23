package com.indivisible.tortidy.prefs;

import android.content.*;
import android.os.*;
import android.preference.*;

/** interface for interacting with preferences **/
public class Preferences
{
    private static final String TAG = "com.indivisible.tortidy";
	
	private SharedPreferences prefs;
	
	//// preferences:
	private static final String PREF_DIR_DOWN = "downloadsDir";
	private static final String PREF_DIR_UP   = "uploadsDir";
	
	
	
	public Preferences(Context context) {
		prefs = PreferenceManager.getDefaultSharedPreferences(context);
		//prefs = context.getSharedPreferences(PREFS_NAME, PREFS_MODE);
	}
	
	
	
	public String getDownloadsDir() {
		
		if (!prefs.contains(PREF_DIR_DOWN)) {
			//TODO ask for downloads location
			String defaultDownloads = Environment.getExternalStoragePublicDirectory(
		        Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();

			SharedPreferences.Editor editor = prefs.edit();
			editor.putString(PREF_DIR_DOWN, defaultDownloads);
			editor.commit();
		}
		
		return prefs.getString(PREF_DIR_DOWN, "error retrieving PREF_DIR_DOWN");
	}
	
	
	public String getUploadsDir() {

		if (!prefs.contains(PREF_DIR_UP)) {
			//TODO ask for uploads location
			String defaultDownloads = Environment.getExternalStoragePublicDirectory(
		        Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();

			SharedPreferences.Editor editor = prefs.edit();
			editor.putString(PREF_DIR_UP, defaultDownloads);
			editor.commit();
		}

		return prefs.getString(PREF_DIR_UP, "error retrieving PREF_DIR_DOWN");
	}
}
