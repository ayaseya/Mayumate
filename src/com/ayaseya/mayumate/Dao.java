package com.ayaseya.mayumate;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class Dao {

	public static final String TABLE_NAME = "rss_tbl";

	public static final String COLUMN_ID = "_id";//
	public static final String COLUMN_TITLE = "title";//
	public static final String COLUMN_DESCRIPTION = "description";//
	public static final String COLUMN_SITE = "site";//
	public static final String COLUMN_DATE = "date";//
	public static final String COLUMN_LINK = "link";//

	private SQLiteDatabase db;

	/**
	 * コンストラクターでSQLiteDatabaseを引数とします。
	 *
	 * @param db
	 */
	public Dao(SQLiteDatabase db) {
		this.db = db;
	}

	// INSERT処理
	public long insert(Rss rss) {
		// ContentValuesはカラム名をキーとして、値を保存するクラスです。
		ContentValues values = new ContentValues();

		values.put(COLUMN_TITLE, rss.getTitle());
		values.put(COLUMN_DESCRIPTION, rss.getDescription());
		values.put(COLUMN_SITE, rss.getSite());
		values.put(COLUMN_DATE, rss.getDate());
		values.put(COLUMN_LINK, rss.getLink());

		return db.insert(TABLE_NAME, null, values);
	}

	// DELETE処理
	public int delete(String _id) {
		return db.delete(TABLE_NAME, " _id = '" + _id + "'", null);
	}

}
