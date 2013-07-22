package com.indivisible.tortidy.storage;
import java.io.*;

/* Class to contain a torrent file */
public class Tor
{
    private File file;
	String label;
	// boolean isUploaded; // just infer from location?
	
//	String title;
//	String tracker;
//	long size;
	// future torrent internal handling?
	
	public Tor(File torFile) {
		file = torFile;
		//TODO parse info from file
	}
	
//	public void setLabel(String torLabel) {
//		label = torLabel;
//	}
//	public String getLabel() {
//		return label;
//	}
	
	/** returns torrent filename **/
	public String getFilename() {
		return file.getName();
	}
	
	/** returns torrent's full path **/
	public String getFilePath() {
		return file.getAbsolutePath();
	}
	
}
