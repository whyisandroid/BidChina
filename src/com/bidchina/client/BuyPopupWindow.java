package com.bidchina.client;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.Toast;

import com.bidchina.client.net.AppSocketInterface;
import com.bidchina.client.net.BusinessException;
import com.bidchina.client.net.Request;
import com.bidchina.client.net.XUtilsSocketImpl;
import com.bidchina.client.until.Config;
import com.bidchina.client.until.ProgressDialogUtil;

public class BuyPopupWindow implements OnClickListener{
	private Context mContext;
	private View popview;
	public PopupWindow pop;
	private Button bt_choose_column;
	private Button bt_choose_city;
	private Button bt_choose_vocation;
	private Spinner sp_categrae;
	private Spinner sp_time;
	
	private RadioGroup rg_categrae;
	private int rg_checkid = 0;
	
	private EditText et_keywords;
	
	private Button bt_search;
	private static boolean[] columnSelected;
	private static boolean[] citySelected;
	private static boolean[] vocationSelected;
	private List<Bid> bidList = new ArrayList<Bid>();
	private Request<BidResp> request;
	
	private Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				pop.dismiss();
				break;
			case 2:
				Toast.makeText(mContext,msg.obj.toString() , Toast.LENGTH_LONG).show();
			default:
				break;
			}
		};
	};
	
	
	public BuyPopupWindow(View view,Context mContext) {
		this.mContext = mContext;
		LayoutInflater inflater = LayoutInflater.from(mContext);
		popview = inflater.inflate(R.layout.search_buy, null);
		pop = new PopupWindow(popview,
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, false);
		pop.setBackgroundDrawable(new Background2());
		pop.setOutsideTouchable(true);
		pop.setFocusable(true);
		pop.showAsDropDown(view);
		findView();
		initView();
	}
	private void findView() {
		bt_choose_column = (Button)popview.findViewById(R.id.bt_choose_column);
		bt_choose_city = (Button)popview.findViewById(R.id.bt_choose_city);
		bt_choose_vocation = (Button)popview.findViewById(R.id.bt_choose_vocation);
		sp_categrae = (Spinner)popview.findViewById(R.id.sp_categrae);
		sp_time = (Spinner)popview.findViewById(R.id.sp_time);
		bt_search = (Button)popview.findViewById(R.id.bt_search);
		et_keywords = (EditText)popview.findViewById(R.id.et_keywords);
		rg_categrae = (RadioGroup)popview.findViewById(R.id.rg_categrae);
	}
	
	private void initView() {
		bt_choose_column.setOnClickListener(this);
		bt_choose_city.setOnClickListener(this);
		bt_choose_vocation.setOnClickListener(this);
		bt_search.setOnClickListener(this);
		String[] cates = mContext.getResources().getStringArray(R.array.categare);
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(mContext,android.R.layout.simple_spinner_item,cates);
		sp_categrae.setAdapter(adapter);
		sp_categrae.setSelection(1);
		
		String[] times = mContext.getResources().getStringArray(R.array.time);
		ArrayAdapter<String> timeAdapter=new ArrayAdapter<String>(mContext,R.layout.spinnertext,times);
		sp_time.setAdapter(timeAdapter); 
		rg_categrae.setOnCheckedChangeListener(categraeCheckListener);
		
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_choose_column:
			String[] column = mContext.getResources().getStringArray(R.array.column);
			if(columnSelected == null){
				columnSelected = new boolean[column.length];//
			}
            new AlertDialog.Builder(mContext)
                    .setTitle("栏目")
                    // ����
                    .setMultiChoiceItems(R.array
                    		.column, columnSelected,
                            new DialogInterface.OnMultiChoiceClickListener() {// ���ö�ѡ��Ŀ
                                public void onClick(DialogInterface dialog,int which, boolean isChecked) {
                                    // do something
                                	columnSelected[which] = isChecked;
                                }
                            })
                    .setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                        int which) {
                                	int  m = 0;
                                	for (int i = 0; i < columnSelected.length; i++) {
										if(columnSelected[i] == true){
											m++;
										}
									}
                                	if(m == 0){
                                		bt_choose_column.setText("请选择:栏目");
                                	}else{
                                		bt_choose_column.setText("栏目已选:"+(m == 4?"全部":m)+"项");
                                	}
                                }
                            }).show();
			
			
			break;
		case R.id.bt_choose_city:
			String[] city = mContext.getResources().getStringArray(R.array.city);
			if(citySelected == null){
				citySelected = new boolean[city.length];// һ�����Booleanֵ������
			}
            new AlertDialog.Builder(mContext)
                    .setTitle("地区")
                    // ����
                    .setMultiChoiceItems(city, citySelected,
                            new DialogInterface.OnMultiChoiceClickListener() {// ���ö�ѡ��Ŀ
                                public void onClick(DialogInterface dialog,int which, boolean isChecked) {
                                    // do something
                                	citySelected[which] = isChecked;
                                }
                            })
                    .setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                        int which) {
                                	int  m = 0;
                                	for (int i = 0; i < citySelected.length; i++) {
										if(citySelected[i] == true){
											m++;
										}
									}
                                	if(m == 0){
                                		bt_choose_city.setText("请选择：地区");
                                	}else{
                                		bt_choose_city.setText("地区已选："+(m == 36?"全部":m)+"项");
                                	}
                                }
                            }).show();
			
			
			break;
			
		case R.id.bt_choose_vocation:
			String[] vocation = mContext.getResources().getStringArray(R.array.vocation);
			if(vocationSelected == null){
				vocationSelected = new boolean[vocation.length];// һ�����Booleanֵ������
			}
            new AlertDialog.Builder(mContext)
                    .setTitle("行业")
                    // ����
                    .setMultiChoiceItems(vocation, vocationSelected,
                            new DialogInterface.OnMultiChoiceClickListener() {// ���ö�ѡ��Ŀ
                                public void onClick(DialogInterface dialog,int which, boolean isChecked) {
                                    // do something
                                	vocationSelected[which] = isChecked;
                                }
                            })
                    .setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                        int which) {
                                	int  m = 0;
                                	for (int i = 0; i < vocationSelected.length; i++) {
										if(vocationSelected[i] == true){
											m++;
										}
									}
                                	if(m == 0){
                                		bt_choose_vocation.setText("请选择:行业");
                                	}else{
                                		bt_choose_vocation.setText("行业已选:"+(m == 3?"全部":m)+"项");
                                	}
                                }
                            }).show();
			
			
			break;
		case R.id.bt_search:
			// search
			
			ProgressDialogUtil.showProgressDialog(mContext, "搜索中…", false);
			new Thread(new Runnable() {
				@Override
				public void run() {
					request = new Request<BidResp>();
					List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
					
					nameValuePairs.add(new BasicNameValuePair("page", "1"));
					nameValuePairs.add(new BasicNameValuePair("rp", "15"));
					
					if(columnSelected != null){  //栏目
						String column = getColumn();
						nameValuePairs.add(new BasicNameValuePair("table_type", column));
					}
					
					
					if(vocationSelected != null){  // 行业
						String vocation = getVocation();
						nameValuePairs.add(new BasicNameValuePair("categoryid", vocation));
					}
					
					String  type = getCate();
					nameValuePairs.add(new BasicNameValuePair("search_type", type));
					
					if(citySelected != null){  // 城市
						String areaid = getAreaid();
						nameValuePairs.add(new BasicNameValuePair("areaid", areaid));
					}
					
					String date = getDate();
					nameValuePairs.add(new BasicNameValuePair("b_date", date));
					
					if(et_keywords.getText().toString().length() != 0){
						nameValuePairs.add(new BasicNameValuePair("keywords",et_keywords.getText().toString()));
					}
					nameValuePairs.add(new BasicNameValuePair("api_key", Config.API_KEY));
					request.addParameter(Request.AJAXPARAMS, nameValuePairs);
					request.setUrl(Config.HTTP_SEARCH);
					request.setR_calzz(BidResp.class);
					try {
						AppSocketInterface socket = new XUtilsSocketImpl(mContext);
						BidResp bidResp = socket.shortConnect(request);
						if("0".equals(bidResp.getCode())){
							BidData bidData = bidResp.getData();
							if(bidData != null){
								
								bidList = bidData.getData();
								MainActivity.setCount(1);
								MainActivity.setTotalCount(Integer.parseInt(bidData.getTotal_page()));
								MainActivity.setRequest(request);
								mHandler.obtainMessage(1).sendToTarget();
							}
						}else{
							mHandler.obtainMessage(2,bidResp.getMsg()).sendToTarget();
						}
					} catch (BusinessException e) {
						e.printStackTrace();
						mHandler.obtainMessage(2,e.getErrorMessage().getMessage()).sendToTarget();
					}
					
					ProgressDialogUtil.closeProgressDialog();
				}
			}).start();
		
			break;
		default:
			break;
		}
	}
	
	
	private OnCheckedChangeListener categraeCheckListener = new OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			rg_checkid = checkedId;
		}
	};
	
	
	private String getVocation() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < vocationSelected.length; i++) {
			if(vocationSelected[i] == true){
				if(i == 0){
					sb.append(3);
				}else if(i== 1){
					sb.append(17);
				}else if(i == 2){
					sb.append(18);
				}
				sb.append(",");
			}
		}
		if(sb.length() > 1){
			sb.deleteCharAt(sb.length() -1);
		}
		return sb.toString();
	}

	private String getColumn() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < columnSelected.length; i++) {
			if(columnSelected[i] == true){
				if(i == 0){
					sb.append(1);
				}else if(i== 1){
					sb.append(3);
				}else if(i == 2){
					sb.append(4);
				}else if(i == 3){
					sb.append(5);
				}
				sb.append(",");
			}
		}
		if(sb.length() > 1){
			sb.deleteCharAt(sb.length() -1);
		}
		return sb.toString();
	}
	

	private String getAreaid() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < citySelected.length; i++) {
			if(citySelected[i] == true){
				if(i == 34){
					sb.append(36);
				}else if(i== 35){
					sb.append(40);
				}else{
					sb.append(i+1);
				}
				sb.append(",");
			}
		}
		if(sb.length() > 1){
			sb.deleteCharAt(sb.length() -1);
		}
		return sb.toString();
	}

	private String getDate() {
		String cate = sp_time.getSelectedItem().toString();
		String value = "day";
		if("近一天".equals(cate)){
			value = "day";
		}else if("近一周".equals(cate)){
			value = "week";
		}else if("近二周".equals(cate)){
			value = "two";
		}else if("近一月".equals(cate)){
			value = "month";
		}else if("近三月".equals(cate)){
			value = "quarter";
		}else if("近一年".equals(cate)){
			value = "year";
		}
		return value;
	}

	private String getCate() {
		if(rg_checkid == 0){
			return "CONTEXT";
		}
		String cate = ((RadioButton)popview.findViewById(rg_checkid)).getText().toString();
		String value = "";
		if("标题".equals(cate)){
			value = "TITLE";
		}else if("内容".equals(cate)){
			value = "CONTEXT";
		}else if("附件".equals(cate)){
			value = "FJ";
		}else if("招标编号".equals(cate)){
			value = "BIDDINGNO";
		}
		
		return value;
	}
	
	public List<Bid> getBidList() {
		return bidList;
	}
	
	public Request<BidResp> getRequest() {
		return request;
	}
}
