package com.a21zhewang.constructionapp.ui.index;

import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;

import com.a21zhewang.constructionapp.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.HashSet;
import java.util.List;

/**
 * Created by 10430 on 2018/1/19.
 */

public abstract class SelectorAdapter<T> extends BaseQuickAdapter<T, BaseViewHolder> {

    private boolean isSelectors;
    private HashSet<T> hashSet = new HashSet<>();
    private T bean;

    abstract void imp(CheckBox view, T item);


    public boolean isSelectors() {
        return isSelectors;
    }

    public void setSelectors(boolean selectors) {
        isSelectors = selectors;
    }

    public HashSet<T> getHashSet() {
        return hashSet;
    }

    public void setHashSet(HashSet<T> hashSet) {
        this.hashSet = hashSet;
    }

    public T getBean() {
        return bean;
    }

    public void setBean(T bean) {
        this.bean = bean;
    }




    public SelectorAdapter(@Nullable List<T> data) {
        super(R.layout.list_item, data);
    }
    public SelectorAdapter(@LayoutRes int layoutResId, @Nullable List<T> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final T item) {
        final CheckBox itemView = (CheckBox) helper.itemView;

        if (!isSelectors) {
            if (item == bean)
                itemView.setChecked(true);
            else
                itemView.setChecked(false);
        } else {
            if (hashSet.contains(item)) {
                itemView.setChecked(true);

            } else {
                itemView.setChecked(false);

            }
        }

        itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!isSelectors) {//单选
                    if (item == bean){
                     //   bean=null;
                       // itemView.setChecked(false);
                    }
                    else{
                        itemView.setChecked(true);
                        bean = item;
                    }
                    notifyDataSetChanged();
                } else {
                    if (itemView.isChecked() && !hashSet.contains(item)) {
                        hashSet.add(item);
                    } else {
                        hashSet.remove(item);
                    }
                    notifyItemChanged(helper.getLayoutPosition());
                }
            }
        });
        imp(itemView, item);

    }
}
