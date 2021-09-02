package com.a21zhewang.constructionapp.bean;

/**
 * Created by wp-pc on 2020/4/5.
 */

public class Dange {
    private String projectName;
    private int dangerCount;
    private int normalCount;
    private int rectCount;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public int getDangerCount() {
        return dangerCount;
    }

    public void setDangerCount(int dangerCount) {
        this.dangerCount = dangerCount;
    }

    public int getNormalCount() {
        return normalCount;
    }

    public void setNormalCount(int normalCount) {
        this.normalCount = normalCount;
    }

    public int getRectCount() {
        return rectCount;
    }

    public void setRectCount(int rectCount) {
        this.rectCount = rectCount;
    }
}
