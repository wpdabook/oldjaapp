package com.a21zhewang.constructionapp.base;

import com.a21zhewang.constructionapp.bean.CheckType;
import com.a21zhewang.constructionapp.bean.CheckTypeThree;

import java.util.List;

/**
 * Created by zhewang_ljw on 2017/9/6.
 *
 * @auto
 */

public class CheckItemsBean {
    public List<CheckType> getCheckItemList() {
        return checkItemList;
    }

    public void setCheckItemList(List<CheckType> checkItemList) {
        this.checkItemList = checkItemList;
    }

    private List<CheckType> checkItemList ;
}
