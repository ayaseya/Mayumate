package com.ayaseya.mayumate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class BlogSQLiteOpenHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "blog_db"; // データベース名
	private static final String DATABASE_NAME_ASSET = "blog_db.db"; // assetフォルダに保存されたファイル名
	private static final int DATABASE_VERSION = 1; // スキーマバージョン

	private SQLiteDatabase db;
	private final Context context;
	private final File databasePath;

	// コンストラクタ
	public BlogSQLiteOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context = context;
		databasePath = this.context.getDatabasePath(DATABASE_NAME);
	}

	/** 
	 * asset に格納したデータベースをコピーするための空のデータベースを作成します。 
	 */
	public void createEmptyDataBase() throws IOException {

		

		

		boolean dbExist = checkDataBaseExists();

		if (dbExist) {
			// すでにデータベースは作成されているので何も処理しません。 
		} else {
			// このメソッドを呼ぶことで、空のデータベースがアプリのデフォルトシステムパスに作られます。 
			getReadableDatabase();

			try {
				// asset に格納したデータベースをコピーする  
				copyDataBaseFromAsset();

				String dbPath = databasePath.getAbsolutePath();
				SQLiteDatabase checkDb = null;
				try {
					checkDb = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
				} catch (SQLiteException e) {
				}

				if (checkDb != null) {
					checkDb.setVersion(DATABASE_VERSION);
					checkDb.close();
				}

			} catch (IOException e) {
				throw new Error("Error copying database"+e);
			}
		}
	}

	/** 
	 * 再コピーを防止するために、すでにデータベースがあるかどうか判定する 
	 * 
	 * @return 存在している場合 {@code true} 
	 */
	private boolean checkDataBaseExists() {
		String dbPath = databasePath.getAbsolutePath();

		SQLiteDatabase checkDb = null;
		try {
			checkDb = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READONLY);
		} catch (SQLiteException e) {
			// データベースはまだ存在していない  
		}

		if (checkDb == null) {
			// データベースはまだ存在していない  
			return false;
		}

		int oldVersion = checkDb.getVersion();
		int newVersion = DATABASE_VERSION;

		if (oldVersion == newVersion) {
			// データベースは存在していて最新  
			checkDb.close();
			return true;
		}

		// データベースが存在しているが、最新ではないので削除します。  
		File file = new File(dbPath);
		file.delete();
		return false;
	}

	/** 
	 * asset に格納したデーだベースをデフォルトのデータベースパスに作成したからのデータベースにコピーする 
	 */
	private void copyDataBaseFromAsset() throws IOException {

		// asset 内のデータベースファイルにアクセス  
		InputStream input = context.getAssets().open(DATABASE_NAME_ASSET);

		// デフォルトのデータベースパスに作成した空のDB  
		OutputStream output = new FileOutputStream(databasePath);

		// コピー  
		byte[] buffer = new byte[1024];
		int size;
		while ((size = input.read(buffer)) > 0) {
			output.write(buffer, 0, size);
		}

		// Close the streams  
		output.flush();
		output.close();
		input.close();
	}

	public SQLiteDatabase openDataBase() throws SQLException {
		return getReadableDatabase();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

	@Override
	public synchronized void close() {
		if (db != null)
			db.close();

		super.close();
	}

}
