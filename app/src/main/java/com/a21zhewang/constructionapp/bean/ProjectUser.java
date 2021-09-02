package com.a21zhewang.constructionapp.bean;

/**
 * Created by wp-pc on 2020/4/5.
 */

public class ProjectUser {

    private String projectName;
    private int  managerUserCount;
    private int  labourUserCount;
    private int  noUserCount;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public int getManagerUserCount() {
        return managerUserCount;
    }

    public void setManagerUserCount(int managerUserCount) {
        this.managerUserCount = managerUserCount;
    }

    public int getLabourUserCount() {
        return labourUserCount;
    }

    public void setLabourUserCount(int labourUserCount) {
        this.labourUserCount = labourUserCount;
    }

    public int getNoUserCount() {
        return noUserCount;
    }

    public void setNoUserCount(int noUserCount) {
        this.noUserCount = noUserCount;
    }
}
