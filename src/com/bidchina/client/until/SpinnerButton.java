package com.bidchina.client.until;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.Button;

import com.bidchina.client.VarItem;


/**
 * 单选弹出框按钮
 * 
 * @author ly
 * @version v1.0
 * @date 2013-5-1 09:30
 * 
 */
public class SpinnerButton extends Button implements DialogInterface.OnClickListener {
	/******************************************
	 * 标志常量
	 ******************************************/

	/******************************************
	 * 系统对象
	 ******************************************/
	/** 上下文 */
	Context context = null;
	/** 布局填充器 */
	LayoutInflater inflater = null;
	/** 资源管理者 */
	Resources resources = null;
	/** 内容提供接收者 */
	ContentResolver resolver = null;
	/******************************************
	 * 数据对象
	 ******************************************/
	private CharSequence prompt;
	private CharSequence[] entries;
	private CharSequence[] codes;
	private int index = -1;
	/******************************************
	 * 界面对象
	 ******************************************/
	private AlertDialog mPopup;

	/******************************************
	 * 构造函数
	 ******************************************/
	public SpinnerButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public SpinnerButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public SpinnerButton(Context context) {
		super(context);
		init();
	}

	/******************************************
	 * 成员函数
	 ******************************************/
	/**
	 * 初始化导航数据
	 */
	private void init() {
		// 系统对象
		context = getContext();
		inflater = LayoutInflater.from(context);
		resources = getResources();
		resolver = context.getContentResolver();
	}

	/**
	 * 设置标题
	 * 
	 * @param prompt
	 */
	public void setPrompt(CharSequence prompt) {
		this.prompt = prompt;
	}

	/**
	 * 设置单选列表内容
	 * 
	 * @param prompt
	 */
	public void setEntries(CharSequence[] entries) {
		this.entries = entries;
	}

	/**
	 * 设置单选列表内容
	 * 
	 * @param prompt
	 */
	public void setEntriesAndCode(ArrayList<VarItem> varItemList) {
		entries = new CharSequence[varItemList.size()];
		codes = new CharSequence[varItemList.size()];
		for (int i = 0; i < varItemList.size(); i++) {
			entries[i] = varItemList.get(i).getDesc();
			codes[i] = varItemList.get(i).getCode();
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		if (event.getAction() == MotionEvent.ACTION_UP) {
			if (mPopup != null && mPopup.isShowing()) {
				return super.onTouchEvent(event);
			}
			if (this.getText() != null && !"".equals(this.getText().toString())) {
				setIndexByString(this.getText().toString());
			} else {
				this.setIndex(0);
				if (mOnFinishClickListener != null) {
					mOnFinishClickListener.onItemClick(0);
				}
			}
			mPopup = new AlertDialog.Builder(context).setTitle(prompt).setSingleChoiceItems(entries, this.getIndex(), this).create();
			mPopup.setCanceledOnTouchOutside(true);
			mPopup.setCancelable(true);
			mPopup.show();
		}
		return super.onTouchEvent(event);
	}

	public int getIndex() {
		return index;
	}

	public CharSequence getCode() {
		if (index > -1 && index < codes.length) {
			return codes[index];
		}
		return "-1";
	}

	public void setIndex(int index) {
		this.index = index;
		if (entries != null) {
			this.setText(entries[index]);
		}
	}

	public void setIndexByString(String item) {
		if (entries != null) {
			for (int i = 0; i < entries.length; i++) {
				if (entries[i].equals(item)) {
					setIndex(i);
				}
			}
		}
	}

	public void setIndexByCode(String code) {
		for (int i = 0; i < codes.length; i++) {
			if (codes[i].equals(code)) {
				setIndex(i);
			}
		}
	}

	public void onClick(DialogInterface dialog, int whichButton) {
		setIndex(whichButton);
		if (mOnFinishClickListener != null) {
			mOnFinishClickListener.onItemClick(whichButton);
		}
		dialog.dismiss();
	}

	/**
	 * 设置完成选择后的回调
	 */
	private OnFinishClickListener mOnFinishClickListener;

	public interface OnFinishClickListener {
		void onItemClick(int index);
	}

	public void setOnItemClickListener(OnFinishClickListener l) {
		this.mOnFinishClickListener = l;
	}
}
