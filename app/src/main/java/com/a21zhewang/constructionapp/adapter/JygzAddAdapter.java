package com.a21zhewang.constructionapp.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.bean.NineGridModel;
import com.a21zhewang.constructionapp.customview.NineGridLayout;

import java.util.List;


/**
 * 描述：
 * 作者：HMY
 * 时间：2016/5/13
 */
public class JygzAddAdapter extends BaseAdapter {

    private Context mContext;
    private List<NineGridModel> mList;
    protected LayoutInflater inflater;

    public JygzAddAdapter(Context context) {
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
            convertView = inflater.inflate(R.layout.act_jygzadd_adapter_item2, parent, false);

            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        int index = position+1;
        holder.title_id.setText(index+"");
        if(mList.get(position).getStatus() == 0){  //0.无此项   1：安全记录  2.有隐患记录
            holder.layout.setIsShowAll(mList.get(position).isShowAll);
            holder.layout.setUrlList(mList.get(position).urlList);
            holder.content.setText(mList.get(position).getContent());
            holder.content.setTextColor(mContext.getResources().getColor(R.color.color_666666));
        }else if(mList.get(position).getStatus() == 1){
            holder.layout.setIsShowAll(mList.get(position).isShowAll);
            holder.layout.setUrlList(mList.get(position).urlList);
            holder.content.setText(mList.get(position).getContent());
            holder.content.setText(mList.get(position).getQuestioncontent());
            holder.content.setTextColor(mContext.getResources().getColor(R.color.color_666666));
        }else if(mList.get(position).getStatus() == 2) {
            holder.layout.setIsShowAll(mList.get(position).isShowAll);
            holder.layout.setUrlList(mList.get(position).urlList);
            holder.content.setText(mList.get(position).getContent());
            holder.content.setTextColor(mContext.getResources().getColor(R.color.color_666666));
        }

        if(TextUtils.isEmpty(mList.get(position).getQuestioncontent())){
            holder.question_content.setVisibility(View.GONE);
        }else {
            holder.question_content.setVisibility(View.VISIBLE);
            holder.question_content.setText("检查内容："+mList.get(position).getQuestioncontent());
        }
        return convertView;
    }

    private class ViewHolder {
        TextView title_id;
        TextView content;
        TextView question_content;
        NineGridLayout layout;

        public ViewHolder(View view) {
            layout = (NineGridLayout) view.findViewById(R.id.layout_nine_grid);
            content = (TextView)view.findViewById(R.id.request);
            question_content = (TextView)view.findViewById(R.id.question_content);
            title_id = (TextView)view.findViewById(R.id.title_id);
        }
    }
    private int getListSize(List<NineGridModel> list) {
        if (list == null || list.size() == 0) {
            return 0;
        }
        return list.size();
    }
}
