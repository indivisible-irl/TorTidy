package com.indivisible.tortidy.database;
import android.database.sqlite.*;
import android.util.*;
import android.content.*;

public class TorrentsHelper extends SQLiteOpenHelper
{
	private static final String TAG = "com.indivisible.tortidy";
	
	//TODO move db name and version to a common class
	public static final String DATABASE_NAME = "TorTidyDb";
	public static final int DATABASE_VERSION = 1;
	public static final String TABLE_TORRENTS = "torrents";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_TITLE = "title";
	public static final String COLUMN_FILEPATH = "filepath";
	public static final String COLUMN_LABEL_ID = "label_id";
	
	
	/** constructor for the db's torrents table **/
	public TorrentsHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	/** create a new, empty table for torrents**/
	public void onCreate(SQLiteDatabase db)
	{
		Log.i(TAG, "creating torrents table...");
		String create = createStatement();
		db.execSQL(create);
	}

	/** drop and recreate torrents table on new version **/
	public void onUpgrade(SQLiteDatabase db, int oldVerNum, int newVerNum)
	{
		Log.i(TAG, "upgrading torrents table from " +oldVerNum+ " to " +newVerNum+ "...");
		Log.w(TAG, "existing data will be deleted");

		db.execSQL("drop table if exists " +TABLE_TORRENTS+ ";");
		onCreate(db);
	}
	
	/** generate the sql statement to create the torrents table in the db **/
	private String createStatement() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("create table ").append(TABLE_TORRENTS);
		sb.append(" (");
		sb.append(COLUMN_ID).append(" INTEGER primary key autoincrement, ");
		sb.append(COLUMN_LABEL_ID).append(" INTEGER, ");
		sb.append(COLUMN_TITLE).append(" TEXT not null, ");
		sb.append(COLUMN_FILEPATH).append(" TEXT ");
		sb.append(");");

		//TODO set foreign key for label id
		return sb.toString();
	}
	
	
}
