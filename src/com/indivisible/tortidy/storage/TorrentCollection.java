package com.indivisible.tortidy.storage;

import android.util.*;
import java.io.*;
import java.util.*;
import android.content.*;
import com.indivisible.tortidy.prefs.*;

/** class to handle torrent file interactions **/
public class TorrentCollection
{
    private static final String TAG = "com.indivisible.tortidy";
	
	private File monitorDirectory;
    private File queueDirectory;
	private File completedDirectory;
	
	private List<Tor> monitorTorrents;
	private List<Tor> queueTorrents;
	private List<Tor> completedTorrents;
	
	private Preferences prefs;
	
	public TorrentCollection(Context ctx) {
		prefs = new Preferences(ctx);
		
		monitorDirectory   = StorageHandler.getMonitorDirectory(ctx);
		queueDirectory     = StorageHandler.getQueueDirectory(ctx);
		completedDirectory = StorageHandler.getCompletedDirectory(ctx);
		
		populateLists();
	}
	
	/** populate the List<Tor> with torrent objects **/
    public void populateLists() {
        queueTorrents     = populate(queueDirectory);
		completedTorrents = populate(completedDirectory);
		monitorTorrents   = populate(monitorDirectory);
		
		Log.w(TAG, "after populateLists monitor has: " +monitorTorrents.size());
    }
    
	/** populate a torrent list with the directory's contents **/
	private List<Tor> populate(File directory) {
		//Log.i(TAG, "Populate...:");
		List<Tor> tors = new ArrayList<Tor>();
		if (directory != null) {
			Log.d(TAG, directory.getAbsolutePath());
			StorageHandler.getTorrentsRecursive(tors, directory, directory.getName());
			//Log.i(TAG, "populated list contains: " +tors.size());
		}
		else {
			Log.e(TAG, "unable to access storage: " +directory.getAbsolutePath());
		}
		return tors;
	}
	
	/** empty all torrent lists **/
	public void clearLists() {
		monitorTorrents   = new ArrayList<Tor>();
		queueTorrents     = new ArrayList<Tor>();
		completedTorrents = new ArrayList<Tor>();
	}
	
	/** collect all tor file paths **/
	public String[] allTorPaths() {
		List<Tor> allTorrents = new ArrayList<Tor>();
		allTorrents.addAll(queueTorrents);
		allTorrents.addAll(completedTorrents);
		allTorrents.addAll(monitorTorrents);
		String[] allPaths = new String[allTorrents.size()];
		Log.i(TAG, "allTorrents size: " +allTorrents.size());
		
		for (int i=0; i<allPaths.length; i++) {
			allPaths[i] = allTorrents.get(i).getFilePath() //;
			        +"\n\t("+ allTorrents.get(i).getLabel() +")";
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
