package com.bidchina.client;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;


public class CollectionActivity extends Activity{
	private ListView lv_coollection;
	
	private Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				DbUtils db = DbUtils.create(CollectionActivity.this);
				try {
					List<BidDetailData> list = db.findAll(BidDetailData.class);
					CollectionAdapter adapter = new CollectionAdapter(CollectionActivity.this, list);
					lv_coollection.setAdapter(adapter);
				} catch (DbException e) {
					e.printStackTrace();
				}
				break;

			default:
				break;
			}
		};
	};
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
	
	public void goBack(View view){
		this.finish();
	}

	/**
	 * 
	  * 方法描述：初始化 View
	  * @author: why
	  * @time: 2015-2-10 下午1:37:06
	 */
	private void initView() {
		lv_coollection.setOnItemClickListener(coollectionListener);
		lv_coollection.setOnItemLongClickListener(coollectionLongListener);
		DbUtils db = DbUtils.create(this);
		try {
			List<BidDetailData> list = db.findAll(BidDetailData.class);
			CollectionAdapter adapter = new CollectionAdapter(this, list);
			lv_coollection.setAdapter(adapter);
		} catch (DbException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	private OnItemClickListener coollectionListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> adapter, View arg1, int arg2,
				long arg3) {
			BidDetailData detail = (BidDetailData)adapter.getAdapter().getItem(arg2);
			Bundle bundle = new Bundle();
			bundle.putSerializable("BidDetail", detail);
			bundle.putString("URL", detail.getUrl());
			Intent intent = new Intent(CollectionActivity.this, BidActivity.class);
			intent.putExtras(bundle);
			startActivity(intent);
		}
	};
	
	
	private OnItemLongClickListener coollectionLongListener = new OnItemLongClickListener() {
		@Override
		public boolean onItemLongClick(final AdapterView<?> adapter, View arg1,
				final int arg2, long arg3) {
			
			AlertDialog.Builder builder = new AlertDialog.Builder(CollectionActivity.this);
			builder.setMessage("确定要删除吗？")
			       .setCancelable(false)
			       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			        	   BidDetailData detail = (BidDetailData)adapter.getAdapter().getItem(arg2);
			   			DbUtils db = DbUtils.create(CollectionActivity.this);
			   			try {
			   				db.delete(detail);
			   				mHandler.obtainMessage(0).sendToTarget();
			   			} catch (DbException e) {
			   				e.printStackTrace();
			   			}
			   			dialog.cancel();
			           }
			       })
			       .setNegativeButton("No", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			                dialog.cancel();
			           }
			       });
			
			AlertDialog alert = builder.create();
			alert.show();
			return false;
		}
	};
}
