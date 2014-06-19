package com.ayaseya.mayumate;

public class Rss {

	// 記事のタイトル
	private String title;
	// 記事の本文
	private String description;
	// サイト名
	private String name;
	// 日時
	private String date;
	// 記事へのリンク(URL)
	private String link;

	// 既読
	private boolean read;

	public Rss() {
		title = "記事のタイトル";
		description = "記事の本文";
		name = "サイト名";
		date = "2014/1/1";
		read = false;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setSite(String site) {
		this.name = site;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public boolean isRead() {
		return read;
	}

	public void setRead(boolean read) {
		this.read = read;
	}

}
