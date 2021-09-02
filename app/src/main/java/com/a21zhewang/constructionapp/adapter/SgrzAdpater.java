package com.a21zhewang.constructionapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.ui.sgrz.SgrzxqActivity;
import com.angel.adapter.SWRecyclerAdapter;
import com.angel.adapter.SWViewHolder;

import java.util.List;

/**
 * Created by zhewang_ljw on 2017/4/21.
 * 控件命名规范：当前界面名+控件名+名字
 * 此文件作用：
 */

public class SgrzAdpater extends SWRecyclerAdapter {

    Context context;
    List<String> list;

    public SgrzAdpater(Context context, List list) {
        super(context, list);
        this.context=context;
        this.list=list;
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.sgrz_list_item;
    }

    @Override
    public void bindData(SWViewHolder holder, int position, Object item) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, SgrzxqActivity.class));
            }
        });
    }


}
