package com.ayaseya.mayumate;

import static com.ayaseya.mayumate.Dao.*;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RssSQLiteOpenHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "rss_db"; // データベース名
	private static final int DATABASE_VERSION = 1; // スキーマバージョン

	// テーブル作成用SQL文です。
	private static final String CREATE_TABLE_SQL =
			"CREATE TABLE " + RSS_TABLE_NAME + " ("
					+ COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ COLUMN_TITLE + " TEXT,"
					+ COLUMN_DESCRIPTION + " TEXT,"
					+ COLUMN_SITE + " TEXT,"
					+ COLUMN_DATE + " TEXT,"
					+ COLUMN_LINK + " TEXT"
					+ ");";

	static final String DROP_TABLE = "DROP TABLE " + RSS_TABLE_NAME + ";";

	public RssSQLiteOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		db.execSQL(CREATE_TABLE_SQL); // テーブル作成用SQL実行します。

	}

	// onUpgrade()メソッドはデータベースをバージョンアップした時に呼ばれます。
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(DROP_TABLE);
		onCreate(db);
	}

}
