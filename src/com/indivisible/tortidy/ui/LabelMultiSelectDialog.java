package com.indivisible.tortidy.ui;

import com.indivisible.tortidy.R;
import com.indivisible.tortidy.R.layout;
import com.indivisible.tortidy.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class LabelMultiSelectDialog extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_label_multi_select);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.label_multi_select_dialog, menu);
		return true;
	}

}
