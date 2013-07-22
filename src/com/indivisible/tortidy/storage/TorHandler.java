package com.indivisible.tortidy.storage;

import android.os.*;
import android.util.*;
import java.io.*;

public class TorHandler
{
    private static final String TAG = "com.indivisible.tortidy";
    private File downloads;
	
	
	public TorHandler(String downloadLocation) {
		Log.i(TAG, "download location: " +downloadLocation);
		downloads = new File(downloadLocation);
	}
	
	
	
	private boolean isStorageOk() {
		if (!downloads.exists()) {
			Log.e(TAG, "download location not exists");
			return false;
		}
		else if (!downloads.canRead()) {
			Log.e(TAG, "cannot read download location");
			return false;
		}
		else if (!downloads.canExecute()) {
			Log.e(TAG, "cannot execute download location");
			return false;
		}
		else {
		    // everything ok
		    return true;
		}
	}
}
