package com.ayaseya.mayumate;

import static com.ayaseya.mayumate.CommonUtilities.*;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
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

		private Dao daoRead;

		private Dao daoRss;

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container, false);

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

			// ListViewをクリックした時のリスナーを設定します。
			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					//					Log.v(TAG, "Link=" + rss.get(position).getLink());

					// クリックしたアイテムが既読か判定します。
					if (daoRead.isRead(rss.get(position).getTitle()) == false) {

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

					// BundleデータとしてURLをWebViewに渡します。
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
					}

				}
			});

			if (task == null) {

				daoRss.deleteTable();

				// RSSを取得するURL一覧を配列リソースから読み込みます。
				String[] url = getResources().getStringArray(R.array.url);

				// RSSを読み込むためのタスクのインスタンスを取得します。
				task = new RssTask(getActivity(), rss, rssAdapter, daoRss);

				// RSSを取得するためのタスクを起動します。				
				task.execute(url);
			}

			return rootView;
		}

		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);

		}

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
