package com.a21zhewang.constructionapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.bean.TaskDetailBean;
import com.a21zhewang.constructionapp.customview.NineGridLayout;

import java.util.List;


/**
 * 描述：
 * 作者：HMY
 * 时间：2016/5/13
 */
public class TaskDetailAdapter extends BaseAdapter {

    private Context mContext;
    private List<TaskDetailBean> mList;
    protected LayoutInflater inflater;

    public TaskDetailAdapter(Context context) {
        mContext = context;
        inflater = LayoutInflater.from(context);
    }

    public void setList(List<TaskDetailBean> list) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null || convertView.getTag() == null) {
            convertView = inflater.inflate(R.layout.act_taskdetail_item_layout, parent, false);

            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        int index = position+1;
        if(mList.get(position).getContent() == null && mList.get(position).getQuestUrlList().size() == 0 ){
            holder.lin_question.setVisibility(View.GONE);
        }else {
//            holder.question_id.setText(""+ index);
            holder.question_id.setText("内容");
            holder.lin_question.setVisibility(View.VISIBLE);
            holder.question_layout.setIsShowAll(mList.get(position).isShowAll);
            holder.question_layout.setUrlList(mList.get(position).getQuestUrlList());
            holder.content.setText(mList.get(position).getContent());
            holder.content.setTextColor(mContext.getResources().getColor(R.color.color_666666));
        }
        if(mList.get(position).getRemark() == null && mList.get(position).getRequestUrlList().size() == 0){
            holder.lin_request.setVisibility(View.GONE);
        }else {
//            holder.request_id.setText("回复"+ index);
            holder.request_id.setText("过程");
            holder.lin_request.setVisibility(View.VISIBLE);
            holder.request_layout.setIsShowAll(mList.get(position).isShowAll);
            holder.request_layout.setUrlList(mList.get(position).getRequestUrlList());
            if(mList.get(position).getStatus() == 2){
                holder.remark.setText("整改内容："+mList.get(position).getRemark());
                holder.time.setText("整改时间："+mList.get(position).getCreateDate());
            }
            if(mList.get(position).getStatus() == 3 || mList.get(position).getStatus() == 4 ){
                holder.remark.setText("审核内容："+mList.get(position).getRemark());
                holder.time.setText("审核时间："+mList.get(position).getCreateDate());
            }
            holder.remark.setTextColor(mContext.getResources().getColor(R.color.color_666666));
        }
        //1.0  未读   2.0 未回复：问题没有整改完  3.0：已回复
//        if(mList.get(position).isRectObjTag() == true){  //未回复或无整改记录 !"3.0".equals(mList.get(position).getStatus())
//            holder.btn_add_record .setVisibility(View.VISIBLE);
//        }else {
//            holder.btn_add_record .setVisibility(View.GONE);
//        }
        return convertView;
    }

    private class ViewHolder {
        TextView question_id;
        TextView request_id;
        Button btn_add_record;
        NineGridLayout question_layout;
        NineGridLayout request_layout;
        TextView content;
        TextView remark;
        TextView time;
        LinearLayout lin_request;
        LinearLayout lin_question;

        public ViewHolder(View view) {
            question_id = (TextView)view.findViewById(R.id.question_id);
            request_id = (TextView)view.findViewById(R.id.request_id);
            btn_add_record = (Button)view.findViewById(R.id.btn_add_record);
            question_layout = (NineGridLayout) view.findViewById(R.id.layout_question_nine_grid);
            request_layout = (NineGridLayout) view.findViewById(R.id.layout_request_nine_grid);
            content = (TextView)view.findViewById(R.id.content);
            remark = (TextView)view.findViewById(R.id.remark);
            time = (TextView)view.findViewById(R.id.time);
            lin_question = (LinearLayout)view.findViewById(R.id.lin_question);
            lin_request = (LinearLayout)view.findViewById(R.id.lin_request);
        }
    }
    private int getListSize(List<TaskDetailBean> list) {
        if (list == null || list.size() == 0) {
            return 0;
        }
        return list.size();
    }
}
