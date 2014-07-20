package com.ayaseya.mayumate;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.http.impl.cookie.DateParseException;
import org.apache.http.impl.cookie.DateUtils;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
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
	private ArrayList<Rss> rss;
	private RssAdapter rssAdapter;
	private Dao daoRss;
	
	// コンストラクタ
	public RssTask(Context context, ArrayList<Rss> rss, RssAdapter rssAdapter, Dao daoRss) {
		super();

		this.context = context;
		this.rss = rss;
		this.rssAdapter = rssAdapter;
		this.daoRss = daoRss;
	}

	@Override
	protected String doInBackground(String... params) {
		//paramsの配列にはRSS取得先となるURLが一覧で渡されます。

		// RSSを取得するブログ名一覧を配列リソースから読み込みます。
		String[] name = context.getResources().getStringArray(R.array.site);

		String site = null;
		String link = null;

		Rss rssParams = null;

		for (int i = 0; i < params.length; i++) {

			// XMLパーサー解析を開始します。
			try {
				// XmlPullParserのインスタンスを取得します。
				XmlPullParser xmlPullParser = Xml.newPullParser();

				// RSSの取得先となるURLを設定します。
				URL url = new URL(params[i]);

				//  URL が参照するリモートオブジェクトへの接続を表す URLConnection オブジェクトを返します。
				URLConnection connection = url.openConnection();

				// XMLPullParserにXMLファイル（RSS）のストリームを設定します。
				xmlPullParser.setInput(connection.getInputStream(), "UTF-8");

				//イベントを取得します。
				int eventType = xmlPullParser.getEventType();
				// XMLドキュメントの終端までループします。 
				while (eventType != XmlPullParser.END_DOCUMENT) {

					String tag = null; //タグ名を取得するための変数です。  
					switch (eventType) {
					//ドキュメントの最初  
					case XmlPullParser.START_DOCUMENT:
						break;

					//開始タグ時  
					case XmlPullParser.START_TAG:
						tag = xmlPullParser.getName();
						// <item>タグの子要素を取得したいので
						// <item>タグを見つけたら1記事分の情報を格納するため
						// rssParamsのインスタンスを取得します。

						if (rssParams == null) {
							if (tag.equals("title")) {
								//								site = xmlPullParser.nextText();
								site = name[i];
								xmlPullParser.nextText();
							}
						}

						if (tag.equals("item")) {
							rssParams = new Rss();
						} else if (rssParams != null) {
							// <item>タグ以降の各種情報を順に格納していきます。
							if (tag.equals("title")) {
								rssParams.setTitle(xmlPullParser.nextText());
							} else if (tag.equals("link")) {
								rssParams.setLink(xmlPullParser.nextText());
							} else if (tag.equals("date")) {
								/** RSSフィードの日付フォーマットパターン */
								String pattern[] = { DateFormatUtils.ISO_DATETIME_TIME_ZONE_FORMAT.getPattern() };

								// ItemからpubDateを取得してフォーマット
								String rssDate = xmlPullParser.nextText();

								String formattedDate = "";
								try {
									Date date = DateUtils.parseDate(rssDate, pattern);
									SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
									// フォーマット側のTimeZoneも日本にしておきます
									sdf.setTimeZone(TimeZone.getTimeZone("Asia/Tokyo"));
									formattedDate = sdf.format(date);
								} catch (DateParseException e) {
									e.printStackTrace();
								}

								rssParams.setDate(formattedDate);

							} else if (tag.equals("pubDate")) {
								/** RSSフィードの日付フォーマットパターン */
								String pattern[] = { DateUtils.PATTERN_RFC1123 };

								// ItemからpubDateを取得してフォーマット
								String pubdate = xmlPullParser.nextText();

								String formattedPubdate = "";
								try {
									Date date = DateUtils.parseDate(pubdate, pattern);
									SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
									// フォーマット側のTimeZoneも日本にしておきます
									sdf.setTimeZone(TimeZone.getTimeZone("Asia/Tokyo"));
									formattedPubdate = sdf.format(date);
								} catch (DateParseException e) {
									e.printStackTrace();
								}

								rssParams.setDate(formattedPubdate);

							} else if (tag.equals("description")) {
								rssParams.setDescription(xmlPullParser.nextText());
							}
						}
						break;

					//終了タグ時  
					case XmlPullParser.END_TAG:
						// <item>タグが終了したら、1記事分の情報を格納し終えたので  
						// RSSのArrayListに格納します。  
						tag = xmlPullParser.getName();
						if (tag.equals("item")) {
							rssParams.setSite(site);
							//Itemタグ終了時に格納します。  
							//							rss.add(rssParams);

							daoRss.insertRss(rssParams);

							rssParams = null;
						}
						break;
					}
					//次のイベントへ遷移させループさせます。
					eventType = xmlPullParser.next();
				}
			} catch (XmlPullParserException e) {
				return null;
			} catch (IOException e) {
				return null;
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

		// リストにすべての情報を格納します。
		List<Rss> list = daoRss.findAll();

		for (int i = 0; i < list.size(); i++) {
			rss.add(list.get(i));
		}

		rssAdapter.notifyDataSetChanged();
		// 通信中ダイアログを非表示にします。
		loading.dismiss();

	}
}
