package com.indivisible.tortidy.prefs;

import android.content.*;
import android.os.*;
import android.preference.*;
import android.util.*;
import com.indivisible.tortidy.*;

/** interface for interacting with preferences **/
public class Preferences
{
    private static final String TAG = "com.indivisible.tortidy";
	
	private SharedPreferences prefs;
	private Context context;
	
	//// preferences:
	private String PREF_DIR_MONITOR_KEY;
	
	
	/** interface with prefetences **/
	public Preferences(Context ctx) {
		prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
		context = ctx;
		setStrings();
	}
	
	/** grab string resources **/
	private void setStrings() {
		PREF_DIR_MONITOR_KEY   = context.getString(R.string.pref_dirs_monitor_key);
	}
	
	/** retrieve the saved monitor directory, default: Downloads **/
	public String getMonitorDirPath() {
		
		if (!prefs.contains(PREF_DIR_MONITOR_KEY)) {
			String defaultDownloads = Environment.getExternalStoragePublicDirectory(
		        Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
			setMonitorDirPath(defaultDownloads);
		}
		
		return prefs.getString(PREF_DIR_MONITOR_KEY, "!error retrieving PREF_DIR_MONITOR");
	}
	
	/** set the directory to monitor for torrents **/
	public boolean setMonitorDirPath(String path) {
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(PREF_DIR_MONITOR_KEY, path);
		boolean successful = editor.commit();
		if (!successful) {
			Log.e(TAG, "unable to save monitor dir");
		}
		return successful;
	}
	
	/** clear all preferences **/
	public boolean clear() {
		SharedPreferences.Editor editor = prefs.edit();
		editor.clear();
		boolean successful = editor.commit();
		if (successful) {
		    Log.i(TAG, "preferences cleared");
		}
		else {
			Log.e(TAG, "failed to clear preferences");
		}
		return successful;
	}
	
}
