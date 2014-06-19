package com.ayaseya.mayumate;


import android.database.sqlite.SQLiteDatabase;

public class Dao {

	public static final String BLOG_TABLE_NAME = "blog_tbl";
	public static final String RSS_TABLE_NAME = "rss_tbl";

	
	public static final String COLUMN_ID = "_id";//
	public static final String COLUMN_STATE = "title";//
	public static final String COLUMN_MAIL = "site";//
	public static final String COLUMN_PASSWORD = "password";//
	public static final String COLUMN_NAME = "name";//
	public static final String COLUMN_READING = "reading";//
	public static final String COLUMN_BIRTHDAY = "birthday";//
	public static final String COLUMN_GENDER = "gender";//

	
	
	// 記事のタイトル
	private String title;
	// 記事の本文
	private String description;
	// サイト名
	private String site;
	// 日時
	private String date;
	// 記事へのリンク(URL)
	private String link;

	
	private SQLiteDatabase db;

	/**
	 * コンストラクターでSQLiteDatabaseを引数とします。
	 *
	 * @param db
	 */
	public Dao(SQLiteDatabase db) {
		this.db = db;
	}

//	// DBに登録された全ての情報を取得します。
//	public List<Customer> findAll() {
//
//		List<Customer> list = new ArrayList<Customer>();
//
//		String sql = "SELECT * FROM customer_tbl;";
//		Log.v("TEST", "SQL文=" + sql);
//
//		// 第一引数SQL文、第二引数はSQL文内に埋め込まれた「?」にはめ込むString配列です。
//		Cursor cursor = db.rawQuery(sql, null);
//
//		while (cursor.moveToNext()) {
//			Customer customer = new Customer();
//
//			customer.set_id(cursor.getString(0));
//			customer.setState(cursor.getString(1));
//			customer.setMail(cursor.getString(2));
//			customer.setPassword(cursor.getString(3));
//			customer.setName(cursor.getString(4));
//			customer.setReading(cursor.getString(5));
//			customer.setBirthday(cursor.getString(6));
//			customer.setGender(cursor.getString(7));
//
//			list.add(customer);
//		}
//		cursor.close();
//
//		return list;
//	}
//
//	public boolean check(String _id) {
//
//		String sql = "SELECT * FROM customer_tbl WHERE _id ='" + _id + "';";
//		Log.v("TEST", "SQL文=" + sql);
//
//		// 第一引数SQL文、第二引数はSQL文内に埋め込まれた「?」にはめ込むString配列です。
//		Cursor cursor = db.rawQuery(sql, null);
//
//		if (cursor.moveToNext()) {
//			cursor.close();
//			return false;
//		}
//		cursor.close();
//		return true;
//
//	}
//
//	// COUNT処理
//	public long count() {
//
//		String sql = "SELECT * FROM customer_tbl;";
//		Log.v("TEST", "SQL文=" + sql);
//
//		// 第一引数SQL文、第二引数はSQL文内に埋め込まれた「?」にはめ込むString配列です。
//		Cursor cursor = db.rawQuery(sql, null);
//		int result = cursor.getCount();
//		cursor.close();
//
//		return result;
//	}
//
//	// INSERT処理
//	public long insert(Customer customer) {
//		// ContentValuesはカラム名をキーとして、値を保存するクラスです。
//		ContentValues values = new ContentValues();
//
//		values.put(COLUMN_ID, customer.get_id());
//		values.put(COLUMN_STATE, customer.getState());
//		values.put(COLUMN_MAIL, customer.getMail());
//		values.put(COLUMN_PASSWORD, customer.getPassword());
//		values.put(COLUMN_NAME, customer.getName());
//		values.put(COLUMN_READING, customer.getReading());
//		values.put(COLUMN_BIRTHDAY, customer.getBirthday());
//		values.put(COLUMN_GENDER, customer.getGender());
//
//		return db.insert(TABLE_NAME, null, values);
//	}

}
