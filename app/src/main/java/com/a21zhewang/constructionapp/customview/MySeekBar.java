package com.a21zhewang.constructionapp.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.SeekBar;

/**
 * Created by WP-PC on 2019/11/26.
 */

public class MySeekBar extends android.support.v7.widget.AppCompatSeekBar {
    public MySeekBar(Context context) {
        super(context);
// TODO Auto-generated constructor stub
    }


    public MySeekBar(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.seekBarStyle);
    }


    public MySeekBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    /**
     * onTouchEvent 是在 SeekBar 继承的抽象类 AbsSeekBar 里 你可以看下他们的继承关系
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
// TODO Auto-generated method stub
// 原来是要将TouchEvent传递下去的,我们不让它传递下去就行了
// return super.onTouchEvent(event);


        return false;
    }
}
