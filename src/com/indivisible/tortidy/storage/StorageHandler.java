package com.indivisible.tortidy.storage;

import com.indivisible.tortidy.prefs.*;
import java.io.*;
import android.util.*;
import java.util.*;

public class StorageHandler
{
    private static final String TAG = "com.indivisible.tortidy";
	private static final String TOR_FILE_EXT = ".torrent";

	
	
	/** test given storage location for access */
	public static boolean isStorageOk(File testDirectory) {
		if (!testDirectory.exists()) {
			Log.e(TAG, "location not exists: " +testDirectory.getAbsolutePath());
			return false;
		}
		else if (!testDirectory.canRead()) {
			Log.e(TAG, "cannot read location: " +testDirectory.getAbsolutePath());
			return false;
		}
        else if (!testDirectory.canWrite()) {
            Log.e(TAG, "cannot write location: " +testDirectory.getAbsolutePath());
			return false;
        }
		else if (!testDirectory.canExecute()) {
			Log.e(TAG, "cannot execute location: " +testDirectory.getAbsolutePath());
			return false;
		}
		else {
		    Log.d(TAG, "location accessible: " +testDirectory.getAbsolutePath());
		    return true;
		}
	}
	
	/** recursive search for torrents **/
    public static List<Tor> getTorrentsRecursive(List<Tor> tors, File directory) {
        File[] fileList = directory.listFiles(filterDirsAndTors);

		for (File fileOrDir : fileList) {
			if (fileOrDir.isDirectory()) {
				getTorrentsRecursive(tors, fileOrDir);
			}
			else {
				Log.d(TAG, "adding tor: " +fileOrDir.getAbsolutePath());
				tors.add(new Tor(fileOrDir));
			}
		}

		return tors;
    }
	
	/** Filter directory file lists for sub-dirs and tor files **/
    private static FileFilter filterDirsAndTors = new FileFilter() {
		public boolean accept(File file) {
			if (file.isDirectory()) {
				return true;
			}
			else if (file.getAbsolutePath().toLowerCase().endsWith(TOR_FILE_EXT)){
				return true;
			}
			else {
			    return false;
			}
		}
	};
}
