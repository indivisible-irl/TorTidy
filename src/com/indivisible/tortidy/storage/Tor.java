package com.indivisible.tortidy.storage;
import java.io.*;

/* Class to contain a torrent file */
public class Tor
{
    private File file;
	private Label label;
	
	//TODO future torrent internals handling
	
	public Tor(File torFile, Label lab) {
		file = torFile;
		label = lab;
		
	}
	
	/** returns torrent filename **/
	public String getFilename() {
		return file.getName();
	}
	
	/** returns torrent's full path **/
	public String getFilePath() {
		return file.getAbsolutePath();
	}
	
	/** return the torrent's actual File **/
	public File getFile() {
		return file;
	}
	
	/** get the label **/
	public Label getLabel() {
		return label;
	}
	
	/** set the label **/
	public void setLabel(Label lab) {
		label = lab;
	}
}
