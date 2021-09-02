package com.a21zhewang.constructionapp.bean;

/**
 * Created by zhewang_ljw on 2017/4/27.
 * 控件命名规范：当前界面名+控件名+名字
 * 此文件作用：
 */

public class SgrzmsBean {
    /**
     * createTime : 2017-04-24 12:00:00
     * createUserID : lisi
     * floor : 15
     * number : 30
     * projectAreaName : 顶模
     * recordDesc : 按计划完成
     * recordID : Daily001
     * subProjectID : SubPj10001
     * subProjectName : 核心筒钢骨架安装
     */

    private String createTime;
    private String createUserID;
    private String floor;
    private String number;
    private String projectAreaName;
    private String recordDesc;
    private String recordID;
    private String subProjectID;
    private String subProjectName;
    private String elevation;
    private String projectType;

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getElevation() {
        return elevation;
    }

    public void setElevation(String elevation) {
        this.elevation = elevation;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateUserID() {
        return createUserID;
    }

    public void setCreateUserID(String createUserID) {
        this.createUserID = createUserID;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getProjectAreaName() {
        return projectAreaName;
    }

    public void setProjectAreaName(String projectAreaName) {
        this.projectAreaName = projectAreaName;
    }

    public String getRecordDesc() {
        return recordDesc;
    }

    public void setRecordDesc(String recordDesc) {
        this.recordDesc = recordDesc;
    }

    public String getRecordID() {
        return recordID;
    }

    public void setRecordID(String recordID) {
        this.recordID = recordID;
    }

    public String getSubProjectID() {
        return subProjectID;
    }

    public void setSubProjectID(String subProjectID) {
        this.subProjectID = subProjectID;
    }

    public String getSubProjectName() {
        return subProjectName;
    }

    public void setSubProjectName(String subProjectName) {
        this.subProjectName = subProjectName;
    }
}
