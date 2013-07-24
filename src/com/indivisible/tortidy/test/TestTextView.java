package com.indivisible.tortidy.test;
import android.app.*;
import android.os.*;
import android.widget.*;
import com.indivisible.tortidy.*;

public class TestTextView extends Activity
{
    TextView textView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		textView = (TextView) findViewById(R.id.main_text);
		
		
		String text = getExtDir();
		
		
		textView.setText(text);
		
	}
	
	
	String getExtDir() {
		return Environment.getExternalStorageDirectory().getAbsolutePath();
	}
}
