package com.ayaseya.mayumate;
//
//import static com.example.logicalchecksystem.Dao.*;
//import android.content.Context;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//
//public class RssSQLiteOpenHelper extends SQLiteOpenHelper {
//
//	private static final String DATABASE_NAME = "customer_db"; // データベース名
//	private static final int DATABASE_VERSION = 1; // スキーマバージョン
//
//	// テーブル作成用SQL文です。
//	private static final String CREATE_TABLE_SQL =
//			"CREATE TABLE " + TABLE_NAME + " ("
//					+ COLUMN_ID + " TEXT( 5 ) PRIMARY KEY,"
//					+ COLUMN_STATE + " TEXT,"
//					+ COLUMN_MAIL + " TEXT,"
//					+ COLUMN_PASSWORD + " TEXT(8),"
//					+ COLUMN_NAME + " TEXT( 20 ),"
//					+ COLUMN_READING + " TEXT( 20 ),"
//					+ COLUMN_BIRTHDAY + " TEXT( 10 ),"
//					+ COLUMN_GENDER + " TEXT( 2 )"
//					+ ");";
//	/**
//	 * SQL
//	 * CREATE TABLE customer_tbl (
//
//	 * dependents INTEGER
//	 * );
//	 */
//
//	static final String DROP_TABLE = "DROP TABLE " + TABLE_NAME + ";";
//
//	public RssSQLiteOpenHelper(Context context) {
//		super(context, DATABASE_NAME, null, DATABASE_VERSION);
//	}
//
//	@Override
//	public void onCreate(SQLiteDatabase db) {
//
//		db.execSQL(CREATE_TABLE_SQL); // テーブル作成用SQL実行します。
//
//	}
//
//	// onUpgrade()メソッドはデータベースをバージョンアップした時に呼ばれます。
//	@Override
//	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//		db.execSQL(DROP_TABLE);
//		onCreate(db);
//	}
//
//}
