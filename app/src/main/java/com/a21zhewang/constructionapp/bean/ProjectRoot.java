package com.a21zhewang.constructionapp.bean;

import java.util.List;

/**
 * Created by zhewang_ljw on 2017/9/18.
 *
 * @auto
 */

public class ProjectRoot {
    private List<Project> projectList;

    public void setProjectList(List<Project> projectList){
        this.projectList = projectList;
    }
    public List<Project> getProjectList(){
        return this.projectList;
    }
}
