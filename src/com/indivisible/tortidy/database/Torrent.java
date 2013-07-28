package com.indivisible.tortidy.database;
import java.io.*;

public class Torrent
{

//// data

	private long id;
	private String title;
	private File file;
	private Label label;
	
//// constructor
	
	public Torrent() {
		// default constructor
	}
	
//// gets and sets
	
	public long getId() {
		return id;
	}
	public void setId(long i) {
		id = i;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String t) {
		title = t;
	}
	
	public File getFile() {
		return file;
	}
	public void setFile(File f) {
		file = f;
	}
	
	public Label getLabel() {
		return label;
	}
	public void setLabel(Label l) {
		label = l;
	}
	
	
	@Override
	public String toString() {
		return title;
	}
}
