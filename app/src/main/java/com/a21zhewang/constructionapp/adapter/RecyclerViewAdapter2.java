package com.a21zhewang.constructionapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.bean.GtxtxqBean;
import com.bumptech.glide.Glide;
import com.luck.picture.lib.photoview.OnViewTapListener;
import com.luck.picture.lib.photoview.PhotoView;

import java.util.List;



/**
 * Created by zhewang_ljw on 2017/3/15.
 * 控件命名规范：当前界面名+控件名+名字
 * 此文件作用：
 */

public class RecyclerViewAdapter2 extends RecyclerView.Adapter<RecyclerViewAdapter2.ViewHolder> {
    List<GtxtxqBean.FilesBean> filenames;
    PopupWindow popupWindow;
    Context  context;
    public RecyclerViewAdapter2(List<GtxtxqBean.FilesBean> filenames, PopupWindow popupWindow, Context  context) {
        super();
        this.filenames=filenames;
        this.popupWindow=popupWindow;
        this.context=context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        PhotoView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (PhotoView) itemView.findViewById(R.id.tupian_yulan_imageview);

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tupian_yulan_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        String key = filenames.get(position).getUrl();
        Glide.with(context).load(key).into(holder.imageView);
       // holder.imageView.setOnDoubleTapListener(null);
        holder.imageView.setOnViewTapListener(new OnViewTapListener() {
            @Override
            public void onViewTap(View view, float x, float y) {
                popupWindow.dismiss();
            }
        });

    }

    @Override
    public int getItemCount() {
        return filenames.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position, List payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }}