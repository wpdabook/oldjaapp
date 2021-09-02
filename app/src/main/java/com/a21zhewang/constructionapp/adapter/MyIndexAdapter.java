package com.a21zhewang.constructionapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.bean.ClassBean;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import java.util.List;

/**
 * Created by fan.feng on 2017/9/9.
 */

public class MyIndexAdapter extends ClassAdapter<MyIndexAdapter.ClassHolder, MyIndexAdapter.StudentHolder> {

    private Context context;
    private List<ClassBean> mContent;
    private LayoutInflater mInflater;
    private OnItemClickListener listener;

    public MyIndexAdapter(Context context, List mContent) {
        this.context = context;
        this.mContent = mContent;
        mInflater = LayoutInflater.from(context);
    }
    public interface OnItemClickListener {
        void onItemClick(ClassBean classBean,int position);
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
    public MyIndexAdapter.ClassHolder onCreateHeaderViewHolder(ViewGroup parent, int viewType) {
        return new ClassHolder(mInflater.inflate(R.layout.item_title, parent, false));
    }

    /**
     * 创建内容布局item的viewholder
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public MyIndexAdapter.StudentHolder onCreateContentViewHolder(ViewGroup parent, int viewType) {
        return new StudentHolder(mInflater.inflate(R.layout.item_content, parent, false));
    }

    @Override
    public void onBindHeaderViewHolder(MyIndexAdapter.ClassHolder holder, int position) {
        holder.tvClassName.setOnClickListener(null);
        if(position == 0 || mContent.get(position).classStudents.size() == 0){
            holder.tvClassName.setVisibility(View.GONE);
            holder.topline.setVisibility(View.GONE);
            holder.bottomline.setVisibility(View.GONE);
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
        }
        holder.number.setText(PublicUtils.getspint(TitleName)+"");
        /**
         * 24模块图标设置
         */
        setImage("小助手",TitleName,holder.image,R.mipmap.mhelp);
        setImage("施工日志",TitleName,holder.image,R.mipmap.m7);
        setImage("沟通协调",TitleName,holder.image,R.mipmap.m1);
        setImage("质量检查",TitleName,holder.image,R.mipmap.m2);
        setImage("文明施工",TitleName,holder.image,R.mipmap.m4);
        setImage("安全检查",TitleName,holder.image,R.mipmap.m3);
        setImage("施工提醒",TitleName,holder.image,R.mipmap.m6);
        setImage("通知公告",TitleName,holder.image,R.mipmap.m5);
        setImage("工序验收",TitleName,holder.image,R.mipmap.m8);
        setImage("专项检查",TitleName,holder.image,R.mipmap.m10);
        setImage("项目信息",TitleName,holder.image,R.mipmap.m9);
        setImage("汇总统计",TitleName,holder.image,R.mipmap.m11);
        setImage("进度填报",TitleName,holder.image,R.mipmap.m12);
        setImage("危大动态上报",TitleName,holder.image,R.mipmap.m13);
        setImage("知识库",TitleName,holder.image,R.mipmap.m14);
        setImage("重点检查",TitleName,holder.image,R.mipmap.m15);
        setImage("决策小屏",TitleName,holder.image,R.mipmap.m16);
        setImage("今日小屏",TitleName,holder.image,R.mipmap.m17);
        setImage("执法单",TitleName,holder.image,R.mipmap.m19);
        setImage("通知简报",TitleName,holder.image,R.mipmap.m21);
        setImage("风险类型上报",TitleName,holder.image,R.mipmap.m22);
        setImage("程序检查",TitleName,holder.image,R.mipmap.m23);
        setImage("疫情复工检查",TitleName,holder.image,R.mipmap.m24);
        setImage("进度上报",TitleName,holder.image,R.mipmap.m25);
        setImage("网格员管理",TitleName,holder.image,R.mipmap.m26);
        setImage("专项巡查",TitleName,holder.image,R.mipmap.m27);
        setImage("监督管理(第三方)",TitleName,holder.image,R.mipmap.m28);
        setImage("工程导航",TitleName,holder.image,R.mipmap.m30);
        setImage("重点督办",TitleName,holder.image,R.mipmap.m31);
        setImage("简易告知",TitleName,holder.image,R.mipmap.m35);
        setImage("日常监督",TitleName,holder.image,R.mipmap.m37);
        setImage("考勤管理",TitleName,holder.image,R.mipmap.m38);
        setImage("视频监控",TitleName,holder.image,R.mipmap.m39);
        setImage("任务督办",TitleName,holder.image,R.drawable.ic_task_24dp);
        setImage("通知文件",TitleName,holder.image,R.mipmap.m33);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(classBean,ContentPositionForHeader);
                }
                int title = HeaderPosition+1;
                int item = ContentPositionForHeader +1;
//                Toast.makeText(context, "我点击了标题"+ title +"第"+ item+"项", Toast.LENGTH_SHORT).show();
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
        public View topline;
        public View bottomline;

        public ClassHolder(View itemView) {
            super(itemView);

            tvClassName = (TextView) itemView.findViewById(R.id.tvInfo);
            topline = (View)itemView.findViewById(R.id.line);
            bottomline = (View)itemView.findViewById(R.id.line_bottom);
        }
    }

    class StudentHolder extends RecyclerView.ViewHolder{

        public TextView tvName;
        public ImageView image;
        public TextView number;
        public StudentHolder(View itemView) {
            super(itemView);
            tvName =  (TextView) itemView.findViewById(R.id.tvName);
            image = (ImageView)itemView.findViewById(R.id.index_item_img);
            number = (TextView) itemView.findViewById(R.id.index_item_tv_number);
        }
    }

}
