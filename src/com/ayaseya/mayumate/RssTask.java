package com.ayaseya.mayumate;

import static com.ayaseya.mayumate.CommonUtilities.*;

import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Xml;
import android.widget.ArrayAdapter;

public class RssTask extends AsyncTask<Void, Void, String> {

	/*
	 * AsyncTask<型1, 型2,型3>
	 *
	 *   型1 … Activityからスレッド処理へ渡したい変数の型
	 *          ※ Activityから呼び出すexecute()の引数の型
	 *          ※ doInBackground()の引数の型
	 *
	 *   型2 … 進捗度合を表示する時に利用したい型
	 *          ※ onProgressUpdate()の引数の型
	 *
	 *   型3 … バックグラウンド処理完了時に受け取る型
	 *          ※ doInBackground()の戻り値の型
	 *          ※ onPostExecute()の引数の型
	 *
	 *   ※ それぞれ不要な場合は、Voidを設定すれば良い
	 */

	private Context context;
	private ProgressDialog loading;
	private ArrayAdapter<String> adapter;
	private ArrayList<String> title = new ArrayList<String>();

	// コンストラクタ
	public RssTask(Context context, ArrayAdapter<String> adapter) {
		super();

		this.context = context;
		this.adapter = adapter;
	}

	@Override
	protected String doInBackground(Void... params) {

		try {
			// XmlPullParserのインスタンスを取得します。
			XmlPullParser xmlPullParser = Xml.newPullParser();

			// RSSの取得先となるURLを設定します。
			URL url = new URL("http://news.google.com/news?hl=ja&ned=us&ie=UTF-8&oe=UTF-8&output=rss&q=%e6%b8%a1%e8%be%ba%e9%ba%bb%e5%8f%8b");
			//  URL が参照するリモートオブジェクトへの接続を表す URLConnection オブジェクトを返します。
			URLConnection connection = url.openConnection();
			// XMLPullParserにXMLファイル（RSS）のストリームを設定します。
			xmlPullParser.setInput(connection.getInputStream(), "UTF-8");

			int eventType;
			while ((eventType = xmlPullParser.next()) != XmlPullParser.END_DOCUMENT) {
				if (eventType == XmlPullParser.START_TAG && "title".equals(xmlPullParser.getName())) {

					String tmp = xmlPullParser.nextText();

					title.add(tmp);

					//					Log.v(TAG, tmp);

				}
			}
		} catch (Exception e) {

			Log.v(TAG, "Error:" + e);

		}
		return null;
	}

	@Override
	protected void onPreExecute() {

		// RSS読み込み中に表示する、ローディング画面用のダイアログを設定します。
		loading = new ProgressDialog(context);
		loading.setTitle(context.getString(R.string.transmitting));
		loading.setMessage(context.getString(R.string.wait));
		loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		loading.setCancelable(false);

		loading.show();
	}

	@Override
	protected void onPostExecute(String result) {

		for (int i = 0; i < title.size(); i++) {
			adapter.add(title.get(i));

		}

		// 通信中ダイアログを非表示にします。
		loading.dismiss();

	}
}
