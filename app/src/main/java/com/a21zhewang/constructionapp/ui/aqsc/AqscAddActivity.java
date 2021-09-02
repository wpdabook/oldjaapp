package com.a21zhewang.constructionapp.ui.aqsc;

import android.view.View;

import com.a21zhewang.constructionapp.base.BaseAddActivity;
import com.a21zhewang.constructionapp.utils.AppUtils;

/**
 * Created by zhewang_ljw on 2017/5/16.
 * 控件命名规范：当前界面名+控件名+名字
 * 此文件作用：
 */

public class AqscAddActivity extends BaseAddActivity{

    @Override
    public String setActivityBaseAddMyTitleBar() {
        return "安全检查问题整改";
    }

    /**
     * @return 设置请求方法
     */
    @Override
    public String setgetdatamethod() {
        return "GetSafetyMsgInitialize";
    }

    /**
     * @return 设置风险类别请求方法
     */
    @Override
    public String setgetinitdatamethod() {
        return "GetSafetyMsgType";
    }

    /**
     * 初始化界面
     */
    @Override
    public void init() {
//        getActivityBaseAddMySpinnerJcxm3().setVisibility(View.GONE);
    }

    /**
     * @return 设置postdata请求方法
     */
    @Override
    protected String setpostmethod() {
        return "AddSafetyMsg";
    }

    /**
     * 设置风险等级取自哪个下拉 true 取第三级 false 第二级
     */
    @Override
    public boolean setfxdjwhat() {
        return true;
    }
}
