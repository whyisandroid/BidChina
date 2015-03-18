package com.bidchina.client;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;


public class CollectionActivity extends Activity{
	private ListView lv_coollection;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.coollection_layout);
		findView();
		initView();
	}
	/**
	 * 
	  * 方法描述：FindView
	  * @author: why
	  * @time: 2015-2-10 下午1:37:06
	 */
	private void findView() {
		lv_coollection = (ListView)findViewById(R.id.lv_coollection);
	}

	/**
	 * 
	  * 方法描述：初始化 View
	  * @author: why
	  * @time: 2015-2-10 下午1:37:06
	 */
	private void initView() {
		DbUtils db = DbUtils.create(this);
		try {
			List<BidDetailData> list = db.findAll(BidDetailData.class);
		} catch (DbException e) {
			e.printStackTrace();
		}
	}
}
