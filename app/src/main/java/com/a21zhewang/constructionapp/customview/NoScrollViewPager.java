package com.a21zhewang.constructionapp.customview;

/**
 * Created by zhewang_ljw on 2017/5/25.
 * 控件命名规范：当前界面名+控件名+名字
 * 此文件作用：
 */

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 不可以滑动，但是可以setCurrentItem的ViewPager。
 */
public class NoScrollViewPager extends ViewPager {
    public void setScroll(boolean scroll) {
        isScroll = scroll;
    }

    private boolean isScroll=false;
    public NoScrollViewPager(Context context) {
        super(context);
    }

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        return isScroll;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        return isScroll;
    }
}