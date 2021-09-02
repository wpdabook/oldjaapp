package com.a21zhewang.constructionapp.bean;

import java.util.List;

/**
 * Created by zhewang_ljw on 2017/10/9.
 *
 * @auto
 */

public class Project2CheckItem {
    private String projectID;
    private List<CheckTypeOne> checkItemList;

    public String getProjectID() {
        return projectID;
    }

    public void setProjectID(String projectID) {
        this.projectID = projectID;
    }

    public List<CheckTypeOne> getCheckItemList() {
        return checkItemList;
    }

    public void setCheckItemList(List<CheckTypeOne> checkItemList) {
        this.checkItemList = checkItemList;
    }
}
