package com.indivisible.tortidy.storage;

/* class to represent a torent's label */
public class Label
{
    private String title;
	private Boolean isExistingLabel;
	
	/* create a new label. exists set to null */
	public Label(String labelTitle) {
		title = labelTitle;
		isExistingLabel = null;
	}
	
	/* create a new label */
	public Label(String labelTitle, Boolean exists) {
		title = labelTitle;
		isExistingLabel = exists;
	}
	
	
	/* returns a label's title */
	public String getTitle() {
		return title;
	}
	/* sets a label's title */
	public void setTitle(String labelTitle) {
		title = labelTitle;
	}
	
	/* check if a label already exists */
	public Boolean getExists() {
		return isExistingLabel;
	}
	/* set if a label exists */
	public void setExists(Boolean exists) {
		isExistingLabel = exists;
	}
}
