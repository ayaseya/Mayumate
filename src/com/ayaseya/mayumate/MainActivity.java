package com.ayaseya.mayumate;

import static com.ayaseya.mayumate.CommonUtilities.*;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
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
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
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

			// RSSを読み込むためのタスクのインスタンスを取得します。
			task = new RssTask(getActivity(),adapter);

			// RSSボタンのリスナーを設定します。
			rootView.findViewById(R.id.rssBtn).setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					
					
					// RSSを取得するためのタスクを起動します。				
					task.execute();
				}
			});

			return rootView;
		}

		// 
		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);

		}
	}

}
