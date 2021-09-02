package com.a21zhewang.constructionapp.ui.tab;

import android.app.ActivityGroup;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

public class Act_BaseTabImpl extends ActivityGroup {

    private ViewGroup container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    /**
     * 通过id设置Activity显示的container，该container必须是继承ViewGroup的
     */
    protected void setContainer(int resId) {
        container = (ViewGroup) findViewById(resId);
    }

    /**
     * 通过Activity的class显示Activity
     */
    protected void showActivity(Class<?> activityClass) {

        Intent intent = new Intent(this, activityClass);
        // 检查container是否有显示的Activity。假设有，先移除
        View activity = container.getChildAt(0);
        if (activity != null) {
            // 移除显示的activity的View
            container.removeAllViews();
            // 通过ActivityManager移除activity
            getLocalActivityManager().removeAllActivities();
        }
        // 启动新的activity。并将该activity的根视图加入到contanier中
        container.addView(getLocalActivityManager().startActivity(activityClass.getName(), intent).getDecorView());

    }
}
