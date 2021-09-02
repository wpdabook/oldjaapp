package com.a21zhewang.constructionapp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.bean.NineGridModel;
import com.a21zhewang.constructionapp.customview.NineGridLayout;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;


/**
 * 描述：
 * 作者：HMY
 * 时间：2016/5/13
 */
public class ProceduralHostoryAdapter extends BaseAdapter {

    private Context mContext;
    private List<NineGridModel> mList;
    protected LayoutInflater inflater;

    public ProceduralHostoryAdapter(Context context) {
        mContext = context;
        inflater = LayoutInflater.from(context);
    }

    public void setList(List<NineGridModel> list) {
        mList = list;
    }

    @Override
    public int getCount() {
        return getListSize(mList);
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null || convertView.getTag() == null) {
            convertView = inflater.inflate(R.layout.act_replydetail_item2, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        boolean isBack = mList.get(position).isBack();
        /**0.无此项   1：安全记录  2.有隐患记录*/
        int status = mList.get(position).getStatus();
        if(isBack == true){ //isBack ：true可以打回  false 不允许打回
            holder.uesr.setText("操作类型：打回");
        }else {
            if(status == 0){
                holder.uesr.setText("操作类型：无此项");
            }else if(status == 1){
                holder.uesr.setText("操作类型：符合");
            }else if(status == 2) {
                holder.uesr.setText("操作类型：不符合");
            }
        }
        holder.layout.setIsShowAll(mList.get(position).isShowAll);
        holder.layout.setUrlList(mList.get(position).urlList);
        holder.content.setText("检查内容："+mList.get(position).getContent());
        holder.createtime.setText(mList.get(position).getCreateTime());
        holder.uesr.setTextColor(mContext.getResources().getColor(R.color.color_theme));
        holder.content.setTextColor(mContext.getResources().getColor(R.color.color_666666));
        holder.createtime.setTextColor(mContext.getResources().getColor(R.color.color_666666));
        return convertView;
    }

    private class ViewHolder {
        TextView uesr;
        TextView content;
        TextView createtime;
        NineGridLayout layout;

        public ViewHolder(View view) {
            layout = (NineGridLayout) view.findViewById(R.id.layout_nine_grid);
            uesr = (TextView)view.findViewById(R.id.info);
            content = (TextView)view.findViewById(R.id.request);
            createtime = (TextView)view.findViewById(R.id.createtime);
        }
    }
    private int getListSize(List<NineGridModel> list) {
        if (list == null || list.size() == 0) {
            return 0;
        }
        return list.size();
    }
}
