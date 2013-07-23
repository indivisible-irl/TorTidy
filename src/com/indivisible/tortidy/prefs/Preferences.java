package com.indivisible.tortidy.prefs;

import android.content.*;
import android.os.*;
import android.preference.*;
import com.indivisible.tortidy.*;

/** interface for interacting with preferences **/
public class Preferences
{
    private static final String TAG = "com.indivisible.tortidy";
	
	private SharedPreferences prefs;
	private Context context;
	
	//// preferences:
	private String PREF_DIR_DOWN_KEY;
	private String PREF_DIR_UP_KEY;
	private String PREF_LABELS_KEY;
	
	
	/** interface with prefetences **/
	public Preferences(Context ctx) {
		prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
		context = ctx;
		setStrings();
	}
	
	/** grab string resources **/
	private void setStrings() {
		PREF_DIR_DOWN_KEY = context.getString(R.string.pref_dirs_down_key);
		PREF_DIR_UP_KEY = context.getString(R.string.pref_dirs_up_key);
		PREF_LABELS_KEY = context.getString(R.string.pref_labels_key);
	}
	
	/** retrieve the saved download directory **/
	public String getDownloadsDir() {
		
		if (!prefs.contains(PREF_DIR_DOWN_KEY)) {
			//TODO ask for downloads location
			String defaultDownloads = Environment.getExternalStoragePublicDirectory(
		        Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();

			SharedPreferences.Editor editor = prefs.edit();
			editor.putString(PREF_DIR_DOWN_KEY, defaultDownloads);
			editor.commit();
		}
		
		return prefs.getString(PREF_DIR_DOWN_KEY, "error retrieving PREF_DIR_DOWN");
	}
	
	/** retrieve the saved upload directory **/
	public String getUploadsDir() {
		if (!prefs.contains(PREF_DIR_UP_KEY)) {
			//TODO ask for uploads location
			String defaultDownloads = Environment.getExternalStoragePublicDirectory(
		        Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();

			SharedPreferences.Editor editor = prefs.edit();
			editor.putString(PREF_DIR_UP_KEY, defaultDownloads);
			editor.commit();
		}
		return prefs.getString(PREF_DIR_UP_KEY, "error retrieving PREF_DIR_DOWN");
	}
	
	/** retrieve torrent labels **/
	public String getLabels() {
		String defLabels = context.getString(R.string.pref_labels_default);
		return prefs.getString(PREF_LABELS_KEY, defLabels);
	}
	
	/** set labels **/
	public void setLabels(String s) {
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(PREF_LABELS_KEY, s);
		editor.commit();
	}
}
