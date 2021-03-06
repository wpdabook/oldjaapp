package com.a21zhewang.constructionapp.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Camera;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.LinearLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.a21zhewang.constructionapp.R;

import java.util.ArrayList;
import java.util.List;


public class AutoTextView<T> extends TextSwitcher implements
        ViewSwitcher.ViewFactory {

    private float mHeight;
    private Context mContext;
    //mInUp,mOutUp分别构成向下翻页的进出动画
    private Rotate3dAnimation mInUp;
    private Rotate3dAnimation mOutUp;

    //mInDown,mOutDown分别构成向下翻页的进出动画
    private Rotate3dAnimation mInDown;
    private Rotate3dAnimation mOutDown;
    private Handler handler;
    private Runnable runnable;
    private String text;
    private List<String> texts;
    int index = 0;
    private AutoTextViewAPI autoTextViewAPI;
    private int onclickindex;

    public AutoTextView(Context context) {
        this(context, null);
        // TODO Auto-generated constructor stub
    }

    public interface AutoTextViewAPI<T> {
        String setstrs(T str);

        void onclick(T obj);
    }

    public AutoTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.auto3d);
        mHeight = a.getDimension(R.styleable.auto3d_textSize, 16);
        a.recycle();
        mContext = context;
        init();

    }

    public void setstr(String str) {
        this.text = str;
    }

    public void setstr(List<String> str) {
        this.texts.clear();
        this.texts.addAll(str);
    }
    public void removeCallbacks(){
        handler.removeCallbacks(runnable);
    }

    public void setstr(final List<T> str, AutoTextViewAPI autotvAPI) {

        texts.clear();
        text="";
        this.autoTextViewAPI = autotvAPI;
        for (int i = 0, j = str.size(); i < j; i++) {
            texts.add(autoTextViewAPI.setstrs(str.get(i)));
        }
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (str.size() == 0) return;
                if (index < str.size()) {
                    onclickindex = index;
                    autoTextViewAPI.onclick(str.get(onclickindex));

                } else {
                    index = 0;
                    onclickindex = index;
                    autoTextViewAPI.onclick(str.get(onclickindex));
                }
            }
        });
    }


    private void init() {
        // TODO Auto-generated method stub
        setFactory(this);
        mInUp = createAnim(-90, 0, true, true);
        mOutUp = createAnim(0, 90, false, true);
        mInDown = createAnim(90, 0, true, false);
        mOutDown = createAnim(0, -90, false, false);
        //TextSwitcher主要用于文件切换，比如 从文字A 切换到 文字 B，
        //setInAnimation()后，A将执行inAnimation，
        //setOutAnimation()后，B将执行OutAnimation
        setInAnimation(mInUp);
        setOutAnimation(mOutUp);
        setClickable(true);
        this.texts = new ArrayList<>();

    }

    private Rotate3dAnimation createAnim(float start, float end, boolean turnIn, boolean turnUp) {
        final Rotate3dAnimation rotation = new Rotate3dAnimation(start, end, turnIn, turnUp);
        rotation.setDuration(800);
        rotation.setFillAfter(false);
        rotation.setInterpolator(new AccelerateInterpolator());
        return rotation;
    }

    //这里返回的TextView，就是我们看到的View
    @Override
    public View makeView() {
        // TODO Auto-generated method stub
        TextView t = new TextView(mContext);
        t.setGravity(Gravity.CENTER);
        t.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        t.setTextSize(mHeight);
        t.setMaxLines(1);
        t.setTextColor(Color.parseColor("#666666"));
        return t;
    }

    //定义动作，向下滚动翻页
    public void previous() {
        if (getInAnimation() != mInDown) {
            setInAnimation(mInDown);
        }
        if (getOutAnimation() != mOutDown) {
            setOutAnimation(mOutDown);
        }
    }

    //定义动作，向上滚动翻页
    public void next() {
        if (getInAnimation() != mInUp) {
            setInAnimation(mInUp);
        }
        if (getOutAnimation() != mOutUp) {
            setOutAnimation(mOutUp);
        }
    }

    class Rotate3dAnimation extends Animation {
        private final float mFromDegrees;
        private final float mToDegrees;
        private float mCenterX;
        private float mCenterY;
        private final boolean mTurnIn;
        private final boolean mTurnUp;
        private Camera mCamera;

        public Rotate3dAnimation(float fromDegrees, float toDegrees, boolean turnIn, boolean turnUp) {
            mFromDegrees = fromDegrees;
            mToDegrees = toDegrees;
            mTurnIn = turnIn;
            mTurnUp = turnUp;
        }

        @Override
        public void initialize(int width, int height, int parentWidth, int parentHeight) {
            super.initialize(width, height, parentWidth, parentHeight);
            mCamera = new Camera();
            mCenterY = getHeight() / 2;
            mCenterX = getWidth() / 2;
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            final float fromDegrees = mFromDegrees;
            float degrees = fromDegrees + ((mToDegrees - fromDegrees) * interpolatedTime);

            final float centerX = mCenterX;
            final float centerY = mCenterY;
            final Camera camera = mCamera;
            final int derection = mTurnUp ? 1 : -1;

            final Matrix matrix = t.getMatrix();

            camera.save();
            if (mTurnIn) {
                camera.translate(0.0f, derection * mCenterY * (interpolatedTime - 1.0f), 0.0f);
            } else {
                camera.translate(0.0f, derection * mCenterY * (interpolatedTime), 0.0f);
            }
            camera.rotateX(degrees);
            camera.getMatrix(matrix);
            camera.restore();

            matrix.preTranslate(-centerX, -centerY);
            matrix.postTranslate(centerX, centerY);
        }
    }

    public void startloop() {

        if (handler == null)
            handler = new Handler();
        if (runnable == null) {
            runnable = new Runnable() {
                @Override
                public void run() {
                    if (texts != null && texts.size() > 0 && index < texts.size()) {
                        text = texts.get(index);
                        index++;
                    } else {
                        index = 0;
                    }
                    setText(text);
                    handler.postDelayed(runnable, 2000);
                }
            };
        }
        handler.post(runnable);

    }
}
