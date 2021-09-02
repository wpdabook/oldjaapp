package com.a21zhewang.constructionapp.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;

/**
 * Created by zhewang_ljw on 2017/4/19.
 * 控件命名规范：当前界面名+控件名+名字
 * 此文件作用：
 */

public class MyIndexItem extends LinearLayout {
   private ImageView imageView;
    private TextView numbertv;
    private TextView nametv;
    private TextView jdtv;
    private AutoLinearLayout index_item_lin;
    private RelativeLayout index_item_default;
    Context mContext;
    int[] mArray = new int[]{R.drawable.jianbian1};

    public MyIndexItem(Context context) {
        super(context);
        this.mContext=context;
        init();
    }

    public MyIndexItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext=context;
        init();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyIndexItem);
        try {
            int resourceId = typedArray.getResourceId(R.styleable.MyIndexItem_imgsrc, 0);
            imageView.setImageResource(resourceId);
            String string = typedArray.getString(R.styleable.MyIndexItem_tvname);
            nametv.setText(string);
        }catch (Exception e ){

        }finally {
            typedArray.recycle();
        }
    }
    public void init(){
        View.inflate(mContext, R.layout.index_item3,this);
        imageView= (ImageView) findViewById(R.id.index_item_img);
        numbertv= (TextView) findViewById(R.id.index_item_tv_number);
        nametv= (TextView) findViewById(R.id.index_item_tv_name);
        jdtv= (TextView) findViewById(R.id.index_item_tv_jd);
        index_item_lin= (AutoLinearLayout) findViewById(R.id.index_item_lin);
        index_item_default = (RelativeLayout) findViewById(R.id.index_item_default);
        ArrayList<Integer> indexs = new ArrayList<Integer>();
        int n = mArray.length;
        int i = 0;
        while (i < n) {
            int rnd = (int) (Math.random() * n);
            if (!indexs.contains(rnd)) {
                indexs.add(rnd);
                i++;
            }
        }
        for (int index : indexs) {
            index_item_default.setBackgroundResource(mArray[index]);
        }
        setClickable(true);
    }
    public void setImageView(int id){
        try {
            imageView.setImageResource(id);
        }catch (Exception e){
            PublicUtils.log("index_item:图片设置出错");
        }

    }
    public void setNumbertv(String str){
        if (str==null)str="";
        numbertv.setText(str);
        if (TextUtils.isEmpty(str)){
            setNumbertvVisibility(GONE);
            return;
        }
        try {
            int parseInt = Integer.parseInt(str);
            if (parseInt==0){
                setNumbertvVisibility(GONE);
            }else{
                setNumbertvVisibility(VISIBLE);
            }
        }catch (Exception e){
            setNumbertvVisibility(GONE);
        }

    }
    public void setNumbertvVisibility(int str){
        numbertv.setVisibility(str);
    }
    public void setNametv(String str){
        if (str==null)str="";
        nametv.setText(str);
    }

    public void setJdtv(String str) {
        if (str==null)str="";
        jdtv.setText(str);
    }
}
