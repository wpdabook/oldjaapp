package com.a21zhewang.constructionapp.ui.wmsg;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.a21zhewang.constructionapp.base.BaseAddActivity;
import com.a21zhewang.constructionapp.ui.zljd.ZljdxqActivity;
import com.a21zhewang.constructionapp.utils.AppUtils;

public class WmsgAddActivity extends BaseAddActivity {



    /**
     * @return 设置标题栏文字
     */
    @Override
    public String setActivityBaseAddMyTitleBar() {
        return "文明施工问题整改";
    }

    /**
     * @return 设置请求方法
     */
    @Override
    public String setgetdatamethod() {
        if("com.a21zhewang.constructionapp.qt".equals(AppUtils.getPackageName(WmsgAddActivity.this))){
            return "GetCivilizedMsgInitialize_QinTai";
        }else {
            return "GetCivilizedMsgInitialize";
        }
    }

    /**
     * @return 设置风险类别请求方法
     */
    @Override
    public String setgetinitdatamethod() {
        return "GetCivilizedMsgType";
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
        return "AddCivilizedMsg";
    }

    /**
     * 设置风险等级取自哪个下拉 true 取第三级 false 第二级
     */
    @Override
    public boolean setfxdjwhat() {
        return false;
    }
}
