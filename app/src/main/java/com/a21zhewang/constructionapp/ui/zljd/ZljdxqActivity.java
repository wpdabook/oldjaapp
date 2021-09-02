package com.a21zhewang.constructionapp.ui.zljd;

import android.app.Activity;
import android.os.Bundle;

import com.a21zhewang.constructionapp.base.BasexqActivity;
import com.a21zhewang.constructionapp.utils.AppUtils;

public class ZljdxqActivity extends BasexqActivity {


    /**
     * @return 设置标题文字
     */
    @Override
    public String settitlebar() {
        return "质量检查";
    }

    /**
     * @return 请求方法
     */
    @Override
    public String getdatamethod() {
        if("com.a21zhewang.constructionapp.qt".equals(AppUtils.getPackageName(ZljdxqActivity.this))){
            return "GetQualityMsgDetails_QinTai";
        }else {
            return "GetQualityMsgDetails";
        }
    }

    /**
     * @return postdata方法
     */
    @Override
    public String postdatamethod() {
        return "GetQualityMsgDetailsButton";
    }


}
