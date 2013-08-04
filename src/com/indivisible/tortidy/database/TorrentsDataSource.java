package com.indivisible.tortidy.database;
import android.database.sqlite.*;
import android.content.*;
import java.io.*;
import android.database.*;
import android.util.*;
import java.util.*;
import com.indivisible.tortidy.storage.*;

public class TorrentsDataSource
{

//// data

	private static final String TAG = "com.indivisible.tortidy";
	private SQLiteDatabase db;
	private TorrentsHelper torrentsHelper;
	private LabelsDataSource labels;
	
	private static final String[] allColumns = {
		TorrentsHelper.COLUMN_ID,			// 0
		TorrentsHelper.COLUMN_TITLE,		// 1
		TorrentsHelper.COLUMN_FILEPATH,		// 2
		TorrentsHelper.COLUMN_LABEL_ID};	// 3


//// constructor

	public TorrentsDataSource(Context context) {
		torrentsHelper = new TorrentsHelper(context);
		labels = new LabelsDataSource(context);
	}

//// open and close

	/** open the db in a readonly state **/
	public void openReadable() throws SQLiteException {
		db = torrentsHelper.getReadableDatabase();
	}

	/** open the db in a writeable state **/
	public void openWriteable() throws SQLiteException {
		db = torrentsHelper.getWritableDatabase();
	}

	/** closes an open db connection **/
	public void close() {
		torrentsHelper.close();
	}

//// CRUD operations

	/** create, save and return a new label. returns null on error **/
	public Torrent createTorrent(String title, File file, Label label) {
		ContentValues values = new ContentValues();
		values.put(TorrentsHelper.COLUMN_TITLE, title);
		values.put(TorrentsHelper.COLUMN_FILEPATH, file.getAbsolutePath());
		values.put(TorrentsHelper.COLUMN_LABEL_ID, label.getId());

		// save new comment
		long newId = db.insert(
			TorrentsHelper.TABLE_TORRENTS,
			null,
			values);

		return getTorrent(newId);
	}
	
	/** retrieve a Torrent from its id **/
	public Torrent getTorrent(long torId) {
		Cursor cursor = db.query(
			TorrentsHelper.TABLE_TORRENTS,
			allColumns,
			TorrentsHelper.COLUMN_ID +" = "+ torId,
			null, null, null, null);
			
		try {
			if (cursor.moveToFirst()) {
				Torrent tor = cursorToTorrent(cursor);
				Log.d(TAG, "created torrent: " +tor);
				return tor;
			}
			else {
				Log.e(TAG, "unable to retrieve saved tor. id: " +torId+ ", title: ?");
				return null;
			}
		}
		finally {
			cursor.close();
		}
	}
	
	/** retrieve a torrent from its title **/
	public Torrent getTorrent(String title) {
		Cursor cursor = db.query(
			TorrentsHelper.TABLE_TORRENTS,
			allColumns,
			TorrentsHelper.COLUMN_TITLE +" = "+ title,
			null, null, null, null);

		try {
			if (cursor.moveToFirst()) {
				Torrent tor = cursorToTorrent(cursor);
				Log.d(TAG, "created torrent: " +tor);
				return tor;
			}
			else {
				Log.e(TAG, "unable to retrieve saved tor. title: " +title);
				return null;
			}
		}
		finally {
			cursor.close();
		}
	}

	/** retrieve Torrent based on title or create new if not exists **/
	public Torrent getOrCreateTorrent(File torFile, File root) {
		//TODO change title to internal name
		String title = torFile.getName();
		Torrent tor = getTorrent(title);
		
		if (tor == null) {
			Label label = labels.getOrCreateLabel(Util.getLabelFromLocation(torFile, root));
			tor = getOrCreateTorrent(torFile, root);
		}
		
		return tor;
	}
	
	public Torrent updateOrCreateTorrent(Torrent newTor) {
		return null;
	}
	
	/** retrieve all labels from the db **/
	public List<Torrent> getAllTorrents() {
		List<Torrent> allTors = new ArrayList<Torrent>();

		Cursor cursor = db.query(
			TorrentsHelper.TABLE_TORRENTS,
			allColumns,
			null, null, null, null, null);

		if (cursor.moveToFirst()) {
			while (!cursor.isAfterLast()) {
				Torrent tor = cursorToTorrent(cursor);
				allTors.add(tor);
				cursor.moveToNext();
			}
		}
		else {
			Log.w(TAG, "getAllLabels: no labels were found");
		}

		cursor.close();
		Log.i(TAG, "num of all torrents: " +allTors.size());
		return allTors;
	}

	/** update a Torrent's entry. returns successful if exists and saved **/
	public boolean updateTorrent(Torrent newTor) {
		Torrent oldTor = getTorrent(newTor.getTitle());
		
		ContentValues values = new ContentValues();
		values.put(TorrentsHelper.COLUMN_TITLE, newTor.getTitle());
		values.put(TorrentsHelper.COLUMN_FILEPATH, newTor.getFile().getAbsolutePath());
		values.put(TorrentsHelper.COLUMN_LABEL_ID, newTor.getLabel().getId());
		
		int numRowsAffected = db.update(
				TorrentsHelper.TABLE_TORRENTS,
				values,
				TorrentsHelper.COLUMN_ID+ " = " +newTor.getId(),
				null);
		Log.d(TAG, "update: rows affected = " +numRowsAffected);
		
		if (numRowsAffected == 0)
			return false;
		return true;
	}
	
	/** delete a single label. returns num of rows affected **/
	public int deleteTorrent(Torrent tor) {
		long id = tor.getId();
		Log.w(TAG, "deleting torrent: " +tor);
		int numRowsAffected = db.delete(
			TorrentsHelper.TABLE_TORRENTS,
			TorrentsHelper.COLUMN_ID +" = "+ id,
			null);
		return numRowsAffected;
	}

//// methods

	/** get a label object out of a cursor row **/
	private Torrent cursorToTorrent(Cursor cursor) {
		Torrent tor = new Torrent();
		tor.setId(cursor.getLong(0));
		tor.setTitle(cursor.getString(1));
		tor.setFile(new File(cursor.getString(2)));
		
		labels.openReadable();
		tor.setLabel(labels.getLabel(cursor.getLong(3)));
		labels.close();
		
		return tor;
	}

	

}
