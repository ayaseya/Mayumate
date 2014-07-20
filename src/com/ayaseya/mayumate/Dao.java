package com.ayaseya.mayumate;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Dao {

	public static final String RSS_TABLE_NAME = "rss_tbl";
	public static final String READ_TABLE_NAME = "read_tbl";

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

	// DBに登録された全ての情報を取得します。
	public List<Rss> findAll() {

		List<Rss> list = new ArrayList<Rss>();

		// 件名か本文に「まゆゆ」or「渡辺麻友」が含まれる行を抽出します。
		String sql = "SELECT * FROM rss_tbl WHERE " +
				"title LIKE '%まゆゆ%' or " +
				"description LIKE '%まゆゆ%' or " +
				"title LIKE '%渡辺麻友%' or " +
				"description LIKE '%渡辺麻友%' " +
				"ORDER BY date DESC;";

		// 第一引数SQL文、第二引数はSQL文内に埋め込まれた「?」にはめ込むString配列です。
		Cursor cursor = db.rawQuery(sql, null);

		while (cursor.moveToNext()) {
			Rss rss = new Rss();

			rss.setTitle(cursor.getString(1));
			rss.setDescription(cursor.getString(2));
			rss.setSite(cursor.getString(3));
			rss.setDate(cursor.getString(4));
			rss.setLink(cursor.getString(5));

			list.add(rss);
		}
		cursor.close();

		return list;
	}

	// INSERT処理
	public long insertRss(Rss rss) {
		// ContentValuesはカラム名をキーとして、値を保存するクラスです。
		ContentValues values = new ContentValues();

		values.put(COLUMN_TITLE, rss.getTitle());
		values.put(COLUMN_DESCRIPTION, rss.getDescription());
		values.put(COLUMN_SITE, rss.getSite());
		values.put(COLUMN_DATE, rss.getDate());
		values.put(COLUMN_LINK, rss.getLink());

		return db.insert(RSS_TABLE_NAME, null, values);
	}

	// INSERT処理(既読情報)
	public long insertRead(Rss rss) {
		// ContentValuesはカラム名をキーとして、値を保存するクラスです。
		ContentValues values = new ContentValues();

		values.put(COLUMN_TITLE, rss.getTitle());

		return db.insert(READ_TABLE_NAME, null, values);
	}

	// COUNT処理
	public int count() {

		// テーブルの全行を取得します。
		String sql = "SELECT * FROM read_tbl;";

		// 第一引数SQL文、第二引数はSQL文内に埋め込まれた「?」にはめ込むString配列です。
		Cursor cursor = db.rawQuery(sql, null);
		int result = cursor.getCount();
		cursor.close();

		return result;
	}

	public boolean isRead(String title) {

		String sql = "SELECT * FROM read_tbl WHERE title ='" + title + "';";
		Cursor cursor = db.rawQuery(sql, null);

		if (cursor.moveToFirst()) {
			cursor.close();
			return true;
		}
		cursor.close();
		return false;

	}

	// DELETE処理(1行目を削除します)
	public long deleteRead() {

		// LIMIT句で1行目の_idを取得します。
		String sql = "SELECT _id FROM read_tbl LIMIT 1;";

		// 第一引数SQL文、第二引数はSQL文内に埋め込まれた「?」にはめ込むString配列です。
		Cursor cursor = db.rawQuery(sql, null);

		// カーソルを0行目から1行目に移動します。
		cursor.moveToNext();

		// カーソルから_idにプライマリーキーを格納します。
		int _id = cursor.getInt(0);

		// カーソルを閉じます。
		cursor.close();

		// _idに該当する行を削除します。
		return db.delete(READ_TABLE_NAME, " _id = '" + _id + "'", null);
	}

	// DELETE処理(全行を削除します)
	public long deleteTable() {
		return db.delete(RSS_TABLE_NAME, null, null);
	}

}
