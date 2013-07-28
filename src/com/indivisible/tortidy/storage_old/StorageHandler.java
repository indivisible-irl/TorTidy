package com.indivisible.tortidy.storage_old;

import com.indivisible.tortidy.prefs.*;
import java.io.*;
import android.util.*;
import java.util.*;
import android.content.*;
import android.os.*;

public class StorageHandler
{
    private static final String TAG = "com.indivisible.tortidy";
	private static final String TOR_FILE_EXT = ".torrent";
	private static final String DIR_APP_NAME = "TorTidy";
	private static final String DIR_QUEUE_NAME = "Queue";
	private static final String DIR_COMPLETED_NAME = "Completed";

	
	
	/** test given storage location for access */
	private static boolean isStorageOk(File testDirectory) {
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
	
	/** form and get the monitor ditectory **/
	public static File getMonitorDirectory(Context ctx) {
		Preferences prefs = new Preferences(ctx);
		String monitorPath = prefs.getMonitorDirPath();
		File monitor = new File(monitorPath);
		return ensureDirExists(monitor);
	}
	
	/** retrieve queue directory **/
	public static File getQueueDirectory(Context ctx) {
		return getExtDirectory(ctx, DIR_QUEUE_NAME);
	}
	
	/** retrieve completed directory **/
	public static File getCompletedDirectory(Context ctx) {
		return getExtDirectory(ctx, DIR_COMPLETED_NAME);
	}
	
	/** retrieve external directory named with supplied String **/
	private static File getExtDirectory(Context ctx, String dir) {
		File myExtDir = getExtAppDirectory(ctx);
		if (myExtDir == null) {
			Log.e(TAG, "ext app dir returned null");
			return null;
		}
		File extDir = new File(myExtDir, dir);
		return ensureDirExists(extDir);
	}
	
	/** retrieve (and create) apps external directory **/
	private static File getExtAppDirectory(Context ctx) {
		if (isExternalStorageAccessible()) {
		    File extDir = Environment.getExternalStorageDirectory();
		    File myExtDir = new File (extDir, DIR_APP_NAME);
		    return ensureDirExists(myExtDir);
		}
		else {
			return ensureDirExists(ctx.getFilesDir());
		}
	}
	
	/** ensure a directory exists and return it **/
	private static File ensureDirExists(File dir) {
		if (dir == null) {
			Log.e(TAG, "supplied dir was null");
			return null;
		}
		else if (!dir.exists()) {
			Log.d(TAG, "dir not exists, creating: " +dir.getAbsolutePath());
			if (!dir.mkdirs()) {
				Log.e(TAG, "unable to create dir: " +dir.getAbsolutePath());
				return null; //TODO need better return/error handling
			}
		}
		return dir;
	}
	
	/** test if external storage is available **/
	private static boolean isExternalStorageAccessible() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)
			&& Environment.getExternalStorageDirectory().canWrite()) {
			return true;
		}
		else {
		    Log.w(TAG, "External Storage unwritable:");
	    	Log.w(TAG, state);
	    	return false;
		}
	}
	
	/** recursive search for torrents **/
    public static List<Tor> getTorrentsRecursive(List<Tor> tors, File directory, String rootDir) {
        File[] fileList = directory.listFiles(filterDirsAndTors);

		for (File fileOrDir : fileList) {
			if (fileOrDir.isDirectory()) {
				getTorrentsRecursive(tors, fileOrDir, rootDir);
			}
			else {
				String labelTitle = LabelsHandler.getLabelFromLocation(fileOrDir, rootDir);
				Label label = new Label(labelTitle, true);
				Log.d(TAG, "adding tor: " +fileOrDir.getAbsolutePath());
				tors.add(new Tor(fileOrDir, label));
			}
		}
		Log.i(TAG, "found " +tors.size()+ "tors so far"); 
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
