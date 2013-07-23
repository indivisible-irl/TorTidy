package com.indivisible.tortidy;

import android.preference.*;
import android.content.SharedPreferences.*;
import android.os.*;

public class PrefsActivity extends PreferenceActivity implements Preference.OnPreferenceClickListener
{
    private static final String TAG = "com.indivisible.tortidy";
	
    //// preferences
	private EditTextPreference prefEtDirDownloads;
	private EditTextPreference prefEtDirUploads;
	private EditTextPreference prefEtLabels;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
		initPrefs();
	}
	
	/** initialise preferences and tie to ui **/
	private void initPrefs() {
		prefEtDirDownloads = (EditTextPreference) findPreference(getString(R.string.pref_dirs_down_key));
		prefEtDirUploads   = (EditTextPreference) findPreference(getString(R.string.pref_dirs_up_key));
		
		prefEtLabels       = (EditTextPreference) findPreference(getString(R.string.pref_labels_key));
	}
	
	/** special handling of preference clicks **/
	public boolean onPreferenceClick(Preference p1)
	{
		// notify click handled
		return true; //false to pass on?
	}
	
}
