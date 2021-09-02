package com.a21zhewang.constructionapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.bean.JdzfCompanys;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyDXCompanyAdapter extends BaseAdapter {

	private Context context = null;
	private List<JdzfCompanys> datas = null;
	/**
	 * CheckBox 是否选择的存储集合,key 是 position , value 是该position是否选中
	 */
	private Map<Integer, Boolean> isCheckMap = new HashMap<Integer, Boolean>();

	public MyDXCompanyAdapter(Context context, List<JdzfCompanys> datas) {
		this.datas = datas;
		this.context = context;
		// 初始化,默认都没有选中
		initDate(false);
	}

	/*** 首先,默认情况下,所有项目都是没有选中的.这里进行初始化*/
	public void initDate(boolean bool) {
		for (int i = 0; i < datas.size(); i++) {
			isCheckMap.put(i, bool);
		}
	}
	@Override
	public int getCount() {
		return datas == null ? 0 : datas.size();
	}

	@Override
	public Object getItem(int position) {
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View view, ViewGroup parent) {
		final JdzfCompanys JdzfCompanys = datas.get(position);
		ViewGroup layout = null;
		if (view == null) {
			layout = (ViewGroup) LayoutInflater.from(context).inflate(
					R.layout.listview_item_layout2, parent, false);
		} else {
			layout = (ViewGroup) view;
		}

		TextView tvTitle = (TextView) layout.findViewById(R.id.tvTitle);
		CheckBox cbCheck = (CheckBox) layout.findViewById(R.id.cbCheckBox);
		tvTitle.setText(JdzfCompanys.getEtpShortName());

		/** 设置单选按钮的选中*/
		cbCheck.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
										 boolean isChecked) {
				/**将选择项加载到map里面寄存*/
				isCheckMap.put(position, isChecked);
				if(isChecked){
					JdzfCompanys.setChecked(true);
				}else {
					JdzfCompanys.setChecked(false);
				}
			}
		});

//      ---------------------没有删除的时候使用-----------------
		if (isCheckMap.get(position) == null) {
			isCheckMap.put(position, false);
		}
		cbCheck.setChecked(isCheckMap.get(position));
		ViewHolder holder = new ViewHolder();
		holder.cbCheck = cbCheck;
		holder.tvTitle = tvTitle;
//		---------------------没有删除的时候使用-----------------
//		if (!canRemove) {
//			// 隐藏单选按钮,因为是不可删除的
//			cbCheck.setVisibility(View.GONE);
//			cbCheck.setChecked(false);
//		} else {
//			cbCheck.setVisibility(View.VISIBLE);
//
//			if (isCheckMap.get(position) == null) {
//				isCheckMap.put(position, false);
//			}
//
//			cbCheck.setChecked(isCheckMap.get(position));
//			ViewHolder holder = new ViewHolder();
//			holder.cbCheck = cbCheck;
//			holder.tvTitle = tvTitle;
//
//			/*** 将数据保存到tag*/
			layout.setTag(holder);
//		}
		/*** 将数据保存到tag*/
		return layout;
	}
	/**
	 * 增加一项的时候
	 */
	public void add(JdzfCompanys JdzfCompanys) {
		this.datas.add(0, JdzfCompanys);
		// 让所有项目都为不选择
		initDate(false);
	}

	// 移除一个项目的时候
	public void remove(int position) {
		this.datas.remove(position);
	}

	public Map<Integer, Boolean> GetisCheckMap() {
		return this.isCheckMap;
	}
	public static class ViewHolder {

		public TextView tvTitle = null;
		public CheckBox cbCheck = null;
		public Object data = null;

	}
}
