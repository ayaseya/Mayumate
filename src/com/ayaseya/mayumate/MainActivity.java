package com.ayaseya.mayumate;

import static com.ayaseya.mayumate.CommonUtilities.*;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.v(TAG, "/* ********** ********** ********** ********** */");

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
		private ArrayList<String> list = new ArrayList<String>();
		private ArrayAdapter<String> adapter;
		private RssTask task;

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container, false);

			// リストビューのインスタンスを生成します。
			listView = (ListView) rootView.findViewById(R.id.listView);

			//			list.add("1");
			//			list.add("2");
			//			list.add("3");

			// 文字列を表示させるシンプルなアダプターを用意します。
			adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, list);
			
			
			// リストビューにアダプターを設定します。
			listView.setAdapter(adapter);

			// RSSボタンのリスナーを設定します。
			rootView.findViewById(R.id.rssBtn).setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					// RSSを取得するURL一覧を配列リソースから読み込みます。
					String[] url = getResources().getStringArray(R.array.url);
					// RSSを読み込むためのタスクのインスタンスを取得します。
					task = new RssTask(getActivity(), adapter);

					// RSSを取得するためのタスクを起動します。				
					task.execute(url);

				}
			});

			// WebViewボタンのリスナーを設定します。
			rootView.findViewById(R.id.webViewBtn).setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					getFragmentManager().beginTransaction()
							.replace(R.id.container, new WebViewFragment())
							.addToBackStack(null)
							.commit();

				}
			});

			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					Log.v(TAG, "position=" + position);

					// BundleデータにURLを乗せてWebViewのFragmentを表示させる
					// すでにWebViewのFragmentが存在する場合には
					// showメソッドで前面に表示させた後、URLを読み込ませる←要検証
				}
			});

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

			// WebViewのインスタンスを取得します。
			webview = (WebView) rootView.findViewById(R.id.webView);

			// デフォルトではloadUrl()で読み込んだページ内のリンクをクリックすると、
			// 標準のブラウザが起動してしまうため、リンク先のページも
			// WebView内で表示させるためWebViewClientを設定します。
			webview.setWebViewClient(new WebViewClient());

			// ズーム機能を有効にします。
			webview.getSettings().setBuiltInZoomControls(true);

			// 初期ページを読み込みます。
			webview.loadUrl("https://www.google.co.jp/");
			return rootView;
		}

	}

	private class RssAdapter extends ArrayAdapter<Rss> {

		public RssAdapter(Context context, ArrayList<Rss> data) {
			super(context,
					R.layout.list_item_layout,
					data);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View row = super.getView(position, convertView, parent);

			//			ImageView img = (ImageView) row.findViewById(R.id.iconView);
			//
			//			if (icon.get(position).equals("f01")) {
			//				img.setImageResource(R.drawable.f01);
			//			} else if (icon.get(position).equals("f02")) {
			//				img.setImageResource(R.drawable.f02);
			//			} else if (icon.get(position).equals("f03")) {
			//				img.setImageResource(R.drawable.f03);
			//			} else if (icon.get(position).equals("f04")) {
			//				img.setImageResource(R.drawable.f04);
			//			} else if (icon.get(position).equals("f05")) {
			//				img.setImageResource(R.drawable.f05);
			//			} else if (icon.get(position).equals("f06")) {
			//				img.setImageResource(R.drawable.f06);
			//			}

			return row;
		}

	}

}
