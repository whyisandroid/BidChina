package com.bidchina.client;

import com.bidchina.client.util.ShareUtil;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class BidActivity extends Activity {
	private TextView tv_detail_title;
	private TextView tv_description;
	private TextView tv_content;
	private TextView tv_detail_title2;
	private TextView tv_detail_date;
	private ImageButton main_top_left;
	
	private TextView tv_share;
	private BidDetailData bidDetail;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bid_info_activity);
		findView();
		initView();
	}

	private void findView() {
		tv_description = (TextView)findViewById(R.id.tv_description);
		tv_content = (TextView)findViewById(R.id.tv_content);
		tv_detail_title = (TextView)findViewById(R.id.tv_detail_title);
		tv_detail_title2 = (TextView)findViewById(R.id.tv_detail_title2);
		tv_detail_date = (TextView)findViewById(R.id.tv_detail_date);
		main_top_left = (ImageButton)findViewById(R.id.main_top_left);
		tv_share = (TextView)findViewById(R.id.tv_share);
	}

	private void initView() {
		main_top_left.setOnClickListener(leftListener);
		tv_share.setOnClickListener(shareListener);
		 bidDetail = (BidDetailData)getIntent().getExtras().getSerializable("BidDetail");
		if(bidDetail != null){
			tv_detail_title.setText(bidDetail.getTitle());
			tv_detail_title2.setText(bidDetail.getTitle());
			tv_detail_date.setText(bidDetail.getDate());
			tv_description.setText(Html.fromHtml(bidDetail.getDescription()));
			tv_content.setText(Html.fromHtml(bidDetail.getContent()));
		}
	}
	
	private OnClickListener leftListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			BidActivity.this.finish();
		}
	};
	
	private OnClickListener shareListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if(bidDetail != null){
				String title = bidDetail.getTitle();
				String text = bidDetail.getTitle();
				ShareMessage share =  new ShareMessage(text,title);
				ShareUtil.showShare(BidActivity.this, share);
			}
		}
	};
}
