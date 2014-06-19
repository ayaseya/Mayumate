package com.ayaseya.mayumate;

import static com.ayaseya.mayumate.CommonUtilities.*;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.database.SQLException;
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
import android.widget.ListView;

public class MainActivity extends Activity {

	private BlogSQLiteOpenHelper helper;
	private SQLiteDatabase db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.v(TAG, "/* ********** ********** ********** ********** */");
		
		requestWindowFeature(Window.FEATURE_NO_TITLE); // タイトルバーを非表示にする
		
		setContentView(R.layout.activity_main);
		
		
		
		
		
		
	
		
		
		
		
		
		
		
		helper = new BlogSQLiteOpenHelper(this);  
	    try {  
	        helper.createEmptyDataBase();  
	        db = helper.openDataBase();  
	    } catch (IOException ioe) {  
	        throw new Error("Unable to create database");  
	    } catch(SQLException sqle){  
	        throw sqle;  
	    }  
		
	    

//        db = helper.getWritableDatabase();
//        
//        //あとは適当にクエリ投げたりできる
//        Cursor cursor = db.query("blog_tbl", null, null, null, null, null, null);
//        cursor.moveToNext();
//	    
//        Log.v(TAG, "TEST:"+cursor.getString(1));

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
		//		private ArrayList<String> list = new ArrayList<String>();
		//		private ArrayAdapter<String> adapter;

		private RssAdapter rssAdapter;

		private ArrayList<Rss> rss = new ArrayList<Rss>();

		private RssTask task;

		private WebViewFragment webViewFragment;

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container, false);

//			// リストビューのインスタンスを生成します。
//			listView = (ListView) rootView.findViewById(R.id.listView);
//
//			//			list.add("1");
//			//			list.add("2");
//			//			list.add("3");
//
//			//			rss.add(new Rss());
//
//			// 文字列を表示させるシンプルなアダプターを用意します。
//			//			adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, list);
//
//			rssAdapter = new RssAdapter(getActivity(), rss);
//
//			// リストビューにアダプターを設定します。
//			//			listView.setAdapter(adapter);
//
//			listView.setAdapter(rssAdapter);
//
//			// ListViewをクリックした時のリスナーを設定します。
//			listView.setOnItemClickListener(new OnItemClickListener() {
//
//				@Override
//				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//					Log.v(TAG, "Link=" + rss.get(position).getLink());
//					rss.get(position).setRead(true);
//
//					Bundle bundle = new Bundle();
//					bundle.putString("Link", rss.get(position).getLink());
//
//					if (webViewFragment == null) {
//						webViewFragment = new WebViewFragment();
//
//						webViewFragment.setArguments(bundle);
//						getFragmentManager().beginTransaction()
//								.replace(R.id.container, webViewFragment)
//								.addToBackStack(null)
//								.commit();
//					} else {
//						webViewFragment.setArguments(bundle);
//						getFragmentManager().beginTransaction()
//								.replace(R.id.container, webViewFragment)
//								.addToBackStack(null)
//								.commit();
//					}
//
//				}
//			});
//
//			if (task == null) {
//				// RSSを取得するURL一覧を配列リソースから読み込みます。
//				String[] url = getResources().getStringArray(R.array.url);
//				// RSSを読み込むためのタスクのインスタンスを取得します。
//				task = new RssTask(getActivity(), rss, rssAdapter);
//
//				// RSSを取得するためのタスクを起動します。				
//				task.execute(url);
//			}

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
//
//			String url = getArguments().getString("Link");
//
//			// WebViewのインスタンスを取得します。
//			webview = (WebView) rootView.findViewById(R.id.webView);
//
//			// デフォルトではloadUrl()で読み込んだページ内のリンクをクリックすると、
//			// 標準のブラウザが起動してしまうため、リンク先のページも
//			// WebView内で表示させるためWebViewClientを設定します。
//			webview.setWebViewClient(new WebViewClient());
//
//			// ズーム機能を有効にします。
//			webview.getSettings().setBuiltInZoomControls(true);
//
//			// ページを読み込みます。
//			webview.loadUrl(url);
			return rootView;
		}

	}

}
