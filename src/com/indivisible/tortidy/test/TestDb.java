package com.indivisible.tortidy.test;
import android.app.*;
import com.indivisible.tortidy.database.*;
import com.indivisible.tortidy.*;
import android.os.*;
import java.util.*;
import android.widget.*;
import android.view.*;
import java.io.*;

public class TestDb extends ListActivity
{
	private LabelsDataSource labels;
	private TorrentsDataSource tors;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_db_labels);

		labels = new LabelsDataSource(this.getApplicationContext());
		labels.openWriteable();
		tors = new TorrentsDataSource(this.getApplicationContext());
		tors.openWriteable();

//		List<Label> values = labels.getAllLabels();
//		ArrayAdapter<Label> adapter = new ArrayAdapter<Label>(
//				this.getApplicationContext(),
//				android.R.layout.simple_list_item_1,
//				values);
		List<Torrent> values = tors.getAllTorrents();
		ArrayAdapter<Torrent> adapter = new ArrayAdapter<Torrent>(
				this.getApplicationContext(),
				android.R.layout.simple_list_item_1,
				values);
		
		setListAdapter(adapter);
	}
	
	public void onClick(View view) {
		@SuppressWarnings("unchecked")
		
		//ArrayAdapter<Label> adapter = (ArrayAdapter<Label>) getListAdapter();
		Label label = null;
		ArrayAdapter<Torrent> adapter = (ArrayAdapter<Torrent>) getListAdapter();
		Torrent tor = null;
		
		switch (view.getId()) {
			
			case R.id.bAdd:
				String[] titles = new String[] { "Cool", "Very nice", "Hate it" };
				int nextInt = new Random().nextInt(3);
				
				label = labels.createLabel(titles[nextInt], true);
				//adapter.add(label);
				tor = tors.createTorrent(titles[nextInt], new File("."), label);
				adapter.add(tor);
				break;
				
			case R.id.bDelete:
				if (getListAdapter().getCount() > 0) {
					//label = (Label) getListAdapter().getItem(0);
					//labels.deleteLabel(label);
					//adapter.remove(label);
					tor = (Torrent) getListAdapter().getItem(0);
					tors.deleteTorrent(tor);
					adapter.remove(tor);
				}
				break;
		}
		adapter.notifyDataSetChanged();
	}
	
	@Override
	protected void onResume() {
		labels.openWriteable();
		tors.openWriteable();
		super.onResume();
	}

	@Override
	protected void onPause() {
		labels.close();
		tors.close();
		super.onPause();
	}
}
