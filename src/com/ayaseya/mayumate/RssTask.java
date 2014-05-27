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

public class RssTask extends AsyncTask<String, Void, String> {

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
	//	private ArrayAdapter<String> adapter;
	private ArrayList<Rss> rss;
	private RssAdapter rssAdapter;

	//	private ArrayList<String> title = new ArrayList<String>();

	// コンストラクタ
	public RssTask(Context context, ArrayList<Rss> rss, RssAdapter rssAdapter) {
		super();

		this.context = context;
		this.rss = rss;
		this.rssAdapter = rssAdapter;
	}

	@Override
	protected String doInBackground(String... params) {
		//paramsの配列にはRSS取得先となるURLが一覧で渡されます。

		for (int i = 0; i < params.length; i++) {

			try {
				// XmlPullParserのインスタンスを取得します。
				XmlPullParser xmlPullParser = Xml.newPullParser();

				// RSSの取得先となるURLを設定します。
				URL url = new URL(params[i]);
				//  URL が参照するリモートオブジェクトへの接続を表す URLConnection オブジェクトを返します。
				URLConnection connection = url.openConnection();
				// XMLPullParserにXMLファイル（RSS）のストリームを設定します。
				xmlPullParser.setInput(connection.getInputStream(), "UTF-8");

				int eventType;
				String site = null;
				String link = null;

				Rss rssParams = null;
				while ((eventType = xmlPullParser.next()) != XmlPullParser.END_DOCUMENT) {

					if (eventType == XmlPullParser.START_TAG && "title".equals(xmlPullParser.getName())) {
						xmlPullParser.next();
						if (site == null) {
							site = xmlPullParser.getText();
							Log.v(TAG, "【" + site + "】");

						} else {

							if (rssParams != null) {
								rss.add(rssParams);
							}

							if (!xmlPullParser.getText().equals("渡辺麻友 - Google News")) {

								rssParams = new Rss();
								rssParams.setSite(site);
								rssParams.setTitle(xmlPullParser.getText());
								Log.v(TAG, "> " + xmlPullParser.getText());

							}
						}

					} else if (eventType == XmlPullParser.START_TAG && "link".equals(xmlPullParser.getName())) {
						xmlPullParser.next();
						if (link == null) {
							link = xmlPullParser.getText();
							Log.v(TAG, "└" + link);
						} else {

							if (xmlPullParser.getText() != null) {
								if (!xmlPullParser.getText()
										.equals("http://news.google.com/news?ned=us&hl=ja&q=%E6%B8%A1%E8%BE%BA%E9%BA%BB%E5%8F%8B")) {

									if (rssParams != null) {

										rssParams.setLink(xmlPullParser.getText());
									}
									Log.v(TAG, xmlPullParser.getText());
								}

							}

						}

					} else if (eventType == XmlPullParser.START_TAG && "date".equals(xmlPullParser.getName())) {
						xmlPullParser.next();

						if (xmlPullParser.getText() != null) {
							if (rssParams != null) {
								rssParams.setDate(xmlPullParser.getText());
							}
							Log.v(TAG, xmlPullParser.getText());

						}

					} else if (eventType == XmlPullParser.START_TAG && "pubDate".equals(xmlPullParser.getName())) {
						xmlPullParser.next();
						String str = xmlPullParser.getText();
						if (rssParams != null) {
							rssParams.setDate(str);

						}
						Log.v(TAG, str);

					}
				}

			} catch (Exception e) {
				Log.v(TAG, "Error:" + e);

			}

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

		// ArrayListをソートする

		rssAdapter.notifyDataSetChanged();
		// 通信中ダイアログを非表示にします。
		loading.dismiss();

	}
}
