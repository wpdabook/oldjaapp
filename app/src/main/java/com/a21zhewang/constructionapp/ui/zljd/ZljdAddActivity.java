package com.a21zhewang.constructionapp.ui.zljd;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.a21zhewang.constructionapp.base.BaseAddActivity;
import com.a21zhewang.constructionapp.ui.aqsc.AqscAddActivity;
import com.a21zhewang.constructionapp.utils.AppUtils;

public class ZljdAddActivity extends BaseAddActivity {


    /**
     * @return 设置标题栏文字
     */
    @Override
    public String setActivityBaseAddMyTitleBar() {
        return "质量检查问题整改";
    }

    /**
     * @return 设置请求方法
     */
    @Override
    public String setgetdatamethod() {
        if("com.a21zhewang.constructionapp.qt".equals(AppUtils.getPackageName(ZljdAddActivity.this))){
            return "GetQualityMsgInitialize_QinTai";
        }else {
            return "GetQualityMsgInitialize";
        }
    }

    /**
     * @return 设置风险类别请求方法
     */
    @Override
    public String setgetinitdatamethod() {
        return "GetQualityMsgType";
    }

    /**
     * 初始化界面
     */
    @Override
    public void init() {
//getActivityBaseAddMySpinnerJcxm3().setVisibility(View.GONE);
    }

    /**
     * @return 设置postdata请求方法
     */
    @Override
    protected String setpostmethod() {
        return "AddQualityMsg";
    }

    /**
     * 设置风险等级取自哪个下拉 true 取第三级 false 第二级
     */
    @Override
    public boolean setfxdjwhat() {
        return false;
    }
}
