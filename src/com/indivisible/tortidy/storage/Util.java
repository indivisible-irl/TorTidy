package com.indivisible.tortidy.storage;
import java.io.*;
import android.content.*;
import java.util.*;
import com.indivisible.tortidy.database.*;
import android.util.*;

public class Util
{
	private static final String TAG = "com.indivisible.tortidy";
	
	/** get a label name from torrent's folder location **/
	public static String getLabelFromLocation(File tor, File root) {
		File torParent = tor.getParentFile();
		
		if (root.equals(torParent)) {
			return null;
		}
		else {
			return torParent.getName();
		}
	}
	
	public static boolean ensureLabelFolders(Context ctx, List<Label> labels) {
		for (Label label : labels) {
			
		}
		return false;
	}
	
	public static boolean ensureLabelFolderExists(Context ctx, Label label) {
		
		return false;
	}
	
	
//	public static boolean moveFile(File tor, File destination) {
//		tor.re
//	}
	
	
	public static int boolToInt(boolean bool) {
		if (bool)
			return 1;
		return 0;
	}
	
	public static boolean intToBool(int i) {
		if (i == 0)
			return false;
		else if (i == 1)
			return true;
		else {
			Log.e(TAG, "intToBool invalid int: " +i);
			return false;
		}
	}
}
