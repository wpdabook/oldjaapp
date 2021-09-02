package com.ljw.customseekbar;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;

/**
 * Created by 10430 on 2018/5/14.
 */

public class CustomSeekBar  extends View{
    //进度条颜色
    private @ColorInt int color= Color.parseColor("#0E84FA");
    //进度条颜色
    private @ColorInt int bgColor= Color.parseColor("#E5E5E5");
    //进度条颜色
    private @ColorInt int textBgColor= Color.parseColor("#1A8AFA");
    //竖线文字
    private @ColorInt int lineColor= Color.parseColor("#3B9BFB");
    //文字颜色
    private @ColorInt int textColor= Color.WHITE;
    //最大值
    private float max=100f;
    //最小值
    private float min=0f;

    public float getPosstion() {
        return posstion;
    }

    //当前值
    private float posstion=50f;
    //画笔
    private Paint posPaint;
    //控件宽
    private float measuredWidth;
    //控件高
    private float measuredHeight;
    //进度条高度
    private float seekBarHeight=10;
    //指示线宽度
    private float lineWidth=2;
    //文字显示背景宽度
    private float textBgWidth=80;
    //文字显示背景宽度
    private float textBgHeight=50;
    private float verticalCentre;
    private float seekBarHeightCentre;
    private float top;
    private float bottom;
    //底部滑块
    private Bitmap fromDrawable;
    //是否滑动
    private boolean isSlide=true;
    //最低高度
    private int minHeight=200;
    private float hkleft;

    public CustomSeekBar(Context context) {
        this(context,null);
    }


    public CustomSeekBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,1);
    }


    public CustomSeekBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    private void init(Context context){
        posPaint=new Paint();
        posPaint.setStyle(Paint.Style.FILL);
       // posPaint.setFlags(Paint);
        posPaint.setColor(color);
        posPaint.setAntiAlias(true);
        fromDrawable = getBitmapFromDrawable(context, R.drawable.ic_combined_shape);


    }

    /**
     * @param postion 设置当前进度
     */
public void setPosstion(float postion){

    ValueAnimator animator = ValueAnimator.ofFloat(this.posstion,postion);
    animator.setDuration(1500);
    //animator.setRepeatCount(1);
    AccelerateInterpolator accelerateInterpolator=new AccelerateInterpolator();

    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
         Log.e("value",animation.getAnimatedValue()+"");
           posstion=(float)animation.getAnimatedValue();
          postInvalidate();
        }
    });
    animator.start();

}
    /**
     * 是否允许滑动
     *  default ture
     * @param isSlide
     */
    public void  setNoSlide(boolean isSlide){
         this.isSlide=isSlide;
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width;
        int height ;
//        EXACTLY：一般是设置了明确的值或者是MATCH_PARENT
//
//        AT_MOST：表示子布局限制在一个最大值内，一般为WARP_CONTENT
//
//        UNSPECIFIED：表示子布局想要多大就多大，很少使用
        if (widthMode == MeasureSpec.EXACTLY)
        {
            width = widthSize;
        } else
        {
            // mPaint.setTextSize(mTitleTextSize);
            //  mPaint.getTextBounds(mTitle, 0, mTitle.length(), mBounds);
            //  float textWidth = mBounds.width();
            int desired = (int) (getPaddingLeft() + widthSize + getPaddingRight());
            width = desired;
        }

        if (heightMode == MeasureSpec.EXACTLY)
        {
            height = heightSize;
        } else
        {
            //  mPaint.setTextSize(mTitleTextSize);
            // mPaint.getTextBounds(mTitle, 0, mTitle.length(), mBounds);
            //  float textHeight = mBounds.height();
            //  int desired = (int) (getPaddingTop() + textHeight + getPaddingBottom());
            height = minHeight;
        }



        setMeasuredDimension(width, height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        measuredWidth = getMeasuredWidth();
        measuredHeight = getMeasuredHeight();
        //垂直中点
        verticalCentre = measuredHeight / 2;
        //进度条高度中点
        seekBarHeightCentre = seekBarHeight / 2;
        top = verticalCentre - seekBarHeightCentre;
        bottom = verticalCentre + seekBarHeightCentre;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画边框
      //  posPaint.setColor(bgColor);
       // posPaint.setStyle(Paint.Style.STROKE);
     //   canvas.drawRect(new RectF(0, 0, measuredWidth, measuredHeight), posPaint);

        //画进度条背景
        posPaint.setColor(bgColor);
        float textbgCentre = textBgWidth / 2;
        posPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(new RectF(textbgCentre, top, measuredWidth-textbgCentre, bottom), posPaint);

        //画当前进度
        posPaint.setColor(color);
        float right = (measuredWidth-textBgWidth) * (posstion<1?0f:posstion /100)+textbgCentre;
        canvas.drawRect(new RectF(textbgCentre, top, right, bottom), posPaint);

        //画竖线
        posPaint.setColor(lineColor);
        float top1 = top - 5 - textBgHeight;
        canvas.drawRect(new RectF(right-lineWidth, top1, right, bottom), posPaint);

        //画文字背景
        posPaint.setStyle(Paint.Style.FILL);
        posPaint.setColor(textBgColor);
        RectF rect = new RectF(right - textbgCentre -lineWidth, top1, right + textbgCentre, top - 5);
        canvas.drawRoundRect(rect,10,10,posPaint);

        //画文字
        posPaint.setColor(textColor);
        posPaint.setTextAlign(Paint.Align.CENTER);
        posPaint.setTextSize(18);
        Paint.FontMetrics fontMetrics = posPaint.getFontMetrics();
        //为基线到字体上边框的距离,即上图中的top
        float top = fontMetrics.top;
        //为基线到字体下边框的距离,即上图中的bottom
        float bottom = fontMetrics.bottom;
        //基线中间点的y轴计算公式
        int baseLineY = (int) (rect.centerY() - top/2 - bottom/2);
        canvas.drawText((int)posstion+"%",rect.centerX(),baseLineY,posPaint);

        //画滑块
        hkleft = right - 30 - 2;
        canvas.drawBitmap(fromDrawable, hkleft,this.bottom,posPaint);
        //canvas.get

    }

    public Bitmap getBitmapFromDrawable(Context context, @DrawableRes int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);

        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else if (drawable instanceof VectorDrawable || drawable instanceof VectorDrawableCompat) {
            Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, 60, 40);
            drawable.draw(canvas);
            return bitmap;
        } else {
            throw new IllegalArgumentException("unsupported drawable type");
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        return super.dispatchTouchEvent(event);
    }

    float x=0;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //判断是否允许滑动，不允许直接不做处理
       if (!isSlide)return isSlide;
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                 x = event.getX();
                float y = event.getY();
                if (x > hkleft && x < hkleft+fromDrawable.getWidth() && y > bottom && y < bottom+fromDrawable.getHeight()) {
                   // x = event.getX();
                    getParent().requestDisallowInterceptTouchEvent(true);
                }else{
                    getParent().requestDisallowInterceptTouchEvent(false);
                    break;
                }


            case MotionEvent.ACTION_MOVE:

                float x1 = event.getX();
                if (x< x1){

                    if (posstion>= max)return true;

                    posstion=posstion+0.4f;
                    invalidate();

                }else if (x> x1){

                    if (posstion<=min)return true;
                 //   getParent().requestDisallowInterceptTouchEvent(true);
                    posstion=posstion-0.4f;
                    invalidate();
                }else{

                }
                x=x1;
                return true;
        }

        return super.onTouchEvent(event);
    }




}
