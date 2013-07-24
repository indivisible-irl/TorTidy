package com.indivisible.tortidy.storage;

import android.util.*;
import java.io.*;
import java.util.*;
import android.content.*;
import com.indivisible.tortidy.prefs.*;

/** class to handle torrent file interactions **/
public class TorHandler
{
    private static final String TAG = "com.indivisible.tortidy";
    private static final String TOR_FILE_EXT = ".torrent";
	
	private File monitorDirectory;
    private File queueDirectory;
	private File completedDirectory;
	
	private List<Tor> monitorTorrents;
	private List<Tor> queueTorrents;
	private List<Tor> completedTorrents;
	
	private Preferences prefs;
	
	public TorHandler(Context ctx) {
		prefs = new Preferences(ctx);
		
		monitorDirectory   = new File(prefs.getMonitorDirPath());
		queueDirectory     = new File(prefs.getQueueDirPath());
		completedDirectory = new File(prefs.getCompletedDirPath());
		
		initLists();
	}
	
	private void initLists() {
		monitorTorrents   = new ArrayList<Tor>();
		queueTorrents     = new ArrayList<Tor>();
		completedTorrents = new ArrayList<Tor>();
	}
    
    /** Filter directory file lists for sub-dirs and tor files **/
    private FileFilter filterDirsAndTors = new FileFilter() {
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
	
	
	/** test given storage location for access */
	private boolean isStorageOk(File testDirectory) {
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
    
	/** populate the List<Tor> with torrent objects **/
    public void populateTorrents() {
        initLists();
		
		//TODO move to sep method
        if (isStorageOk(queueDirectory)) {
			getTorrentsRecursive(queueTorrents, queueDirectory);
		}
		else {
			Log.e(TAG, "unable to access storage: " +queueDirectory.getAbsolutePath());
		}
		
		if (isStorageOk(completedDirectory)) {
			getTorrentsRecursive(completedTorrents, completedDirectory);
		}
		else {
			Log.e(TAG, "unable to access storage: " +completedDirectory.getAbsolutePath());
		}
		
		if (isStorageOk(monitorDirectory)) {
			getTorrentsRecursive(monitorTorrents, monitorDirectory);
		}
		else {
			Log.e(TAG, "unable to access storage: " +monitorDirectory.getAbsolutePath());
		}
    }
    
	/** recursive search for torrents **/
    private List<Tor> getTorrentsRecursive(List<Tor> tors, File directory) {
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
	
	/** collect all tor file paths **/
	public String[] allTorPaths() {
		List<Tor> allTorrents = new ArrayList<Tor>();
		allTorrents.addAll(queueTorrents);
		allTorrents.addAll(completedTorrents);
		allTorrents.addAll(monitorTorrents);
		String[] allPaths = new String[allTorrents.size()];
		
		
		for (int i=0; i<allPaths.length; i++) {
			allPaths[i] = allTorrents.get(i).getFilePath();
			        //.replaceFirst(downloadsDirectory.getAbsolutePath(), "");
		}
		
		return allPaths;
	}
	
	/** all torrent paths in one string **/
	public String allTorPathsString() {
		StringBuilder sb = new StringBuilder();
		for (String path : allTorPaths()) {
			sb.append(path).append("\n");
		}
		return sb.toString();
	}
	
	
}
