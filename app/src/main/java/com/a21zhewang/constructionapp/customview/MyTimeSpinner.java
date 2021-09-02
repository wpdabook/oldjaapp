package com.a21zhewang.constructionapp.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.a21zhewang.constructionapp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * Created by zhewang on 2016/11/4.
 */

public class MyTimeSpinner extends LinearLayout {
    RelativeLayout relativeLayout;
    TextView textView;
    Context thiscContext;
    PopupWindow timespop;
    DatePicker mDatePicker;
    TextView backtv, isoktv;

    public void setMyTimeSpinnerinterface(MyTimeSpinnerinterface myTimeSpinnerinterface) {
        this.myTimeSpinnerinterface = myTimeSpinnerinterface;
    }

    MyTimeSpinnerinterface myTimeSpinnerinterface;

    public interface MyTimeSpinnerinterface {
        void setokListener();

        void setbackListener();
    }

    public MyTimeSpinner(Context context) {
        super(context);
    }

    public MyTimeSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MytimeSpinner);
        for (int i = 0; i < typedArray.length(); i++) {
            int viewid = typedArray.getIndex(i);

            if (viewid == R.styleable.MytimeSpinner_timetextsize) {
                float size = typedArray.getDimension(R.styleable.MytimeSpinner_timetextsize, 5);
                textView.setTextSize(size);
            } else if (viewid == R.styleable.MytimeSpinner_timetext) {

                String text = typedArray.getString(R.styleable.MytimeSpinner_timetext);
                textView.setText(text);

            }

        }
        typedArray.recycle();
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                timespop.showAsDropDown(v);
//                timespop.showAtLocation(getRootView(), Gravity.CENTER,0,0);
            }
        });
    }

    public void setshowcenter() {
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                timespop.showAtLocation(v.getRootView(), Gravity.CENTER, 0, 0);
            }
        });
    }

    public void init(Context context) {
        thiscContext = context;
        View.inflate(context, R.layout.mytimespinner, this);
        relativeLayout = (RelativeLayout) this.findViewById(R.id.mytimespinner_ll);
        textView = (TextView) this.findViewById(R.id.mytimespinner_text);
        View timeview = LayoutInflater.from(context).inflate(R.layout.tims_item, null);
        mDatePicker = (DatePicker) timeview.findViewById(R.id.times_item_datepicker);
        mDatePicker.setFocusable(true);
        backtv = (TextView) timeview.findViewById(R.id.times_item_back);
        backtv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                timespop.dismiss();
            }
        });
        isoktv = (TextView) timeview.findViewById(R.id.times_item_isok);
        timespop = new PopupWindow(timeview, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        timespop.setFocusable(true);
        timespop.setOutsideTouchable(true);
        timespop.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));
        timespop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

            }
        });
        setokonclick(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myTimeSpinnerinterface != null)
                    myTimeSpinnerinterface.setokListener();

                settimetext(gettime());
                timespop.dismiss();
            }
        });
        setbackonclick(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myTimeSpinnerinterface != null)
                    myTimeSpinnerinterface.setbackListener();
                timespop.dismiss();
            }
        });
        settimetext(gettime());
    }

    public void setbackground(Drawable d) {
        relativeLayout.setBackground(d);
    }

    public void setbackonclick(OnClickListener onClickListener) {
        backtv.setOnClickListener(onClickListener);
    }

    public void setokonclick(OnClickListener onClickListener) {
        isoktv.setOnClickListener(onClickListener);
    }

    public String gettime() {
//        Log.i("shijian",mDatePicker.getYear()+"-"+(mDatePicker.getMonth()+1)+"-"+mDatePicker.getDayOfMonth());
        return mDatePicker.getYear() + "-" + (mDatePicker.getMonth() + 1) + "-" + mDatePicker.getDayOfMonth();
    }

    public String gettime1() {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss ");
        String ss = df.format(new Date(System.currentTimeMillis()));
//        Log.i("shijian",mDatePicker.getYear()+"-"+(mDatePicker.getMonth()+1)+"-"+mDatePicker.getDayOfMonth()+" "+ss);
        return gettimetext();
    }

    //    public String gettime2(){
//        Calendar CD = Calendar.getInstance();
//        int YY = CD.get(Calendar.YEAR) ;
//        int MM = CD.get(Calendar.MONTH)+1;
//        int DD = CD.get(Calendar.DATE);
//        int HH = CD.get(Calendar.HOUR);
//        int NN = CD.get(Calendar.MINUTE);
//        int SS = CD.get(Calendar.SECOND);
//        int MI = CD.get(Calendar.MILLISECOND);
//        return YY+""+MM+DD+HH+NN+SS+MI;
//    }
    public void settimetext(String text) {
        textView.setText(text);

    }

    public String gettimetext() {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        String ss = df.format(new Date(System.currentTimeMillis()));
        return textView.getText() + " " + ss;
    }

    public void setdisspop() {
        timespop.dismiss();
    }

}
