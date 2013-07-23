package com.indivisible.tortidy.storage;

import android.util.*;
import java.io.*;
import java.util.*;

/** class to handle torrent file interactions **/
public class TorHandler
{
    private static final String TAG = "com.indivisible.tortidy";
    private static final String TOR_FILE_EXT = ".torrent";
	
    private File downloadsDirectory;
	private List<Tor> torrents;
	
	public TorHandler(String downloadsDirectoryPath) {
		Log.i(TAG, "download location: " +downloadsDirectoryPath);
		downloadsDirectory = new File(downloadsDirectoryPath);
	}
    
    /** Filter directory file lists for sub-dirs and tor files **/
    private FileFilter filterDirsTors = new FileFilter() {
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
	private boolean isStorageOk() {
		if (!downloadsDirectory.exists()) {
			Log.e(TAG, "download location not exists");
			return false;
		}
		else if (!downloadsDirectory.canRead()) {
			Log.e(TAG, "cannot read download location");
			return false;
		}
        else if (!downloadsDirectory.canWrite()) {
            Log.e(TAG, "cannot write download location");
			return false;
        }
		else if (!downloadsDirectory.canExecute()) {
			Log.e(TAG, "cannot execute download location");
			return false;
		}
		else {
		    Log.d(TAG, "download location accessible");
		    return true;
		}
	}
    
	/** populate the List<Tor> with torrent objects **/
    public void populateTorrents() {
        torrents = new ArrayList<Tor>();
		
        if (isStorageOk()) {
			getTorrentsRecursive(torrents, downloadsDirectory);
		}
		else {
			Log.e(TAG, "unable to access storage");
		}
    }
    
	/** recursive search for torrents **/
    private List<Tor> getTorrentsRecursive(List<Tor> tors, File directory) {
        File[] fileList = directory.listFiles(filterDirsTors);
		
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
		String[] allPaths = new String[torrents.size()];
		//int rootLen = downloadsDirectory.getAbsolutePath().length();
		
		for (int i=0; i<allPaths.length; i++) {
			allPaths[i] = torrents.get(i).getFilePath()
			        .replaceFirst(downloadsDirectory.getAbsolutePath(), "");
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
