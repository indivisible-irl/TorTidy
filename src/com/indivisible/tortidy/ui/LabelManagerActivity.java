package com.indivisible.tortidy.ui;

import android.app.*;
import android.os.*;
import com.indivisible.tortidy.*;
import com.indivisible.tortidy.database.*;
import java.util.*;
import android.widget.*;
import android.view.*;

public class LabelManagerActivity extends ListActivity
{
	private static final String TAG = "com.indivisible.tortidy";
	
	private Button bAddOrConfirm;
	private Button bDeleteOrCancel;
	
	private LabelsDataSource labels;
	private boolean isDeleteMode;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.label_manager);
		
		isDeleteMode = false;
		
		// buttons:
		bAddOrConfirm   = (Button) findViewById(R.id.bLabelAddOrConfirm);
		bDeleteOrCancel = (Button) findViewById(R.id.bLabelDeleteOrCancel);
		
		// labels:
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
	
	//// onClick (use xml call or listeners?)
	public void onClick(View view) {
		
		if(isDeleteMode) {
			switch (view.getId()) {
				case R.id.bLabelAddOrConfirm:
					break;
				case R.id.bLabelDeleteOrCancel:
					isDeleteMode = false;
					break;
			}
		}
		else {
			switch (view.getId()) {
				case R.id.bLabelAddOrConfirm:
					break;
			
				case R.id.bLabelDeleteOrCancel:
					isDeleteMode = true;
					break;
			}
		}
		
	}
	
}
