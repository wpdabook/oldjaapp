package com.a21zhewang.constructionapp.bean;

import java.util.List;

/**
 * Created by zhewang_ljw on 2017/9/7.
 *
 * @auto
 */

public class CheckBean <T extends CheckBean>{
    String dicID;
    String dicName;
    List<T> checkItemList;
    String level;
}
