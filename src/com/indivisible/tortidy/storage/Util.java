package com.indivisible.tortidy.storage;
import java.io.*;
import android.content.*;
import java.util.*;
import com.indivisible.tortidy.database.*;

public class Util
{
	
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
}
