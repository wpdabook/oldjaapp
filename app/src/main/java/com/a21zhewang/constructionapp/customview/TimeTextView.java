package com.a21zhewang.constructionapp.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.utils.DensityUtil;
import com.a21zhewang.constructionapp.utils.TimeUtils;
import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.listener.CustomListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by zhewang_ljw on 2017/6/15.
 * 控件命名规范：当前界面名+控件名+名字
 * 此文件作用：
 */

public class TimeTextView extends TextView {
    private TimePickerView pvCustomTime;
    private Context mContext;
    private TimeBtn.TimeBtnOkAndClear timeBtnOkAndClear;
    private TextView tvClear;
    private TextView tvSubmit;
    private ImageView ivCancel;
    private  boolean[] booleans = {true, true, true, false, false, false};
    private String startTime;
    private boolean isTimeSolt;
    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    private String dateFormat="yyyy-MM-dd HH:mm";
    public void setTimeBtnOkAndClear(TimeBtn.TimeBtnOkAndClear timeBtnOkAndClear) {
        this.timeBtnOkAndClear = timeBtnOkAndClear;
    }

    public TextView getTvClear() {
        return tvClear;
    }

    public void setTvClear(TextView tvClear) {
        this.tvClear = tvClear;
    }

    public TextView getTvSubmit() {
        return tvSubmit;
    }

    public void setTvSubmit(TextView tvSubmit) {
        this.tvSubmit = tvSubmit;
    }

    public ImageView getIvCancel() {
        return ivCancel;
    }

    public void setIvCancel(ImageView ivCancel) {
        this.ivCancel = ivCancel;
    }

    public TimeTextView(Context context) {
        this(context,null);
    }
    public  interface  TimeBtnOkAndClear{
        void ok(String time);
        void clear(String time);
    }

    public TimeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.TimeTextView);
        for (int i = 0; i < typedArray.length(); i++) {
            switch (typedArray.getIndex(i)) {
                case R.styleable.TimeTextView_dateFormat:
                    String format = typedArray.getString(R.styleable.TimeTextView_dateFormat);
                    if (!TextUtils.isEmpty(format)){
                        dateFormat=format;
                    }
                    break;
                case R.styleable.TimeTextView_showTime:
                    boolean aBoolean = typedArray.getBoolean(R.styleable.TimeTextView_showTime, true);
                    booleans[3]=aBoolean;
                    booleans[4]=aBoolean;
                    break;
                case R.styleable.TimeTextView_startTime:
                    startTime = typedArray.getString(R.styleable.TimeTextView_startTime);
                case R.styleable.TimeTextView_isTimeslot:
                    isTimeSolt = typedArray.getBoolean(R.styleable.TimeTextView_isTimeslot, false);

                    break;
            }
        }
        typedArray.recycle();
        init();
    }

    public TimeTextView(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }
    private void init(){


        setClickable(true);

//        android:textColor="#333333"
//        android:gravity="center_vertical|left"

        setTextColor(Color.parseColor("#333333"));
        setGravity(Gravity.CENTER);
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        initCustomTimePicker();
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (pvCustomTime.isShowing()){
                    pvCustomTime.dismiss();
                }else{
                    pvCustomTime.show();

                }

            }
        });

        setTvClearVisiable(isTimeSolt);
    }
    public  void setStartTime(){
       // pvCustomTime.set
    }
    private void initCustomTimePicker(){

        this.initCustomTimePicker(booleans);
    }
    private void initCustomTimePicker(boolean[] booleans) {

        /**
         * @description
         *
         * 注意事项：
         * 1.自定义布局中，id为 optionspicker 或者 timepicker 的布局以及其子控件必须要有，否则会报空指针.
         * 具体可参考demo 里面的两个自定义layout布局。
         * 2.因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
         * setRangDate方法控制起始终止时间(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
         */
        Calendar selectedDate = Calendar.getInstance();//系统当前时间

        Calendar startDate = Calendar.getInstance();
        //startDate.set(2014, 1, 23);
        if (!TextUtils.isEmpty(startTime)){
            Date date = TimeUtils.string2Date(startTime);
            startDate.setTime(date);
        }
        Calendar endDate = Calendar.getInstance();
        endDate.set(2100, 12, 1);
        //时间选择器 ，自定义布局
        //选中事件回调
        setText(getTime(selectedDate.getTime()));
/*.setType(TimePickerView.Type.ALL)//default is all
.setCancelText("Cancel")
.setSubmitText("Sure")
.setContentSize(18)
.setTitleSize(20)
.setTitleText("Title")
.setTitleColor(Color.BLACK)
/*.setDividerColor(Color.WHITE)//设置分割线的颜色
.setTextColorCenter(Color.LTGRAY)//设置选中项的颜色
.setLineSpacingMultiplier(1.6f)//设置两横线之间的间隔倍数
.setTitleBgColor(Color.DKGRAY)//标题背景颜色 Night mode
.setBgColor(Color.BLACK)//滚轮背景颜色 Night mode
.setSubmitColor(Color.WHITE)
.setCancelColor(Color.WHITE)*//*.gravity(Gravity.RIGHT)// default is center*///是否只显示中间选中项的label文字，false则每项item全部都带有label。
        pvCustomTime = new TimePickerView.Builder(mContext, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                String time = getTime(date);

                    setText(time);



                if (timeBtnOkAndClear!=null){
                    timeBtnOkAndClear.ok(time);
                }
            }
        })
                /*.setType(TimePickerView.Type.ALL)//default is all
                .setCancelText("Cancel")
                .setSubmitText("Sure")
                .setContentSize(18)
                .setTitleSize(20)
                .setTitleText("Title")
                .setTitleColor(Color.BLACK)
               /*.setDividerColor(Color.WHITE)//设置分割线的颜色
                .setTextColorCenter(Color.LTGRAY)//设置选中项的颜色
                .setLineSpacingMultiplier(1.6f)//设置两横线之间的间隔倍数
                .setTitleBgColor(Color.DKGRAY)//标题背景颜色 Night mode
                .setBgColor(Color.BLACK)//滚轮背景颜色 Night mode
                .setSubmitColor(Color.WHITE)
                .setCancelColor(Color.WHITE)*/
               /*.gravity(Gravity.RIGHT)// default is center*/
                .setDate(selectedDate)
                 .setRangDate(startDate, endDate)

                .setLayoutRes(R.layout.pickerview_custom_time, new CustomListener() {

                    @Override
                    public void customLayout(View v) {
                        tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        tvClear = (TextView) v.findViewById(R.id.tv_clear);
                        ivCancel = (ImageView) v.findViewById(R.id.iv_cancel);
                        tvSubmit.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomTime.returnData();
                                pvCustomTime.dismiss();
                            }
                        });
                        tvClear.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (timeBtnOkAndClear!=null){
                                    timeBtnOkAndClear.clear(null);
                                }
                                pvCustomTime.dismiss();
                            }
                        });
                        ivCancel.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomTime.dismiss();
                            }
                        });

                    }
                })
                .setType(booleans)
                .setLabel("年", "月", "日", "时", "分", "秒")
                .isCenterLabel(true) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(Color.BLUE)
                .build();

    }
    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        return format.format(date);
    }
    public void setTvClearVisiable(Boolean visiable){
        if (tvClear!=null){
            if (visiable){
                tvClear.setVisibility(VISIBLE);
            }else{
                tvClear.setVisibility(INVISIBLE);

            }
        }

    }
}
