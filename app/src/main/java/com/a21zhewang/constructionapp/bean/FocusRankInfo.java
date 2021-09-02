package com.a21zhewang.constructionapp.bean;

/**
 * Created by WP-PC on 2019/8/14.
 */

public class FocusRankInfo {
    private String projectName;
    private double rate;
    private int sunCount;
    private String projectID;
    private String proShortName;
    private int momRate;
    private int count;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public int getSunCount() {
        return sunCount;
    }

    public void setSunCount(int sunCount) {
        this.sunCount = sunCount;
    }

    public String getProjectID() {
        return projectID;
    }

    public void setProjectID(String projectID) {
        this.projectID = projectID;
    }

    public String getProShortName() {
        return proShortName;
    }

    public void setProShortName(String proShortName) {
        this.proShortName = proShortName;
    }

    public int getMomRate() {
        return momRate;
    }

    public void setMomRate(int momRate) {
        this.momRate = momRate;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
