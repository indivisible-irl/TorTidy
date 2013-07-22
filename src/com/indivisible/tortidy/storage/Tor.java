package com.indivisible.tortidy.storage;
import java.io.*;

/* Class to contain a torrent file */
public class Tor
{
    File file;
	String label;
	boolean isUploaded; // just infer from location?
	
	//String title;
	//String tracker;
	//long size;
	// future torrent internal handling?
	
	public Tor(File torFile) {
		file = torFile;
		//TODO parse info from file
	}

	public void setIsUploaded(boolean isUploaded) {
		this.isUploaded = isUploaded;
	}
	public boolean isUploaded() {
		return isUploaded;
	}
	
	public void setLabel(String torLabel) {
		label = torLabel;
	}
	public String getLabel() {
		return label;
	}
	
	
}
