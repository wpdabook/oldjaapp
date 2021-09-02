package com.a21zhewang.constructionapp.ui.wmsg;

import android.app.Activity;
import android.os.Bundle;

import com.a21zhewang.constructionapp.base.BasexqActivity;
import com.a21zhewang.constructionapp.ui.zljd.ZljdxqActivity;
import com.a21zhewang.constructionapp.utils.AppUtils;

public class WmsgxqActivity extends BasexqActivity {


    /**
     * @return 设置标题文字
     */
    @Override
    public String settitlebar() {
        return "文明施工";
    }

    /**
     * @return 请求方法
     */
    @Override
    public String getdatamethod() {
        if("com.a21zhewang.constructionapp.qt".equals(AppUtils.getPackageName(WmsgxqActivity.this))){
            return "GetCivilizedMsgDetails_QinTai";
        }else {
            return "GetCivilizedMsgDetails";
        }
    }

    /**
     * @return postdata方法
     */
    @Override
    public String postdatamethod() {
        return "GetCivilizedMsgDetailsButton";
    }
}
