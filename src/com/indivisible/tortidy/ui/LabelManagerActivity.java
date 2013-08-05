package com.indivisible.tortidy.ui;

import android.app.*;
import android.os.*;
import com.indivisible.tortidy.*;
import com.indivisible.tortidy.database.*;
import java.util.*;
import android.widget.*;

public class LabelManagerActivity extends ListActivity
{
	private static final String TAG = "com.indivisible.tortidy";
	
	private LabelsDataSource labels;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.label_manager);
		
		labels = new LabelsDataSource(this.getApplicationContext());
		labels.openWriteable();
		
		List<Label> labelList = labels.getAllLabels();
		ArrayAdapter adapter = new ArrayAdapter<Label>(
				this.getApplicationContext(),
				android.R.layout.simple_list_item_1,
				labelList);
		setListAdapter(adapter);
	}
	
	@Override
	protected void onResume() {
		labels.openWriteable();
		super.onResume();
	}

	@Override
	protected void onPause() {
		labels.close();
		super.onPause();
	}
	
}
