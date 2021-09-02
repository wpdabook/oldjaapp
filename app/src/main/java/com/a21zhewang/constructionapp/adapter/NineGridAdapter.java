package com.a21zhewang.constructionapp.adapter;

import android.content.Context;
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
public class NineGridAdapter extends BaseAdapter {

    private Context mContext;
    private List<NineGridModel> mList;
    protected LayoutInflater inflater;

    public NineGridAdapter(Context context) {
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
        if(mList.get(position).getStatus() == 1){ //0:本地回复  1：第三方回复
            holder.layout.setIsShowAll(mList.get(position).isShowAll);
            holder.layout.setUrlList(mList.get(position).urlList);
            holder.uesr.setText(mList.get(position).getUser());
            holder.content.setText(mList.get(position).getContent());
            holder.createtime.setText(mList.get(position).getCreateTime());
            holder.uesr.setTextColor(android.graphics.Color.RED);
            holder.content.setTextColor(android.graphics.Color.RED);
            holder.createtime.setTextColor(android.graphics.Color.RED);
        }else {
            holder.layout.setIsShowAll(mList.get(position).isShowAll);
            holder.layout.setUrlList(mList.get(position).urlList);
            holder.uesr.setText(mList.get(position).getUser());
            holder.content.setText(mList.get(position).getContent());
            holder.createtime.setText(mList.get(position).getCreateTime());
            holder.uesr.setTextColor(mContext.getResources().getColor(R.color.color_theme));
            holder.content.setTextColor(mContext.getResources().getColor(R.color.color_666666));
            holder.createtime.setTextColor(mContext.getResources().getColor(R.color.color_666666));
        }
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
