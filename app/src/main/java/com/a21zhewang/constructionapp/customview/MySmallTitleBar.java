package com.a21zhewang.constructionapp.customview;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.a21zhewang.constructionapp.R;


/**
 * Created by zhewang on 2016/11/3.
 */

public class MySmallTitleBar extends LinearLayout {
    TextView titlenametv, backtv, righttextview,titlenameleft;
    ImageView backimg,title_rightimg;
    LinearLayout linearLayout;
    Context mContext;

    public MySmallTitleBar(Context context) {
        super(context);
    }

    public MySmallTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyTitleBar);
        String name = typedArray.getString(R.styleable.MyTitleBar_titletext);
        titlenametv.setText(name);
        String righttext = typedArray.getString(R.styleable.MyTitleBar_righttext);
        righttextview.setText(righttext);
        boolean isvisi = typedArray.getBoolean(R.styleable.MyTitleBar_backlin,true);
        boolean rigthisvisi = typedArray.getBoolean(R.styleable.MyTitleBar_rightvisiable,false);
        if (rigthisvisi){
            righttextview.setVisibility(VISIBLE);
        }else{
            righttextview.setVisibility(INVISIBLE);
        }
      if (!isvisi)linearLayout.setVisibility(INVISIBLE);
        typedArray.recycle();
    }

    private void initView(Context context) {
        mContext = context;
        // TODO Auto-generated method stub
        View.inflate(context, R.layout.smalltitle, this);
        titlenametv = (TextView) this.findViewById(R.id.title_TB_name);//标题文字
        titlenameleft = (TextView)this.findViewById(R.id.title_TB_name_left);
        backtv = (TextView) this.findViewById(R.id.title_back_tv);//返回文字
        righttextview = (TextView) this.findViewById(R.id.title_righttextview);
        linearLayout = (LinearLayout) this.findViewById(R.id.title_back);//返回布局
        backimg = (ImageView) this.findViewById(R.id.title_back_img);
        title_rightimg=(ImageView) this.findViewById(R.id.title_rightimg);
        linearLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) mContext).finish();
            }
        });
    }
    public void initIndexTitle(){
        titlenametv.setVisibility(GONE);
        titlenameleft.setVisibility(VISIBLE);
    }
    public void SetTitleTextSize(){
        titlenametv.setVisibility(VISIBLE);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        float value = dm.scaledDensity;
        titlenametv.setTextSize(18/value);
    }
    public void setback(OnClickListener onClickListener) {
        linearLayout.setOnClickListener(onClickListener);

    }
    public void setTitle_rightimgOnClick(OnClickListener onClickListener) {
        title_rightimg.setOnClickListener(onClickListener);

    }

public void setTitle_rightimgSrc(int srcID){
    title_rightimg.setImageResource(srcID);
}

    public void setTitlenametv(String text) {
        titlenametv.setText(text);
    }
    public void setLeftTitlenametv(String text) {
        titlenameleft.setText(text);
    }
    public void setRightTitlenametv(String text) {
        righttextview.setText(text);
    }

    public void setRighttextviewonclick(OnClickListener onClickListener) {
        righttextview.setOnClickListener(onClickListener);
    }

    public String getRightText() {
        return righttextview.getText().toString();
    }

    public void setleftvisiable() {
        linearLayout.setVisibility(GONE);
    }
    public void setrigthvisiable(int vsi) {
        righttextview.setVisibility(vsi);
    }
}
