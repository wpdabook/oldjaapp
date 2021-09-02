package com.a21zhewang.constructionapp.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.a21zhewang.constructionapp.R;
import com.angel.layout.SWPullRecyclerLayout;

/**
 * Created by zhewang_ljw on 2017/6/13.
 * 控件命名规范：当前界面名+控件名+名字
 * 此文件作用：
 */

public class MyRx extends SWPullRecyclerLayout {

    public MyRx(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyRx(Context context) {
        super(context);
        init(context);
    }
    public void init(Context context){
        View header = LayoutInflater.from(context).inflate(R.layout.header, null);
        View footer = LayoutInflater.from(context).inflate(R.layout.footer, null);
        this.addHeaderView(header, 100);
        this.addFooterView(footer, 100);
    }
}
