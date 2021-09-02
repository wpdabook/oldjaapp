package com.a21zhewang.constructionapp.bean;

import java.util.List;

/**
 * Created by WP-PC on 2019/8/14.
 */

public class Focus {

    /*饼图数据*/
    private int civilizedCount;
    private int userCount;
    private int supervisorCount;


    /*风险排查*/
    private int level;
    private int count;

    /*安全风险-等级*/
    private int projectCount;
    private int qualityCount;
    private int safetyCount;


    public int getCivilizedCount() {
        return civilizedCount;
    }

    public void setCivilizedCount(int civilizedCount) {
        this.civilizedCount = civilizedCount;
    }

    public int getQualityCount() {
        return qualityCount;
    }

    public void setQualityCount(int qualityCount) {
        this.qualityCount = qualityCount;
    }

    public int getSafetyCount() {
        return safetyCount;
    }

    public void setSafetyCount(int safetyCount) {
        this.safetyCount = safetyCount;
    }

    public int getUserCount() {
        return userCount;
    }

    public void setUserCount(int userCount) {
        this.userCount = userCount;
    }

    public int getSupervisorCount() {
        return supervisorCount;
    }

    public void setSupervisorCount(int supervisorCount) {
        this.supervisorCount = supervisorCount;
    }

    public int getProjectCount() {
        return projectCount;
    }

    public void setProjectCount(int projectCount) {
        this.projectCount = projectCount;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
