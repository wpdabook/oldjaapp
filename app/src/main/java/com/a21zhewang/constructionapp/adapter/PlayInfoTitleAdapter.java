package com.a21zhewang.constructionapp.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.bean.PlayUserList;
import java.util.List;

/**
 * Created by tian on 2018/10/8.
 */

public class PlayInfoTitleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private SparseArray<String> titles = new SparseArray<>();
    private List<PlayUserList> dataList;
    private Context mContext;

    public PlayInfoTitleAdapter(Context context, List<PlayUserList> dataList){
        this.mContext = context;
        this.dataList = dataList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(isTitle(viewType)){
            View v = LayoutInflater.from(mContext).inflate(R.layout.play_info_title, parent, false);
            TitleViewHolder titleViewHolder = new TitleViewHolder(v);
            return titleViewHolder;
        }else {
            View v = LayoutInflater.from(mContext).inflate(R.layout.item_play_info, parent, false);
            MyViewHolder myViewHolder = new MyViewHolder(v);
            return myViewHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(isTitle(position)){
            TitleViewHolder titleViewHolder = (TitleViewHolder) holder;
            titleViewHolder.tv.setText(titles.get(position));
            return;
        }
        //获取空过去的item（获取真实的item的位置，即去掉title后的真实位置）
        for (int i = 0; i < titles.size(); i++){
            int key = titles.keyAt(i);
            if(position > titles.keyAt(titles.size() -1)){  //item在最后一个title之后的情况
                position -= titles.size();
                break;
            }else if(key < position && position < titles.keyAt(i + 1)){ //item在某两个title之间的情况
                position -= (i + 1);
                break;
            }
        }
        final int s = position;
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.name.setText(dataList.get(s).getUserName());
        if(dataList.get(position).getIsKey() == 1){
            myViewHolder.focus.setText("✔");
        }else {
            myViewHolder.focus.setText("✖");
        }
        if(dataList.get(position).getIsProg() == 1){
            myViewHolder.produl.setText("✔");
        }else {
            myViewHolder.produl.setText("✖");
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size() + titles.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(isTitle(position)) {
            return position;  //返回title的位置，仅用作onCreateViewHolder方法中的判断--是title
        }else{
            return position + 100000; //返回title+100000的位置，仅用作onCreateViewHolder方法中的判断--不是title
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        //如果是title就占据设置的spanCount个单元格
//        final GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
        final RecyclerView.LayoutManager layoutManager = (RecyclerView.LayoutManager)recyclerView.getLayoutManager();
        //Sets the source to get the number of spans occupied by each item in the adapter.
//        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(int position) {
//                if(isTitle(position)){
//                    return layoutManager.getSpanCount();
//                }
//                return 1;
//            }
//        });

    }

    private boolean isTitle(int position){
        return titles.get(position) == null ? false : true;
    }

    /**
     *
     * @param position 插入item的位置，注意这里的下标是包含title的（title算一个item，并且所有item随着插入的title的增多而改变），
     *                 即该position参数可以理解为包含title的所有item中title所处于的插入的位置
     * @param title
     */
    public void addTitle(int position, String title){
        titles.put(position, title);
    }

    public static class TitleViewHolder extends RecyclerView.ViewHolder{
        TextView tv;

        public TitleViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv_grid_item_title);
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView focus;
        TextView produl;
        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            focus = (TextView) itemView.findViewById(R.id.focus);
            produl = (TextView) itemView.findViewById(R.id.produl);
        }
    }
}
