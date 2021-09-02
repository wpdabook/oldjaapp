package com.a21zhewang.constructionapp.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

/**
 * Created by WP-PC on 2020/3/12.
 */

public class CustomVideoView extends VideoView{
    public CustomVideoView(Context context) {
        super(context);
    }

    public CustomVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //此处设置的默认值可随意,因为getDefaultSize中的size是有值的
        int width = getDefaultSize(0,widthMeasureSpec);
        int height = getDefaultSize(0,heightMeasureSpec);
        setMeasuredDimension(width,height);
        System.out.println("======onMeasure===width==="+width);
        System.out.println("======onMeasure===height==="+height);
    }
}
