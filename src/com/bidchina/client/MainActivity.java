package com.bidchina.client;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bidchina.client.net.AppSocketInterface;
import com.bidchina.client.net.BusinessException;
import com.bidchina.client.net.Request;
import com.bidchina.client.net.XUtilsSocketImpl;
import com.bidchina.client.until.Config;
import com.bidchina.client.until.ProgressDialogUtil;
import com.bidchina.client.until.PullToRefreshView;
import com.bidchina.client.until.PullToRefreshView.OnFooterRefreshListener;


public class MainActivity extends Activity implements OnClickListener,OnFooterRefreshListener{
	private ListView lv_bid;
	private TextView tv_choose;
	private RadioButton rb_buy;
	private RadioButton rb_project;
//	private boolean rbFlag = true;
	
	private BidData bidData;
	private BidAdpter adapter;
	private BuyPopupWindow buyPop;
	private PullToRefreshView pull_bid_refresh;
	
	private List<Bid> bidList = new ArrayList<Bid>();
	
	private static int count = 1;
	private static int totalCount = 0;
	
	private static Request<BidResp> request;
	
	private Handler mHandler = new  Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				adapter.notifyDataSetChanged();
				pull_bid_refresh.onFooterRefreshComplete();
				break;
			case 2:
				pull_bid_refresh.onFooterRefreshComplete();
				break;
			case 3:
				Toast.makeText(MainActivity.this,msg.obj.toString() , Toast.LENGTH_LONG).show();

				break;
			case 4:
				BidDetailData detail = (BidDetailData)msg.obj;
				Bundle bundle = new Bundle();
				bundle.putSerializable("BidDetail", detail);
				Intent intent = new Intent(MainActivity.this, BidActivity.class);
				intent.putExtras(bundle);
				startActivity(intent);
				break;
			default:
				break;
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		findView();
		initView();
		getBidData();
	}


	private void findView() {
		lv_bid = (ListView)findViewById(R.id.lv_bid);
		tv_choose = (TextView)findViewById(R.id.tv_choose);
		rb_buy = (RadioButton)findViewById(R.id.rb_buy);
		rb_project = (RadioButton)findViewById(R.id.rb_project);
		pull_bid_refresh = (PullToRefreshView)findViewById(R.id.pull_bid_refresh);
	}

	private void initView() {
		tv_choose.setOnClickListener(chooseListener);
		pull_bid_refresh.setOnFooterRefreshListener(this);
		//rb_buy.setOnClickListener(this);
		//rb_project.setOnClickListener(this);
		adapter = new BidAdpter(this,bidList);
		lv_bid.setAdapter(adapter);
		lv_bid.setOnItemClickListener(bidItemListener);
	}
	
	

	
	
	private void getBidData() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				 request = new Request<BidResp>();
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs.add(new BasicNameValuePair("api_key", "app_key"));
				request.addParameter(Request.AJAXPARAMS, nameValuePairs);
				request.setUrl(Config.HTTP_SEARCH);
				request.setR_calzz(BidResp.class);
				try {
					AppSocketInterface socket = new XUtilsSocketImpl(
							MainActivity.this);
					BidResp bidResp = socket.shortConnect(request);
					if ("0".equals(bidResp.getCode())) {
						BidData bidData = bidResp.getData();
						if(bidData != null){
							count = Integer.parseInt(bidData.getCurrent_page());
							totalCount = Integer.parseInt(bidData.getTotal_page());
							bidList.addAll(bidData.getData());
							mHandler.obtainMessage(1).sendToTarget();
						}
					} else {
						mHandler.obtainMessage(2).sendToTarget();
						//Toast.makeText(MainActivity.this, bidResp.getMsg(),Toast.LENGTH_LONG).show();
					}
				} catch (BusinessException e) {
					e.printStackTrace();
					mHandler.obtainMessage(2).sendToTarget();
					//Toast.makeText(MainActivity.this,e.getErrorMessage().getMessage(), Toast.LENGTH_LONG).show();
				}
			}
		}).start();
	}
	
	
	private OnItemClickListener bidItemListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2,
				final long arg3) {
			
			ProgressDialogUtil.showProgressDialog(MainActivity.this, "通信中…", false);
			new Thread(new Runnable() {
				@Override
				public void run() {
					Request<BidDetailResp> request = new Request<BidDetailResp>();
					List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
					
					nameValuePairs.add(new BasicNameValuePair("url", bidList.get(arg2).getUrl()));
					/*try {
						nameValuePairs.add(new BasicNameValuePair("url", URLEncoder.encode("http://www.chinabidding.com.cn/zbgg/E6zU.html","GBK")));
					} catch (UnsupportedEncodingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}*/

					nameValuePairs.add(new BasicNameValuePair("api_key", "app_key"));
					request.addParameter(Request.AJAXPARAMS, nameValuePairs);
					request.setUrl(Config.HTTP_SEARCH_detail);
					request.setR_calzz(BidDetailResp.class);
					try {
						AppSocketInterface socket = new XUtilsSocketImpl(MainActivity.this);
						BidDetailResp bidDetailResp = socket.shortConnect(request);
						if("0".equals(bidDetailResp.getCode())){
							mHandler.obtainMessage(4,bidDetailResp.getData()).sendToTarget();
						}else{
							mHandler.obtainMessage(3,bidDetailResp.getMsg()).sendToTarget();
						}
					} catch (BusinessException e) {
						e.printStackTrace();
						mHandler.obtainMessage(3,e.getErrorMessage().getMessage()).sendToTarget();
					}
					
					ProgressDialogUtil.closeProgressDialog();
				}
			}).start();
		}
	};
	
	
	private OnClickListener chooseListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			//if(rbFlag){
			buyPop = new BuyPopupWindow(v, MainActivity.this);
			buyPop.pop.setOnDismissListener(popDismissListener);
		//	}else{
		//		new ProjectPopupWindow(v, MainActivity.this);
		//	}
		}
	};
	
	
	private OnDismissListener popDismissListener = new OnDismissListener() {
		
		@Override
		public void onDismiss() {
			if(buyPop != null && buyPop.getBidList().size() >0){
				bidList.clear();
				bidList.addAll(buyPop.getBidList());
				adapter.notifyDataSetChanged();
			}
		}
	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.rb_buy:
		//	rbFlag = true;
			break;
		case R.id.rb_project:
		//	rbFlag = false;
			break;

		default:
			break;
		}
	}



	@Override
	public void onFooterRefresh(PullToRefreshView view) {
		count++;
		if(count>totalCount){
			Toast.makeText(this, "没有更多了", Toast.LENGTH_LONG).show();
			mHandler.obtainMessage(2).sendToTarget();
			return;
		}
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					AppSocketInterface socket = new XUtilsSocketImpl(
							MainActivity.this);
					
					List<NameValuePair> nameValuePairs = (List<NameValuePair>) request
							.getParameter(Request.AJAXPARAMS);
					for (int i = 0; i < nameValuePairs.size(); i++) {
						BasicNameValuePair pair = (BasicNameValuePair) nameValuePairs.get(i);
						if("page".equals(pair.getName())){
							nameValuePairs.remove(i);
						}
					}
					nameValuePairs.add(new BasicNameValuePair("page", count+""));
					
					
					BidResp bidResp = socket.shortConnect(request);
					if ("0".equals(bidResp.getCode())) {
						BidData bidData = bidResp.getData();
						if(bidData != null){
							count = Integer.parseInt(bidData.getCurrent_page());
							totalCount = Integer.parseInt(bidData.getTotal_page());
							bidList.addAll(bidData.getData());
							mHandler.obtainMessage(1).sendToTarget();
						}
					} else {
						mHandler.obtainMessage(2).sendToTarget();
						Toast.makeText(MainActivity.this, bidResp.getMsg(),
								Toast.LENGTH_LONG).show();
					}
				} catch (BusinessException e) {
					e.printStackTrace();
					mHandler.obtainMessage(2).sendToTarget();
					Toast.makeText(MainActivity.this,
							e.getErrorMessage().getMessage(), Toast.LENGTH_LONG)
							.show();
				}
			}
		}).start();
	}
	
	public static void setCount(int count) {
		MainActivity.count = count;
	}
	
	public static void setTotalCount(int totalCount) {
		MainActivity.totalCount = totalCount;
	}
	
	public static void setRequest(Request<BidResp> request) {
		MainActivity.request = request;
	}
}
     