package com.a21zhewang.constructionapp.adapter;

import android.content.Context;

import com.zhy.adapter.recyclerview.CommonAdapter;

import java.util.List;

/**
 * Created by zhewang_ljw on 2017/6/2.
 * 控件命名规范：当前界面名+控件名+名字
 * 此文件作用：
 */

public  abstract class BaseAdapter<T>  extends CommonAdapter<T> {


    public BaseAdapter(Context context, int layoutId, List datas) {
        super(context, layoutId, datas);
    }

    public void add(int positon, T item) {
        mDatas.add(positon, item);
        notifyItemInserted(positon);
    }

    public void addall(List<T> ls) {
        mDatas.addAll(ls);
        notifyItemRangeChanged(0,mDatas.size());
    }

    public void delete(int positon) {
        mDatas.remove(positon);
        notifyItemRemoved(positon);

    }
    public void deleteall(){
        if (mDatas.size()>0){
        int endindex=mDatas.size();
        mDatas.clear();
        notifyItemRangeRemoved(0,endindex);
        }
    }
    public List<T> getlist(){
        return mDatas;
    }
}
