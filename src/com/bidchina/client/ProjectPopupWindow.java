package com.bidchina.client;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;

public class ProjectPopupWindow {
	private Context mContext;
	private View popview;
	public ProjectPopupWindow(View view,Context mContext) {
		this.mContext = mContext;
		LayoutInflater inflater = LayoutInflater.from(mContext);
		popview = inflater.inflate(R.layout.search_project, null);
		final PopupWindow pop = new PopupWindow(popview,
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, false);
		pop.setBackgroundDrawable(new Background2());
		pop.setOutsideTouchable(true);
		pop.setFocusable(true);
		pop.showAsDropDown(view);
		findView();
		initView();
	}
	
	
	private void findView() {
		
	}
	
	
	private void initView() {
		
	}
}
