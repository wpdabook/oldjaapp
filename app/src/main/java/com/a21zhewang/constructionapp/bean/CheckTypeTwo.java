package com.a21zhewang.constructionapp.bean;


/**
 * Created by zhewang_ljw on 2017/9/6.
 *
 * @auto
 */

public class CheckTypeTwo extends CheckType<CheckTypeThree> {
    /**
     * Get the level of this item. The level start from 0.
     * If you don't care about the level, just return a negative.
     */
    @Override
    public int getLevel() {
        return 1;
    }

    @Override
    public int getItemType() {
        return 1;
    }
}
