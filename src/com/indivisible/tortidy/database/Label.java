package com.indivisible.tortidy.database;

/** class to represent a torrent's label **/
public class Label implements Comparable<Label>
{

//// data
	
	private long id;
	private String title;
	private boolean isExistingLabel;

//// constructors
	
	/** create a new, blank label **/
	public Label() {
		// default constructor
	}
	
	/** create a new label **/
	public Label(long id, String labelTitle, boolean exists) {
		this.id = id;
		title = labelTitle;
		isExistingLabel = exists;
	}
	
//// gets and sets

	/** get the label's db id **/
	public long getId() {
		return id;
	}
	/** set the label's db id **/
	public void setId(long id) {
		this.id = id;
	}
	
	/** returns a label's title **/
	public String getTitle() {
		return title;
	}
	/** sets a label's title **/
	public void setTitle(String labelTitle) {
		title = labelTitle;
	}

	/** check if a label already exists **/
	public Boolean exists() {
		return isExistingLabel;
	}
	/** set if a label exists **/
	public void setExists(Boolean exists) {
		isExistingLabel = exists;
	}

//// methods
	
	@Override
	public String toString() {
		if (isExistingLabel)
			return "1:" +title;
		return "0:" +title;
	}
	
	/** sort on label titles **/
	public int compareTo(Label label) {
		return title.compareTo(label.getTitle());
	}
	
}
