package com.bidchina.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebSettings.TextSize;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bidchina.client.until.Config;
import com.bidchina.client.until.MD5Test;
import com.bidchina.client.until.ShareUtil;

public class BidActivity extends Activity {
	private TextView tv_detail_title;
	private TextView tv_description;
	private TextView tv_content;
	private TextView tv_detail_title2;
	private TextView tv_detail_date;
	private ImageButton main_top_left;
	
	private TextView tv_share;
	private BidDetailData bidDetail;
	
	private MyWebview wv_show;
	private String htmlData = "";
	
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
		
		wv_show = (MyWebview)findViewById(R.id.wv_show);
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
		
		htmlData = "<br>"+bidDetail.getTitle() +"<br><br>"+bidDetail.getDate() +bidDetail.getDescription()+bidDetail.getContent()+"<br>";
		htmlData = htmlData.replaceAll("&amp;", "");
        htmlData = htmlData.replaceAll("quot;", "\"");
        htmlData = htmlData.replaceAll("lt;", "<");
        htmlData = htmlData.replaceAll("gt;", ">");
        
        
        
        wv_show.getSettings().setTextSize(TextSize.LARGEST);
        wv_show.setInitialScale(39);  // 使用竖屏
        initWebViewSetting(wv_show);  
        wv_show.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN); 
        wv_show.setVisibility(View.VISIBLE);
        wv_show.loadDataWithBaseURL(null, htmlData, "text/html", "utf-8", null); 
        wv_show.setWebChromeClient(new WebChromeClient()); 
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
				String textDetail = htmlData;
				String url = Config.HTTP_SEARCH_SHARE + getURLType();
				ShareMessage share =  new ShareMessage(text,title,url,textDetail);
				ShareUtil.showShare(BidActivity.this, share);
			}
		}
	};
	
	private String getURLType() {
		StringBuffer sb = new  StringBuffer();
		String URL  = getIntent().getExtras().getString("URL");
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("url",URL));
		sb.append("url="+URL+"&");
		nameValuePairs.add(new BasicNameValuePair("api_key", Config.API_KEY));
		sb.append("api_key="+Config.API_KEY+"&");
		try {
			String sign =MD5Test.md5Sign(nameValuePairs);
			sb.append("signature="+sign);
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return sb.toString();
	}
	
	
	
	
private void initWebViewSetting(MyWebview webView) {
		
		WebSettings mWebSettings = webView.getSettings();
		// 有图 or 无图
		mWebSettings.setLoadsImagesAutomatically(true);
		// 弹窗
		mWebSettings.setSupportMultipleWindows(false);
		// 设置JavaScript有效性
		mWebSettings.setJavaScriptEnabled(true);
		mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);

		// 开启 HTML5 Web Storage
		mWebSettings.setDomStorageEnabled(true);
		mWebSettings.setDatabaseEnabled(true);
		// String databasePath = getContext().getDir("database",
		// Context.MODE_PRIVATE).getPath();
		// mWebSettings.setDatabasePath(databasePath);
		// 支持使用插件
		// mWebSettings.setPluginsEnabled(true);
		mWebSettings.setAllowFileAccess(true);
		// 2.2以上版本支持
		// if(android.os.Build.VERSION.SDK >
		// android.os.Build.VERSION_CODES.ECLAIR_MR1){
		// mWebSettings.setPluginState(PluginState.ON);
		// }
		mWebSettings.setNeedInitialFocus(true);
		// mWebSettings.setUserAgentString(WebSharedPref.USERAGENT);
		// 设置是否保存密码
		mWebSettings.setSavePassword(true);
		mWebSettings.setSaveFormData(true);
		// 保存cookies
		// CookieManager.getInstance().setAcceptCookie(true);
		mWebSettings.setSupportZoom(false);
		mWebSettings.setBuiltInZoomControls(false);
		// 配合概览模式参数，如果不设置，概览模式将不起作用
		mWebSettings.setUseWideViewPort(true);
		// 设置为概览模式
		mWebSettings.setLoadWithOverviewMode(true);
		// 缓存模式
		mWebSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		// if(NetWorkHelper.isNetworkAvailable(ApplyMainFragment.this.getActivity())){
		// mWebSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
		// }else{
		// mWebSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		// }
		//mWebSettings.setUseWideViewPort(true);
	}
	
}
