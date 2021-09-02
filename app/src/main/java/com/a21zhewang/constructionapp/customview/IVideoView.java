package com.a21zhewang.constructionapp.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

/**
 */

public class IVideoView extends VideoView {
    public void setmIMediaPlayState(IMediaPlayState mIMediaPlayState) {
        this.mIMediaPlayState = mIMediaPlayState;
    }

    private IMediaPlayState mIMediaPlayState;
    public IVideoView(Context context) {
        super(context);
    }

    public IVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public IVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //此处设置的默认值可随意,因为getDefaultSize中的size是有值的
        int width = getDefaultSize(0,widthMeasureSpec);
        int height = getDefaultSize(0,heightMeasureSpec);
        setMeasuredDimension(width,height);
    }

    @Override
    public void start() {
        super.start();
        if(mIMediaPlayState!=null){
            mIMediaPlayState.start();
        }

    }

    @Override
    public void pause() {
        super.pause();
        if(mIMediaPlayState!=null){
            mIMediaPlayState.pause();
        }

    }

    public interface IMediaPlayState{
        void start();//默认是没有start和pause回调的
        void pause();

    }

}
