package com.a21zhewang.constructionapp.customview;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.a21zhewang.constructionapp.R;


/**
 * 描述：文件下载dialog 样式
 */
public class CustomDownDialog extends Dialog {


    public CustomDownDialog(Context context) {
        super(context, R.style.loadindDialog);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_download_dilog);
        initView();
    }
    //更新进度时使用
    private LinearLayout ll_down_load_top;
//    private ProgressBar progressBar;//进度条
    private RoundProgressBarWidthNumber mRoundProgressBar;
    private HorizontalProgressBarWithNumber mProgressBar;
    private TextView tv_cancel;     //取消更新

    //提示信息时使用
    private LinearLayout ll_down_load_bot;
    private TextView tv_down_load_tip;
    private TextView tv_down_load_tip_content;
    private TextView tv_down_load_btn_top;
    private TextView tv_down_load_btn_bot;
    private View tv_down_load_line;

    @Override
    public boolean dispatchKeyShortcutEvent(KeyEvent event) {
        return super.dispatchKeyShortcutEvent(event);
    }

    //初始化加载框
//    @SuppressLint("WrongViewCast")
    private void initView(){

        //模式对话框
        setCancelable(false);
        //更新进度时使用
        ll_down_load_top = (LinearLayout) findViewById(R.id.ll_down_load_top);
//        progressBar = (ProgressBar) findViewById(R.id.update_progress);
        mRoundProgressBar = (RoundProgressBarWidthNumber) findViewById(R.id.update_progress0);
        mProgressBar  = (HorizontalProgressBarWithNumber) findViewById(R.id.update_progress);
        tv_cancel = (TextView) findViewById(R.id.tv_cancel);

        //提示信息时使用
        ll_down_load_bot = (LinearLayout) findViewById(R.id.ll_down_load_bot);
        tv_down_load_tip = (TextView) findViewById(R.id.tv_down_load_tip);
        tv_down_load_tip_content = (TextView) findViewById(R.id.tv_down_load_tip_content);
        tv_down_load_btn_top = (TextView) findViewById(R.id.tv_down_load_btn_top);
        tv_down_load_btn_bot = (TextView) findViewById(R.id.tv_down_load_btn_bot);
        tv_down_load_line = findViewById(R.id.tv_down_load_line);
    }

    /**
     * 清空布局内容
     * @return
     */
    public CustomDownDialog clearALl(){
        tv_down_load_tip.setText("");
        tv_down_load_tip_content.setText("");
        tv_down_load_btn_top.setText("");
        tv_down_load_btn_bot.setText("");
        return this;
    }


    /**
     * 实现更新布局还是提示布局
     *
     * @param isUpdate true:更新布局 显示
     *                 false:提示布局 显示
     */
    public CustomDownDialog setVisibale(boolean isUpdate){
        if(isUpdate){
            this.ll_down_load_top.setVisibility(View.VISIBLE);
            this.ll_down_load_bot.setVisibility(View.GONE);
        }else{
            this.ll_down_load_bot.setVisibility(View.VISIBLE);
            this.tv_down_load_line.setVisibility(View.VISIBLE);
            this.tv_down_load_btn_top.setVisibility(View.VISIBLE);
            this.ll_down_load_top.setVisibility(View.GONE);
        }
        return this;
    }

    /**
     * 强制更新时 隐藏 线和底部按钮
     * @return
     */
    public CustomDownDialog setHiddent(){
        this.tv_down_load_line.setVisibility(View.GONE);
        this.tv_down_load_btn_top.setVisibility(View.GONE);
        return this;
    }

    /////////////////////////////////////////// start 初始化提示信息

    /**
     * 最上面的提示
     * @param name
     */
    public CustomDownDialog setTextViewTip(CharSequence name){
        this.tv_down_load_tip.setText(name);
        return this;
    }
    /**
     * 提示内容
     * @param name
     */
    public CustomDownDialog setTextViewContnt(CharSequence name){
        this.tv_down_load_tip_content.setText(name);
        return this;
    }
    /**
     * 上面的按钮
     * @param name
     */
    public CustomDownDialog setTextViewTop(CharSequence name){
    	findViewById(R.id.ll_download_dialog_top_btn).setVisibility(View.VISIBLE);
        this.tv_down_load_btn_top.setText(name);
        return this;
    }
    /**
     *下面的按钮
     * @param name
     */
    public CustomDownDialog setTextViewBot(CharSequence name){
    	findViewById(R.id.ll_download_dialog_bot_btn).setVisibility(View.VISIBLE);
        this.tv_down_load_btn_bot.setText(name);
        return this;
    }

    /////////////////////////////////////////// end 初始化提示信息



    /**
     * 更新进度条
     *
     * @param current
     */
    public void setProgressBar(int current) {
//        progressBar.setProgress(current);
        mRoundProgressBar.setProgress(current);
        mProgressBar.setProgress(current);
    }

    /**
     * 注册点击事件 取消更新
     * @param click
     */
    public CustomDownDialog setClick(View.OnClickListener click){
        this.tv_cancel.setOnClickListener(click);
        return this;
    }

    /**
     * 中间按钮 点击事件
     * @param click
     */
    public CustomDownDialog setTopClick(View.OnClickListener click){
        this.tv_down_load_btn_top.setOnClickListener(click);
        return this;
    }

    /**
     * 底部按钮 点击事件
     * @param click
     */
    public CustomDownDialog setBotClick(View.OnClickListener click){
        this.tv_down_load_btn_bot.setOnClickListener(click);
        return this;
    }


}
