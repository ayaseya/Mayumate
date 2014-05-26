package com.ayaseya.mayumate;

public class Rss {

	// 記事のタイトル
	private CharSequence title;
	// 記事の本文
	private CharSequence description;

	public Rss() {
		title = "";
		description = "";
	}

	public CharSequence getDescription() {
		return description;
	}

	public void setDescription(CharSequence description) {
		this.description = description;
	}

	public CharSequence getTitle() {
		return title;
	}

	public void setTitle(CharSequence title) {
		this.title = title;
	}
}
