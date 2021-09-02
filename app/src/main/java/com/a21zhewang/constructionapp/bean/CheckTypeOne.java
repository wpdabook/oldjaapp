package com.a21zhewang.constructionapp.bean;


import com.a21zhewang.constructionapp.ui.zxjc.AddzxjcActivity;

import java.util.List;

/**
 * Created by zhewang_ljw on 2017/9/6.
 *
 * @auto
 */

public class CheckTypeOne extends CheckType<CheckTypeTwo> {


    /**
     * Get the level of this item. The level start from 0.
     * If you don't care about the level, just return a negative.
     */
    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public int getItemType() {
        return 0;
    }


}
