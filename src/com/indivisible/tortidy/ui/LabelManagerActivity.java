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
	
	private Button bAdd;
	private ListView lvLabels;
	
	private LabelsDataSource labels;
	
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
	
	/** initialise the ui elements **/
	private void initInterface() {
		bAdd     = (Button) findViewById(R.id.bLabelAdd);
		lvLabels = (ListView) findViewById(android.R.id.list);
	}
	
	/** populate ListView in normal mode **/
	private void setList() {
		List<Label> labelList = labels.getAllLabels();
		ArrayAdapter adapter = new ArrayAdapter<Label>(
			this.getApplicationContext(),
			android.R.layout.simple_list_item_1,
			labelList);
		setListAdapter(adapter);
	}
	
	
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
	
}
