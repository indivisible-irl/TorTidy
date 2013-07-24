package com.indivisible.tortidy.prefs;

import android.content.*;
import android.os.*;
import android.util.*;
import android.preference.*;
import com.indivisible.tortidy.*;
import java.util.*;
import java.io.*;

/** interface for interacting with preferences **/
public class Preferences
{
    private static final String TAG = "com.indivisible.tortidy";
	
	private SharedPreferences prefs;
	private Context context;
	
	//// preferences:
	private String PREF_DIR_MONITOR_KEY;
	private String PREF_DIR_QUEUE_KEY;
	private String PREF_DIR_COMPLETED_KEY;
	private String PREF_LABELS_KEY;
	
	
	/** interface with prefetences **/
	public Preferences(Context ctx) {
		prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
		context = ctx;
		setStrings();
	}
	
	/** grab string resources **/
	private void setStrings() {
		PREF_DIR_MONITOR_KEY   = context.getString(R.string.pref_dirs_monitor_key);
		PREF_DIR_QUEUE_KEY     = context.getString(R.string.pref_dirs_queue_key);
		PREF_DIR_COMPLETED_KEY = context.getString(R.string.pref_dirs_completed_key);
		PREF_LABELS_KEY        = context.getString(R.string.pref_labels_key);
	}
	
	/** retrieve the saved monitor directory, default: Downloads **/
	public String getMonitorDirPath() {
		
		if (!prefs.contains(PREF_DIR_MONITOR_KEY)) {
			String defaultDownloads = Environment.getExternalStoragePublicDirectory(
		        Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();

			SharedPreferences.Editor editor = prefs.edit();
			editor.putString(PREF_DIR_MONITOR_KEY, defaultDownloads);
			editor.commit();
		}
		
		return prefs.getString(PREF_DIR_MONITOR_KEY, "!error retrieving PREF_DIR_MONITOR");
	}
	
	/** retrieve the saved queue directory, default: app's storage **/
	public String getQueueDirPath() {
		if (!prefs.contains(PREF_DIR_QUEUE_KEY)) {
			File extStorage = Environment.getExternalStorageDirectory();
			
			return extStorage.getAbsolutePath();
		}
		return prefs.getString(PREF_DIR_QUEUE_KEY, "error retrieving PREF_DIR_QUEUE");
	}
	
	/** retrieve the saved completed directory, default: app's storage **/
	public String getCompletedDirPath() {
		if (!prefs.contains(PREF_DIR_COMPLETED_KEY)) {
			//TODO ask for uploads location?
			return "completed not yet implemented";
		}
		return prefs.getString(PREF_DIR_COMPLETED_KEY, "error retrieving PREF_DIR_COMPLETED");
	}	
	
	/** retrieve saved torrent labels **/
	public String getLabelsRaw() {
		String defLabels = context.getString(R.string.pref_labels_default);
		return prefs.getString(PREF_LABELS_KEY, defLabels);
	}
	
	/** set labels raw string **/
	public boolean setLabelsRaw(String s) {
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(PREF_LABELS_KEY, s);
		boolean successful = editor.commit();
		if (successful) {
			Log.i(TAG, "saved pref_labels: '" +s+ "'");
		}
		else {
			Log.e(TAG, "failed to save pref_labels");
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
