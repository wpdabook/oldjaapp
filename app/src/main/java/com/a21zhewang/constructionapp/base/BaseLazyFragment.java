package com.a21zhewang.constructionapp.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by WP-PC on 2020/4/22.
 */

public abstract class BaseLazyFragment extends Fragment {

    //Fragment的View加载完毕的标记
    private boolean isViewCreated;
    //Fragment对用户可见的标记
    private boolean isUIVisible;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = initView(inflater, container);//让子类实现初始化视图
        initListener();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isViewCreated = true;
        lazyLoad();
    }
    private void lazyLoad() {
        //这里进行双重标记判断,是因为setUserVisibleHint会多次回调,并且会在onCreateView执行前回调,必须确保onCreateView加载完毕且页面可见,才加载数据
        if (isViewCreated && isUIVisible) {
            loadData();
            //数据加载完毕,恢复标记,防止重复加载
            isViewCreated = false;
            isUIVisible = false;
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //isVisibleToUser这个boolean值表示:该Fragment的UI 用户是否可见
        if (isVisibleToUser) {
            isUIVisible = true;
            lazyLoad();
        } else {
            isUIVisible = false;
        }
    }

    public  void clearspValues(){
        PublicUtils.putspstring("tab_tag_one","");
        PublicUtils.putspstring("tab_tag_two","");
        PublicUtils.putspstring("tab_tag_third","");
        PublicUtils.putspstring("tab_tag_four","");
        PublicUtils.putspstring("tab_tag_five","");
        PublicUtils.putspstring("tab_tag_six","");
    }
    //两整数相除得出百分比（保留两位小数）
    public int getPercentage(int a , int b){

        if(b != 0){
            double d = new BigDecimal((float)a/b).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            int s = Integer.parseInt(new DecimalFormat("0").format(d*100));//百分比没有小数点
            return s;
        }
        return 0;
    }
    public void  BaseRateValues(TextView textView, Double b){
        if(b.intValue() == 0){
            textView.setText("0%");
        }else {
            DecimalFormat df = new DecimalFormat("######0.00");
            textView.setText(df.format(b)+"%");
        }
    }
    public void SetTextIsNoEmpty(TextView textView,String str){
        if(!TextUtils.isEmpty(str)){
            textView.setText(str);
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //页面销毁,恢复标记
        isViewCreated = false;
        isUIVisible = false;
    }

    //初始化视图接口，子类必须实现
    public abstract View initView(LayoutInflater inflater, @Nullable ViewGroup container);

    //数据加载接口，留给子类实现
    protected abstract void initListener();

    //数据加载接口，留给子类实现
    protected abstract void loadData();
}
