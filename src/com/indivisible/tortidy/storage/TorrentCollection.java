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
		
		monitorDirectory   = new File(prefs.getMonitorDirPath());
		queueDirectory     = StorageHandler.getQueueDirectory(ctx);
		completedDirectory = StorageHandler.getCompletedDirectory(ctx);
		
		populateLists();
	}
	
	/** populate the List<Tor> with torrent objects **/
    public void populateLists() {
        populate(queueTorrents, queueDirectory);
		populate(completedTorrents, completedDirectory);
		populate(monitorTorrents, monitorDirectory);
    }
    
	/** populate a torrent list with the directory's contents **/
	private void populate(List<Tor> tors, File directory) {
		if (directory != null) {
			tors = new ArrayList<Tor>();
			StorageHandler.getTorrentsRecursive(tors, directory);
		}
		else {
			Log.e(TAG, "unable to access storage: " +directory.getAbsolutePath());
		}
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
