package com.indivisible.tortidy;

import android.content.*;
import android.os.*;
import android.preference.*;
import android.widget.Toast;
import android.util.*;
import java.io.*;
import com.indivisible.tortidy.prefs.*;

public class PrefsActivity extends PreferenceActivity implements Preference.OnPreferenceClickListener
{
    private static final String TAG = "com.indivisible.tortidy";
	
    //// preferences
	private EditTextPreference prefEtDirMonitor;
//	private EditTextPreference prefEtLabels;
	
	private Preference prefClearPrefs;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
		initPrefs();
	}
	
	/** initialise preferences and tie to ui **/
	private void initPrefs() {
		prefEtDirMonitor   = (EditTextPreference) findPreference(getString(R.string.pref_dirs_monitor_key));
//		prefEtLabels       = (EditTextPreference) findPreference(getString(R.string.pref_labels_key));
		
		prefClearPrefs     = findPreference(getString(R.string.pref_debug_clearprefs_key));
		prefClearPrefs.setOnPreferenceClickListener(this);
		
		populateDirPrefs(this.getApplicationContext());
	}
	
	
	private void populateDirPrefs(Context ctx) {
		if (prefEtDirMonitor.getText().equals("")) {
			Log.d(TAG, "Monitor dir not set. Saving default");
			File defMonitor = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
			prefEtDirMonitor.setText(defMonitor.getAbsolutePath());
		}
	}
	
	/** special handling of preference clicks **/
	@Override
	public boolean onPreferenceClick(Preference p1)
	{
		//remember: need to set Listener before handling click
		Log.i(TAG, "pref click: " +p1.getKey());
		Toast.makeText(this, "pref click: " +p1.getKey(), Toast.LENGTH_SHORT).show();
		
		// clear all preferences
		if (p1.equals(prefClearPrefs)) {
			Log.d(TAG, "clearing all preferences");
			
		    Preferences prefs = new Preferences(getApplicationContext());
			if (prefs.clear()) {
				Log.d(TAG, "successfully cleared");
				Toast.makeText(this, "Preferences cleared", Toast.LENGTH_SHORT).show();
				// end activity as new values won't update until reload
				finish();
			}
			else {
				Log.e(TAG, "could not clear prefs");
				Toast.makeText(this, "Couldn't clear preferences", Toast.LENGTH_SHORT).show();
			}
		}
		
		// notify click handled
		return true;
	}
	
}
