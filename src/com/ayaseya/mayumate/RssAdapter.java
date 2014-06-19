package com.ayaseya.mayumate;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class RssAdapter extends ArrayAdapter<Rss> {

	private ArrayList<Rss> rss;

	public RssAdapter(Context context, ArrayList<Rss> data) {
		super(context, R.layout.list_item_layout, R.id.titleView, data);
		rss = data;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = super.getView(position, convertView, parent);

		TextView title = (TextView) row.findViewById(R.id.titleView);
		//		TextView description = (TextView) row.findViewById(R.id.descriptionView);
		TextView site = (TextView) row.findViewById(R.id.siteView);
		//		TextView dateView = (TextView) row.findViewById(R.id.dateView);

		title.setText(rss.get(position).getTitle());
		//		description.setText(rss.get(position).getDescription());
		site.setText(rss.get(position).getName());
		//		dateView.setText(rss.get(position).getDate());

		if (rss.get(position).isRead()) {

			row.setBackgroundColor(Color.rgb(219,112,147));

		}else{
			row.setBackgroundColor(Color.rgb(255,255,255));
		}

		return row;
	}

}