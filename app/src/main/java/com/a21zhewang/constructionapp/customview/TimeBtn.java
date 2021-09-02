package com.a21zhewang.constructionapp.customview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.a21zhewang.constructionapp.R;
import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.listener.CustomListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by zhewang_ljw on 2017/6/14.
 * 控件命名规范：当前界面名+控件名+名字
 * 此文件作用：
 */

@SuppressLint("AppCompatCustomView")
public class TimeBtn extends TextView{

    private TimePickerView pvCustomTime;
    private Context mContext;
    private TimeBtnOkAndClear timeBtnOkAndClear;
    private TextView tvClear;
    private TextView tvSubmit;
    private ImageView ivCancel;

    public TimePickerView getPvCustomTime() {
        return pvCustomTime;
    }

    public void setTimeBtnOkAndClear(TimeBtnOkAndClear timeBtnOkAndClear) {
        this.timeBtnOkAndClear = timeBtnOkAndClear;
    }


    public TimeBtn(Context context) {
        this(context,null);
    }
    public  interface  TimeBtnOkAndClear{
        void ok(String time);
        void clear(String time);
    }

    public TimeBtn(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    public TimeBtn(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }
    private void init(){
        setClickable(true);
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
    }
    private void initCustomTimePicker() {

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
//      //  startDate.set(2014, 1, 23);
       Calendar endDate = Calendar.getInstance();
       endDate.set(2100, 2, 28);
        //时间选择器 ，自定义布局
        //选中事件回调
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
                if (timeBtnOkAndClear!=null){
                    timeBtnOkAndClear.ok(getTime(date));
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
                .setRangDate(selectedDate, endDate)
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
                .setType(new boolean[]{true, true, true, true, true, false})
                .setLabel("", "", "", "时", "分", "秒")
                .isCenterLabel(true) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(Color.BLUE)
                .build();
       // pvCustomTime.set

    }
    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
