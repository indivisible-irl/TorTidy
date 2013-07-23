package com.indivisible.tortidy.storage;

import android.content.*;
import android.util.*;
import com.indivisible.tortidy.prefs.*;
import java.util.*;

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
	private void loadLabels() {
		String rawLabels = prefs.getLabels();
		String[] splitLabels = rawLabels.split(LABEL_DIV);
		labels = new ArrayList<String>(Arrays.asList(splitLabels));
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
}
