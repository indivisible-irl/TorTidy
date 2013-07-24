package com.indivisible.tortidy.storage;

import android.content.*;
import android.util.*;
import com.indivisible.tortidy.prefs.*;
import java.util.*;

/** class to take care of the labels **/
public class LabelsHandler
{
    private static final String TAG = "com.indivisible.tortidy";
	private static final String LABEL_DIV = ",";
	
	private Preferences prefs;
	private List<String> labels;
	
	public LabelsHandler(Context ctx) {
		prefs = new Preferences(ctx);
		loadLabels();
	}
	
	
	/** grab saved labels from shared preferences **/
	public void loadLabels() {
		String rawLabels = prefs.getLabelsRaw();
		String[] splitLabels = rawLabels.split(LABEL_DIV);
		
		labels = new ArrayList<String>();
		for (String rawLabel : splitLabels) {
			labels.add(rawLabel.trim());
		}
	}
	
	/** save current labels to preferences **/
	public void saveLabels() {
		if (testLabels(labels)) {
	    	prefs.setLabelsRaw(getRawString());
		}
		else {
			Log.e(TAG, "failed to save labels: '" +getRawString()+ "'");
		}
	}
	
	/** convert list of labels to a String **/
	public String getRawString() {
		StringBuilder sb = new StringBuilder();
		for (String label : labels) {
			sb.append(label).append(LABEL_DIV);
		}
		String labelsExcess = sb.toString();
		// remove trailing seperator
		return labelsExcess.substring(0,labelsExcess.lastIndexOf(LABEL_DIV));
	}
	
	/** get labels as a String array **/
	public String[] getLabelsArray() {
		String[] labelsArray = new String[labels.size()];
		for (int i=0; i<labelsArray.length; i++) {
			labelsArray[i] = labels.get(i);
		}
		return labelsArray;
	}
	
	/** test labels in group string format for errors **/
	private boolean testLabels(String strLabels) {
		if (containsWhitespace(strLabels)) {
			return false;
		}
		else {
			return true;
		}
	}
	
	/** test labels in list format for errors **/
	private boolean testLabels(List<String> listLabels) {
		for (String label : listLabels) {
			if (containsWhitespace(label)) {
				return false;
			}
		}
		return true;
	}
	
	/** test a string for whitespace **/
	private boolean containsWhitespace(String str) {
		if (str.contains("\\s")) {
			Log.w(TAG, "Label(s) contain whitespace '" +str+ "'");
			return true;
		}
		else {
			return false;
		}
	}
	
	/** test if supplied string is a label **/
	public boolean isLabel(String testingLabel) {
		return labels.contains(testingLabel);
	}
}
