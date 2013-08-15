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
	
//// fields
	
	private Button bAdd;
	private ListView lvLabels;
	
	private LabelsDataSource labels;
	private ArrayAdapter adapter;
	
//// activity methods
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.label_manager);
		
		// labels:
		labels = new LabelsDataSource(this.getApplicationContext());
		labels.openWriteable();
		
		// ui
		initInterface();	//must init interface first
		setList();
		
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
	
//// initialisation methods
	
	/** initialise the ui elements **/
	private void initInterface() {
		bAdd     = (Button) findViewById(R.id.bLabelAdd);
		lvLabels = (ListView) findViewById(android.R.id.list);
	}
	
	/** populate ListView in normal mode **/
	private void setList() {
		List<Label> labelList = labels.getAllLabels();
		//NB made adapter a class var, performance/mem issue?
		adapter = new ArrayAdapter<Label>(
			this.getApplicationContext(),
			android.R.layout.simple_list_item_1,
			labelList);
		setListAdapter(adapter);
	}
	
//// interaction handling
	
	/** onClick method **/  //(use xml call or listeners?)
	public void onClick(View view) {
		
		switch (view.getId()) {
			case R.id.bLabelAdd:
				break;
			default:
				Toast.makeText(this,
						"unhandled click:\n" +view.getTag(),
						Toast.LENGTH_SHORT).show();
				break;
		}//end switch
	}
	
	
	
//// dialogs or Activity calls
	
	/** Open a dialog to ask user for a new label to save **/
	private String getNewLabel() {
		// Dialog newLabelDialog = new Dialog();
		
		return null;
	}
	
	private int[] deleteLabelsQuestion() {
		
		
		return null;
	}
	
	
	
}
