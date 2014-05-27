package com.ayaseya.mayumate;

public class Rss {

	// 記事のタイトル
	private String title;
	// 記事の本文
	private String description;
	// サイト名
	private String site;
	// 日時
	private String date;

	public Rss() {
		title = "";
		description = "";
		site = "";
		date = "";
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

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
