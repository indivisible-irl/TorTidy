package com.indivisible.tortidy.test;
import android.app.*;
import android.os.*;
import android.util.*;
import android.widget.*;
import com.indivisible.tortidy.storage_old.*;
import java.io.*;

public class TestTorHandler extends ListActivity
{
    private static final String TAG = "com.indivisible.tortidy";
	
    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		TorrentCollection tors = new TorrentCollection(this.getApplicationContext());
		tors.populateLists();
		String[] testItems = tors.allTorPaths();
		
		//LabelsHandler lh = new LabelsHandler(getApplicationContext());
		//String[] testItems = lh.getLabelsArray();
		
		
		// fill list with 'testItems'
		Log.i(TAG, "testItems contains: " +testItems.length);
		if (testItems.length > 0)
			Log.i(TAG, "Item 0: '" +testItems[0]+ "'");
		setListAdapter(new ArrayAdapter<String>(
						   TestTorHandler.this, android.R.layout.simple_list_item_1, testItems));
	}
}
