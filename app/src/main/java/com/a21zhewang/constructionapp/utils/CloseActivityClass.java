package com.a21zhewang.constructionapp.utils;

import android.app.Activity;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhewang on 2016/10/12.
 */

public class CloseActivityClass {
    public static List<Activity> activityList = new ArrayList<Activity>();

    public static void exitClient()
    {
        // 关闭所有Activity
        for (int i = 0; i < activityList.size(); i++)
        {
            if (null != activityList.get(i))
            {
                activityList.get(i).finish();
            }
        }


    }
}
