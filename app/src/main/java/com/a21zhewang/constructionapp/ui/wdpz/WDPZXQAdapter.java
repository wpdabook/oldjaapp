package com.a21zhewang.constructionapp.ui.wdpz;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.bean.CheckType;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by 10430 on 2018/8/4.
 */

public class WDPZXQAdapter<T extends CheckType> extends BaseMultiItemQuickAdapter<T, BaseViewHolder> {
    private Context mContext;
    public static final int LEVEL_ONE = 0;
    public static final int LEVEL_TWO = 1;
    public static final int LEVEL_THREE = 2;
    public static final int LEVEL_FOUR = 3;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public WDPZXQAdapter(List<T> data) {
        super(data);
        addItemType(LEVEL_ONE, R.layout.wdpzxq_item1);
        addItemType(LEVEL_TWO, R.layout.wdpzxq_item2);
        addItemType(LEVEL_THREE, R.layout.pzwdxq_item3);
        addItemType(LEVEL_FOUR, R.layout.pzwdxq_item3);
        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                T item = getItem(position);
                switch (item.getItemType()){
                    case LEVEL_ONE:
                    case LEVEL_TWO:
                    case LEVEL_THREE:
                        setExpand(position,item);
                        break;
                }
            }
        });
    }

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    @Override
    protected void convert(final BaseViewHolder helper, final T item) {
switch (item.getItemType()){
    case LEVEL_ONE:
        setimg(helper,item,R.drawable.ic_toup2,R.drawable.ic_todown2);
        helper.setText(R.id.tv_title,item.getDicName());
        break;
    case LEVEL_TWO:
        setimg(helper,item,R.drawable.ic_toup,R.drawable.ic_todown);
        helper.setText(R.id.tv_title,item.getDicName());
        break;
    case LEVEL_THREE:

        setimg(helper,item,R.drawable.ic_group,R.drawable.ic_add);
        String value = item.getDicName()
                + (TextUtils.isEmpty(item.getPlanStartDate())?"":"\n开始时间:" + item.getPlanStartDate() )
                +(TextUtils.isEmpty(item.getPlanEndDate())?"": "\n结束时间:" + item.getPlanEndDate());

        helper.setText(R.id.tv_title, value);
        break;
    case LEVEL_FOUR:
        helper.setText(R.id.tv_title,item.getDicName());
        break;
}


    }

    /**
     * @param pos  展开 或收起
     * @param item
     */
    private void setExpand(int pos, T item) {

        if (item.isExpanded()) {

            collapse(pos, true);
        } else {
            expand(pos, true);
        }

    }
    private void setimg(BaseViewHolder helper, T item,int status1,int status2) {
        if (item.isExpanded()){
            helper.setImageResource(R.id.setchecklist_level0_item_ImageView,status1);
        }else{
            helper.setImageResource(R.id.setchecklist_level0_item_ImageView,status2);

        }
    }
}
