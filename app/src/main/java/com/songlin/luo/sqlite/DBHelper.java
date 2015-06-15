package com.songlin.luo.sqlite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.songlin.luo.sqlite.DbNews.News;

public class DBHelper {
	
//	private static final String CLASSNAME = DBHelper.class.getSimpleName();
	private static final String[] COLS = new String[]{
		News._ID, News.TITLE, News.BODY, News.URL
	};
	private SQLiteDatabase db;
	private DBOpenHelper dbOpenHelper;
	
	public DBHelper(Context context){
		this.dbOpenHelper = new DBOpenHelper(context);
//		Log.v("aaaa", "aaaa->dbOpenHelper = " + dbOpenHelper);
		establishDb();
	}
	
	private void establishDb(){
		if(this.db == null){
			this.db = this.dbOpenHelper.getWritableDatabase();
		}
//		Log.v("aaaa", "aaaa->db = " + db);
	}
	
	public void cleanup(){
		if(this.db != null){
			this.db.close();
			this.db = null;
		}
	}
	
	public long Insert(News news){
		ContentValues values = new ContentValues();
		values.put(News.TITLE, news.title);
		values.put(News.BODY, news.body);
		values.put(News.URL, news.url);
		return this.db.insert(DBOpenHelper.TABLE_NAME, null, values);
	}
	
	public long update(News news){
		ContentValues values = new ContentValues();
		values.put(News.TITLE, news.title);
		values.put(News.BODY, news.body);
		values.put(News.URL, news.url);
		return this.db.update(DBOpenHelper.TABLE_NAME, values, News._ID + "=" + news.id, null);
	}
	
	public int delete(long id){
		return this.db.delete(DBOpenHelper.TABLE_NAME, News._ID + "=" + id, null);
	}
	
	public int delete(String title){
		return this.db.delete(DBOpenHelper.TABLE_NAME, News.TITLE + "like " + title, null);
	}
	
	public News queryByID(long id){
		Cursor cursor = null;
		News news = null;
		try {
			cursor = this.db.query(DBOpenHelper.TABLE_NAME, 
					COLS, News._ID + "=" + id, null, null, null, null);
			if(cursor.getCount() > 0){
				cursor.moveToFirst();
				news = new News();
				news.id = cursor.getLong(0);
				news.title = cursor.getString(1);
				news.body = cursor.getString(2);
				news.url = cursor.getString(3);
			}
		} catch (SQLException e) {
			Log.v("aaaa", "aaaa->queryByID.SQLException");
		} finally{
			if(cursor != null && !cursor.isClosed()){
				cursor.close();
			}
		}
		return news;
	}
	
	public List<News> queryByTitleForList(String title){
		ArrayList<News> list = new ArrayList<DbNews.News>();
		Cursor cursor = null;
		News news = null;
		try {
			cursor = this.db.query(true, DBOpenHelper.TABLE_NAME, 
					COLS, News.TITLE + " like '%" + title + "%'", null, null, null, null, null);
			int count = cursor.getCount();
			cursor.moveToFirst();
			for(int i=0;i<count;i++){
				news = new News();
				news.id = cursor.getLong(0);
				news.title = cursor.getString(1);
				news.body = cursor.getString(2);
				news.url = cursor.getString(3);
				list.add(news);
				cursor.moveToNext();
			}
		} catch (SQLException e) {
			Log.e("aaaa", "aaaa->queryByTitle.SQLException");
			e.printStackTrace();
		}finally{
			if(cursor != null && !cursor.isClosed())
				cursor.close();
		}
		return list;
	}

	public List<News> queryAllForList(){
		ArrayList<News> list = new ArrayList<DbNews.News>();
		Cursor cursor = null;
		News news = null;
		try {
			cursor = this.db.query(DBOpenHelper.TABLE_NAME, 
					COLS, null, null, null, null, null);
			int count = cursor.getCount();
			cursor.moveToFirst();
			for(int i=0;i<count;i++){
				news = new News();
				news.id = cursor.getLong(0);
				news.title = cursor.getString(1);
				news.body = cursor.getString(2);
				news.url = cursor.getString(3);
				list.add(news);
				cursor.moveToNext();
			}
		} catch (SQLException e) {
			Log.e("aaaa", "aaaa->queryByTitle.SQLException");
			e.printStackTrace();
		}finally{
			if(cursor != null && !cursor.isClosed())
				cursor.close();
		}
		return list;
	}
	
	public Cursor queryByTitleForCursor(String title){
		Cursor cursor = null;
		try {
			cursor = this.db.query(DBOpenHelper.TABLE_NAME, 
					COLS, null, null, null, null, null);
		} catch (SQLException e) {
			Log.e("aaaa", "aaaa->queryByTitle.SQLException");
			e.printStackTrace();
		}finally{
//			if(cursor != null && !cursor.isClosed())
//				cursor.close();
		}
		return cursor;
	}
	
	public Cursor queryAllForCursor(){
		Cursor cursor = null;
		try {
			cursor = this.db.query(DBOpenHelper.TABLE_NAME, 
					COLS, null, null, null, null, null);
		} catch (SQLException e) {
			Log.e("aaaa", "aaaa->queryByTitle.SQLException");
			e.printStackTrace();
		}
		return cursor;
	}
	
	private static class DBOpenHelper extends SQLiteOpenHelper{
		private static final String DB_NAME = "db_news";
		private static final String TABLE_NAME = "news";
		private static final int DB_VERSION = 1;
		private static final String CREATE_TABLE = "create table "+TABLE_NAME+" ("+News._ID+" integer primary key," +
				News.TITLE + " text, "+News.BODY+" text, "+News.URL+" text)";
		private static final String DROP_TABLE = "drop table if exists "+TABLE_NAME;
		 
		public DBOpenHelper(Context context){
			super(context, DB_NAME, null, DB_VERSION);
		}
		
		public DBOpenHelper(Context context, String name, CursorFactory factory,
				int version) {
			super(context, name, factory, version);
		} 

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			try {
				db.execSQL(CREATE_TABLE);
			} catch (SQLException e) {
				// TODO: handle exception
				Log.v("aaaa", "aaaa->Database created failed.");
			}
			saveSomeDatas(db, getData());
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			Log.v("aaaa", "aaaa->onUpgrade, oldVersion = " + oldVersion + ", newVersion = " + newVersion);
			try {
				db.execSQL(DROP_TABLE);
			} catch (SQLException e) {
				// TODO: handle exception
				Log.v("aaaa", "aaaa->Database created failed.");
			}
			onCreate(db);
		}
		
		private void saveSomeDatas(SQLiteDatabase db, List<Map<String, String>> value){
			ContentValues values = null;
			Map<String, String> map = null;
			while(value.size() > 0){
				map = value.remove(0);
				values = new ContentValues();
				values.put(News.TITLE, map.get(News.TITLE));
				values.put(News.BODY, map.get(News.BODY));
				values.put(News.URL, map.get(News.URL));
				db.insert(TABLE_NAME, null, values);
			}
		}
		
		private List<Map<String, String>> getData(){
			List<Map<String, String>> list = new ArrayList<Map<String,String>>();
			Map<String, String> map1 = new HashMap<String, String>();
			map1.put(News.TITLE, "���ѧ����Ҫ�����ʮ�ֻᲢ�ɻ�� У���Ʒ�ǿ��");
			map1.put(News.BODY, "���й�֮����������š����������������ϡ������ȵص����ڸ���������㲥��̨��������400-800-0088�����绰��ӳ�����ǵĺ������ڶ�Сѧ������ѧУҪ���Ӽ����ʮ�ֻᣬ��Ҫ���ɻ�ѡ���Сѧ�������ʮ�ֻ��Ƿ������Ĺ涨��������Ǯ����Щʲô����ӳ����ļҳ����ǵĺ��Ӷ���Сѧ����С���϶��꼶����һ��������꼶��ͬ����Ҫ���ʮ�ֻᣬ���Ǹ��ص������Щ���졣������һλ�ҳ������ĺ������������ռ��������������һ����ѧУ�϶��꼶������ѧ�����û���һ�����������Ҫ��ҳ����ź��������������Ҫ�ҳ�ǩ�֡���λ�ҳ�����Ϊ���벻���ʮ�ֻ�������Ը����Ϊ���������ĺ��ӲŶ��꼶����������ʲô�Ǻ�ʮ�ֻᣬ��û�и������������");
			map1.put(News.URL, "http://news.baidu.com");
			list.add(map1);
			Map<String, String> map2 = new HashMap<String, String>();
			map2.put(News.TITLE, "Ժʿ���й�3��Ķ�����ؽ�����Ⱦ ռũ��������1/6");
			map2.put(News.BODY, "����һЩ�߲˺�ˮ�����棬ũҩ�����ߴ�13%���ؽ��������24%�������ι���12%��ʳƷ��ȫ�Ѿ���Ϊ�ҹ���ǰʳƷ��������Ҫ����!��10�վ��еĹ㶫��Э��̳��45��ר�ⱨ����ϣ��й�����ԺԺʿ�����ı�ʾ��ȫ��3��Ķ���������ܵ��ؽ�����Ⱦ����в��ռȫ��ũ��������1/6�����㶫ʡδ���ؽ�����Ⱦ�ĸ��أ�����11%���ҡ����������㶫���Ƚ���ʳƷ��ȫ��������ϵ���ø߿Ƽ�ŤתʳƷ��ȫ��״��");
			map2.put(News.URL, "http://news.baidu.com");
			list.add(map2);
			Map<String, String> map3 = new HashMap<String, String>();
			map3.put(News.TITLE, "�������������������������¼��Ǵ��Ʒ�չ������");
			map3.put(News.BODY, "�������Ͼ�10��12�յ�(��������ӱ)�����������������12���ڴ˼�ָ���������������¼���ӳ�����۶��й�������֯��Ҫ������ˣ����й�������ҵ�ķ�չ��һ���ƶ�������ߴ�����֯�淶�̶ȡ�����ˮƽ��һ���ٽ����ã�����˵�ǹյ㡣");
			map3.put(News.URL, "http://news.baidu.com");
			list.add(map3);
			Map<String, String> map4 = new HashMap<String, String>();
			map4.put(News.TITLE, "����ɽ��ð�彺�Ҳ����ҩ�ɷ� ��ʳ�����໵��");
			map4.put(News.BODY, "���գ�������ɽ�ơ��г�ҩ��ð�彺�����ڱ�������������������ҩ������Ϣʹ�������ٻء�����(10��11��)�����ݰ���ɽ��ҩ�ɷ����޹�˾ (000522��SZ)��ط����˸��� ��ÿ�վ������š����ߣ����˴�����������ջصİ���ɽ�Ƹ�ð�彺�ҵ����������ɴ�֮�и��𡣡��㶫ʡʳƷҩƷ�ල�ֳƣ��Ѿ��ӵ�������������ĺ�����������Ҫʱ�������飬Ŀǰ��û�еó����ۡ�");
			map4.put(News.URL, "http://news.baidu.com");
			list.add(map4);
			Map<String, String> map5 = new HashMap<String, String>();
			map5.put(News.TITLE, "��ʯ���ֹ�˾©˰1182��Υ�淢�Ž���5008��");
			map5.put(News.BODY, "������11�չ����ˡ��л����񹲺͹������������Ϣ������鹫��(�ڶ�ʮһ��)��(���¼�ơ����桱)�����У���ʯ���������µ��ʲ���Ӫ�������޹�˾����ֹ�˾Υ�淢�Ž����ߴ�5008��");
			map5.put(News.URL, "http://news.baidu.com");
			list.add(map5);
			return list;
		}
	}
}
