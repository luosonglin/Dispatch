package com.songlin.luo.sqlite;

import android.provider.BaseColumns;

public class DbNews {

//	private static final String AUTHORITY = "com.kf.util.dbprovider";
	
	public static final class News implements BaseColumns{
		
//		public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);
		public static final String _ID = "_id";
		public static final String TITLE = "title";
		public static final String BODY = "body";
		public static final String URL = "url";
		
		public long id;
		public String title;
		public String body;
		public String url;
		
		public String toString(){
			return _ID + " = " + id + ","
				+ TITLE + " = " + title + ","
				+ BODY + " = " + body + ","
				+ URL + " = " + url;
		}
		
		public String[] toStrings(){
			return new String[]{title, body + "\n See: " +url};
		}
	}
}
