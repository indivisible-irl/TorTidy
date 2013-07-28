package com.indivisible.tortidy.database;
import android.database.sqlite.*;
import android.content.*;
import android.database.*;
import com.indivisible.tortidy.database.*;
import android.util.*;
import java.util.*;

/** DAO class for working with labels **/
public class LabelsDataSource
{

//// data
	
	private static final String TAG = "com.indivisible.tortidy";
	private SQLiteDatabase db;
	private LabelsHelper labelsHelper;
	private static final String[] allColumns = {
			LabelsHelper.COLUMN_ID,			// 0
			LabelsHelper.COLUMN_TITLE,		// 1
			LabelsHelper.COLUMN_EXISTS};	// 2
	
//// constructor
	
	public LabelsDataSource(Context context) {
		labelsHelper = new LabelsHelper(context);
	}
	
//// open and close
	
	/** open the db in a readonly state **/
	public void openReadable() throws SQLiteException {
		db = labelsHelper.getReadableDatabase();
	}
	
	/** open the db in a writeable state **/
	public void openWriteable() throws SQLiteException {
		db = labelsHelper.getWritableDatabase();
	}
	
	/** closes an open db connection **/
	public void close() {
		labelsHelper.close();
	}
	
//// CRUD operations
	
	/** create, save and return a new label. returns null on error **/
	public Label createLabel(String title, boolean exists) {
		ContentValues values = new ContentValues();
		values.put(LabelsHelper.COLUMN_TITLE, title);
		values.put(LabelsHelper.COLUMN_EXISTS, booleanToInt(exists));
		
		long newId = db.insert(
				LabelsHelper.TABLE_LABELS,
				null,
				values);
				
		return getLabel(newId);
	}
	
	public Label getLabel(long labelId) {
		Cursor cursor = db.query(
			LabelsHelper.TABLE_LABELS,
			allColumns,
			LabelsHelper.COLUMN_ID +" = "+ labelId,
			null, null, null, null);

		// convert cursor result
		try {
			if (cursor.moveToFirst()) {
				Label label = cursorToLabel(cursor);
				Log.d(TAG, "created label: " +label);
				return label;
			}
			else {
				Log.e(TAG, "unable to retrieve saved label. id: " +labelId+ ", title: ?");
				return null;
			}
		}
		finally {
			cursor.close();
		}
	}
	
	/** retrieve all labels from the db **/
	public List<Label> getAllLabels() {
		List<Label> allLabels = new ArrayList<Label>();
		
		Cursor cursor = db.query(
				LabelsHelper.TABLE_LABELS,
				allColumns,
				null, null, null, null, null);
		
		if (cursor.moveToFirst()) {
			while (!cursor.isAfterLast()) {
				Label label = cursorToLabel(cursor);
				allLabels.add(label);
				cursor.moveToNext();
			}
		}
		else {
			Log.w(TAG, "getAllLabels: no labels were found");
		}
		
		cursor.close();
		Log.i(TAG, "num of all labels: " +allLabels.size());
		return allLabels;
	}
	
	/** delete a single label. returns num of rows affected **/
	public int deleteLabel(Label label) {
		long id = label.getId();
		Log.w(TAG, "deleting label: " +label);
		int numRowsAffected = db.delete(
				LabelsHelper.TABLE_LABELS,
				LabelsHelper.COLUMN_ID +" = "+ id,
				null);
		return numRowsAffected;
	}
	
//// methods
	
	/** get a label object out of a cursor row **/
	private Label cursorToLabel(Cursor cursor) {
		Label label = new Label();
		label.setId(cursor.getLong(0));
		label.setTitle(cursor.getString(1));
		label.setExists(intToBoolean(cursor.getInt(2)));
		return label;
	}
	
	/** convert a boolean to an int (1 or 0) **/
	private int booleanToInt(boolean existsBool) {
		if (existsBool)
			return 1;
		return 0;
	}
	/** convert an int to a boolean (0 or other) **/
	private boolean intToBoolean(int existsInt) {
		if (existsInt == 0)
			return false;
		return true;
	}
}
