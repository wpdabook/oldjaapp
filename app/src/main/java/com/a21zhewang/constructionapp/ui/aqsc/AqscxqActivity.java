package com.a21zhewang.constructionapp.ui.aqsc;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.a21zhewang.constructionapp.base.BasexqActivity;
import com.a21zhewang.constructionapp.utils.AppUtils;

public class AqscxqActivity extends BasexqActivity {


    /**
     * @return 设置标题文字
     */
    @Override
    public String settitlebar() {
        return "安全检查";
    }



    /**
     * @return 请求方法
     */
    @Override
    public String getdatamethod() {
        return "GetSafetyMsgDetails";
    }

    /**
     * @return postdata方法
     */
    @Override
    public String postdatamethod() {
        return "GetSafetyMsgDetailsButton";
    }




}
