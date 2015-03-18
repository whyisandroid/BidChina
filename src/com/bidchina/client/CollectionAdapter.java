package com.bidchina.client;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class CollectionAdapter extends BaseAdapter {
	private List<BidDetailData> mList;
	private Context mContext;
	public CollectionAdapter(Context mContext,List<BidDetailData> list) {
		this.mList = list;
		this.mContext = mContext;
	}
	
	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return null;
	}
}
