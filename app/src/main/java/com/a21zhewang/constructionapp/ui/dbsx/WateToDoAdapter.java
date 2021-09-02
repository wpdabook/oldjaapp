package com.a21zhewang.constructionapp.ui.dbsx;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.adapter.ClassAdapter;
import com.a21zhewang.constructionapp.bean.ClassBean;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;

import java.util.List;

/**
 * Created by fan.feng on 2017/9/9.
 */

public class WateToDoAdapter extends ClassAdapter<WateToDoAdapter.ClassHolder, WateToDoAdapter.StudentHolder> {
    private Context context;
    private List<ClassBean> mContent;
    private LayoutInflater mInflater;
    private OnItemClickListener listener;
    private boolean isVerisable = false;

    public WateToDoAdapter(Context context, List mContent) {
        this.context = context;
        this.mContent = mContent;
        mInflater = LayoutInflater.from(context);
    }
    public interface OnItemClickListener {
        void onItemClick(ClassBean classBean, int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
    @Override
    public int getHeadersCount() {
        return mContent.size();
    }

    @Override
    public int getContentCountForHeader(int headerPosition) {
        int count = mContent.get(headerPosition).classStudents.size();
        return count;
    }

    /**
     * 创建头布局header的viewholder
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public WateToDoAdapter.ClassHolder onCreateHeaderViewHolder(ViewGroup parent, int viewType) {
        return new ClassHolder(mInflater.inflate(R.layout.item_todo_title, parent, false));
    }

    /**
     * 创建内容布局item的viewholder
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public WateToDoAdapter.StudentHolder onCreateContentViewHolder(ViewGroup parent, int viewType) {
        return new StudentHolder(mInflater.inflate(R.layout.item_todo_content, parent, false));
    }

    @Override
    public void onBindHeaderViewHolder(WateToDoAdapter.ClassHolder holder, int position) {
        holder.tvClassName.setOnClickListener(null);
        if(mContent.get(position).classStudents.size() == 0){
            holder.tvClassName.setVisibility(View.GONE);
          /*  holder.topline.setVisibility(View.GONE);
            holder.bottomline.setVisibility(View.GONE);*/
        }else{
            holder.tvClassName.setText(mContent.get(position).className);
        }
        holder.tvClassName.setTag(position);
    }

    @Override
    public void onBindContentViewHolder(StudentHolder holder, final int HeaderPosition, final int ContentPositionForHeader) {
        final ClassBean classBean = mContent.get(HeaderPosition);
        holder.tvName.setText(mContent.get(HeaderPosition).classStudents.get(ContentPositionForHeader));
        String TitleName = mContent.get(HeaderPosition).classStudents.get(ContentPositionForHeader);
        if(PublicUtils.getspint(TitleName) != 0){
            holder.number.setVisibility(View.VISIBLE);
        }else {
            holder.number.setVisibility(View.GONE);
//            holder.lin_content.setVisibility(View.GONE);
        }
        holder.number.setText(PublicUtils.getspint(TitleName)+"");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(classBean,ContentPositionForHeader);
                }
            }
        });

    }

    public void setImage(String str,String title,ImageView image,int drawableId){
        if(str.equals(title)){
            image.setImageResource(drawableId);
        }
    }

    class ClassHolder extends RecyclerView.ViewHolder{

        public TextView tvClassName;
      /*  public View topline;
        public View bottomline;*/


        public ClassHolder(View itemView) {
            super(itemView);
            tvClassName = (TextView) itemView.findViewById(R.id.tvInfo);
           /* topline = (View)itemView.findViewById(R.id.line);
            bottomline = (View)itemView.findViewById(R.id.line_bottom);*/
        }
    }

    class StudentHolder extends RecyclerView.ViewHolder{

        public TextView tvName;
        public TextView number;
        public LinearLayout lin_content;
        public StudentHolder(View itemView) {
            super(itemView);
            tvName =  (TextView) itemView.findViewById(R.id.tvName);
            number = (TextView) itemView.findViewById(R.id.index_item_tv_number);
            lin_content = (LinearLayout)itemView.findViewById(R.id.lin_content);
        }
    }

}
