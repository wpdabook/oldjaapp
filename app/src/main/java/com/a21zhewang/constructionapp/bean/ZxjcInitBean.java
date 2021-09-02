package com.a21zhewang.constructionapp.bean;

import java.util.List;

/**
 * Created by zhewang_ljw on 2017/9/5.
 *
 * @auto
 */

public class ZxjcInitBean {

    private List<Project> projectList;
    private List<Project2CheckItem> checkTableList;

    public ZxjcInitBean() {
    }

    public List<Project> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<Project> projectList) {
        this.projectList = projectList;
    }

    public List<Project2CheckItem> getCheckTableList() {
        return checkTableList;
    }

    public void setCheckTableList(List<Project2CheckItem> checkTableList) {
        this.checkTableList = checkTableList;
    }
}
