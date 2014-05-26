package com.ayaseya.mayumate;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

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

	private Activity activity;
	private Context context;
	private ProgressDialog loading;

	// コンストラクタ
	public RssTask(Activity activity, Context context) {
		super();
		this.activity = activity;
		this.context = context;
	}

	@Override
	protected String doInBackground(Void... params) {

		String result = "";

		return result;

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

		// 通信中ダイアログを非表示にします。
		loading.dismiss();

	}
}
