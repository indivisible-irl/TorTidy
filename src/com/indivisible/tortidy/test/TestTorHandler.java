package com.indivisible.tortidy.test;
import android.app.*;
import android.os.*;
import android.widget.*;
import com.indivisible.tortidy.storage.*;
import java.io.*;

public class TestTorHandler extends ListActivity
{

    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		TorrentCollection tors = new TorrentCollection(this.getApplicationContext());
		tors.populateLists();
		String[] torPaths = tors.allTorPaths();
		
		setListAdapter(new ArrayAdapter<String>(
						   TestTorHandler.this, android.R.layout.simple_list_item_1, torPaths));
	}
}
