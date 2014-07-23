package com.ayaseya.mayumate;

import static com.ayaseya.mayumate.CommonUtilities.*;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.v(TAG, "/* ********** ********** ********** ********** */");

		requestWindowFeature(Window.FEATURE_NO_TITLE); // タイトルバーを非表示にする

		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment())
					.commit();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
//		int id = item.getItemId();
//		if (id == R.id.action_settings) {
//			return true;
//		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		private ListView listView;

		private RssAdapter rssAdapter;

		private ArrayList<Rss> rss = new ArrayList<Rss>();

		private RssTask task;

		private WebViewFragment webViewFragment;

		private Dao daoRss;
		private Dao daoRead;

		private SwipeRefreshLayout mSwipeRefreshLayout;

		private String[] url;

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container, false);

			// AdView をリソースとしてルックアップしてリクエストを読み込む
		    AdView adView = (AdView)rootView.findViewById(R.id.adView);
		    AdRequest adRequest = new AdRequest.Builder().build();
		    adView.loadAd(adRequest);
			
			
			// SwipeRefreshLayoutの設定
			mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.refresh);
			mSwipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {

				@Override
				public void onRefresh() {

					rssAdapter.clear();

					daoRss.deleteTable();
					// RSSを読み込むためのタスクのインスタンスを取得します。
					task = new RssTask(getActivity(), rss, rssAdapter, daoRss, mSwipeRefreshLayout);

					// RSSを取得するためのタスクを起動します。				
					task.execute(url);

				}
			});
			mSwipeRefreshLayout.setColorScheme(android.R.color.holo_blue_bright, android.R.color.holo_blue_light, android.R.color.holo_blue_dark,
					android.R.color.holo_blue_light);

			// 既読情報を管理するSQLiteHelperクラスのコンストラクターを呼び出します。
			ReadSQLiteOpenHelper dbHelperRead = new ReadSQLiteOpenHelper(getActivity());
			SQLiteDatabase dbRead = dbHelperRead.getWritableDatabase();

			// Daoクラスのコンストラクターを呼び出します。
			daoRead = new Dao(dbRead);

			// 取得したRssを管理するSQLiteHelperクラスのコンストラクターを呼び出します。
			RssSQLiteOpenHelper dbHelperRss = new RssSQLiteOpenHelper(getActivity());
			SQLiteDatabase dbRss = dbHelperRss.getWritableDatabase();

			// Daoクラスのコンストラクターを呼び出します。
			daoRss = new Dao(dbRss);

			// リストビューのインスタンスを生成します。
			listView = (ListView) rootView.findViewById(R.id.listView);

			rssAdapter = new RssAdapter(getActivity(), rss, daoRead);
			listView.setAdapter(rssAdapter);
			
			// RSSを取得するURL一覧を配列リソースから読み込みます。
			url = getResources().getStringArray(R.array.url);

			// ListViewをクリックした時のリスナーを設定します。
			listView.setOnItemClickListener(new OnItemClickListener() {

				/* (非 Javadoc)
				 * @see android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget.AdapterView, android.view.View, int, long)
				 */
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					//					Log.v(TAG, "Link=" + rss.get(position).getLink());

					// クリックしたアイテムが既読か判定します。
					if (daoRead.isRead(rss.get(position).getTitle()) == false) {

						TextView title = (TextView) view.findViewById(R.id.titleView);
						title.setTextColor(Color.argb(255, 170, 170, 170));

						// 既読情報をRSSクラスに設定します。
						//						rss.get(position).setRead(true);

						// データベースの履歴が100件以上なら一番古い情報を削除して追加します。
						if (daoRead.count() >= 100) {
							daoRead.deleteRead();
							daoRead.insertRead(rss.get(position));
						} else {
							daoRead.insertRead(rss.get(position));
						}

					}

					Uri uri = Uri.parse(rss.get(position).getLink());
					Intent i = new Intent(Intent.ACTION_VIEW, uri);
					startActivity(i);

					/*					// BundleデータとしてURLをWebViewに渡します。
										Bundle bundle = new Bundle();
										bundle.putString("Link", rss.get(position).getLink());

										if (webViewFragment == null) {
											webViewFragment = new WebViewFragment();

											webViewFragment.setArguments(bundle);
											getFragmentManager().beginTransaction()
													.replace(R.id.container, webViewFragment)
													.addToBackStack(null)
													.commit();
										} else {
											webViewFragment.setArguments(bundle);
											getFragmentManager().beginTransaction()
													.replace(R.id.container, webViewFragment)
													.addToBackStack(null)
													.commit();
										}*/

				}
			});

			if (daoRss.isRss()==false) {

				daoRss.deleteTable();

				// RSSを読み込むためのタスクのインスタンスを取得します。
				task = new RssTask(getActivity(), rss, rssAdapter, daoRss, mSwipeRefreshLayout);

				// RSSを取得するためのタスクを起動します。				
				task.execute(url);
			}else{
				// リストにすべての情報を格納します。
				List<Rss> list = daoRss.findAll();

				for (int i = 0; i < list.size(); i++) {
					rss.add(list.get(i));
				}
				rssAdapter.notifyDataSetChanged();
			}

			return rootView;
		}

		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);

		}

	}
	
	@Override
	protected void onStart() {
		super.onStart();
		EasyTracker.getInstance(this).activityStart(this);
	}

	@Override
	protected void onStop() {
		super.onStop();
		EasyTracker.getInstance(this).activityStop(this);
	}

	public static class WebViewFragment extends Fragment {

		private WebView webview;

		public WebViewFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

			View rootView = inflater.inflate(R.layout.fragment_webview, container, false);

			String url = getArguments().getString("Link");

			// WebViewのインスタンスを取得します。
			webview = (WebView) rootView.findViewById(R.id.webView);

			// デフォルトではloadUrl()で読み込んだページ内のリンクをクリックすると、
			// 標準のブラウザが起動してしまうため、リンク先のページも
			// WebView内で表示させるためWebViewClientを設定します。
			webview.setWebViewClient(new WebViewClient());

			// ズーム機能を有効にします。
			webview.getSettings().setBuiltInZoomControls(true);

			// 初期ページを読み込みます。
			webview.loadUrl(url);

			return rootView;
		}

	}

}
