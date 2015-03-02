package com.bidchina.client;

import java.util.ArrayList;
import java.util.List;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class BidAdpter extends BaseAdapter {
	private LayoutInflater inflater;
	private Context mContext;
	private List<Bid> list = new ArrayList<Bid>(); 

	public BidAdpter(Context mContext,List<Bid> list) {
		this.mContext = mContext;
		inflater=LayoutInflater.from(mContext);
		this.list = list;
	}

	@Override
	public int getCount() {
		
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int location, View convertView, ViewGroup arg2) {
		Bid bid = list.get(location);
		ViewHolder holder;
		if(convertView == null){
			convertView =inflater.inflate(R.layout.bid_item, null);
			holder = new ViewHolder();
			holder.findview(convertView);
			convertView.setTag(holder);
		}else {
			holder=(ViewHolder) convertView.getTag();
		}
		holder.tv_vocation.setText(bid.getCategory());
		holder.tv_title.setText(bid.getTitle());
		holder.tv_city.setText(bid.getArea());
		holder.tv_time.setText(bid.getDate());
		return convertView;
	}
	
	class ViewHolder{
		protected TextView tv_vocation;
		protected TextView tv_title;
		protected TextView tv_city;
		protected TextView tv_time;
		
		/**
		 */
		public void findview(View view) {
			tv_vocation = (TextView)view.findViewById(R.id.tv_vocation);
			tv_title = (TextView)view.findViewById(R.id.tv_titie);
			tv_city = (TextView)view.findViewById(R.id.tv_city);
			tv_time = (TextView)view.findViewById(R.id.tv_time);
		}
	}

}
