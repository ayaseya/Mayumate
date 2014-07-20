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
	private Dao daoRead;

	public RssAdapter(Context context, ArrayList<Rss> data, Dao daoRead) {
		super(context, R.layout.list_item_layout, R.id.titleView, data);
		this.rss = data;
		this.daoRead = daoRead;
	}

	private class ViewHolder {
		private TextView title;
		private TextView site;
		private TextView date;

		public ViewHolder(View view) {
			this.title = (TextView) view.findViewById(R.id.titleView);
			this.site = (TextView) view.findViewById(R.id.siteView);
			this.date = (TextView) view.findViewById(R.id.dateView);
		}
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = super.getView(position, convertView, parent);

		ViewHolder holder = (ViewHolder) row.getTag();

		if (holder == null) {
			holder = new ViewHolder(row);
			row.setTag(holder);
		}

		holder.title.setText(rss.get(position).getTitle());
		holder.site.setText(rss.get(position).getSite());
		holder.date.setText(rss.get(position).getDate());

		holder.title.setText(rss.get(position).getTitle());
		holder.site.setText(rss.get(position).getSite());
		holder.date.setText(rss.get(position).getDate());

		if (position % 2 == 0) {

			row.setBackgroundColor(Color.argb(255, 240, 240, 240));
			if (daoRead.isRead(rss.get(position).getTitle())) {
				holder.title.setTextColor(Color.argb(255, 170, 170, 170));
			} else {
				holder.title.setTextColor(Color.argb(255, 44, 44, 44));
			}

		} else {

			row.setBackgroundColor(Color.argb(128, 255, 255, 255));

			if (daoRead.isRead(rss.get(position).getTitle())) {
				holder.title.setTextColor(Color.argb(255, 170, 170, 170));
			} else {
				holder.title.setTextColor(Color.argb(255, 44, 44, 44));
			}

		}

		return row;
	}

}