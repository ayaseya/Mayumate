package com.ayaseya.mayumate;

// 各ブログサイトの情報を格納するクラスです。
public class Blog {

	// プライマリーキー
	private int _id;

	// ブログの名前
	private String name;

	// ブログのURL
	private String url;

	// SQLのSELECT文であいまい検索(渡辺麻友、まゆゆ等のワード)する必要があるか否か
	// AKB48関連のまとめブログから他のメンバーの記事を除外する必要がある
	// true:あいまい検索する false:全ての記事を登録する
	private boolean select;

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isSelect() {
		return select;
	}

	public void setSelect(boolean select) {
		this.select = select;
	}

}
