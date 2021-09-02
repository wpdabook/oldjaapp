package com.a21zhewang.constructionapp.ui.sgtx;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.ui.tzgg.TzggAndSgtxActivity;
import com.angel.interfaces.OnTouchUpListener;
import com.angel.layout.SWPullRecyclerLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SgtxActivity extends TzggAndSgtxActivity{


    /**
     * @return 设置请求的的页面
     */
    @Override
    public String setmethod() {
        return "notice1";
    }

    /**
     * @return 设置请求数据的方法
     */
    @Override
    public String setgetdatamethod() {
        return "GetListNoticeMsg";
    }

    @Override
    public String settitle() {
        return "施工提醒";
    }

    @Override
    public String setaddimgisvisiable() {
        return "Notice1AddBtn";
    }
}
