package com.a21zhewang.constructionapp.bean;

/**
 * Created by zhewang_ljw on 2017/6/13.
 * 控件命名规范：当前界面名+控件名+名字
 * 此文件作用：
 */

public class GxysListBean {
    /**
     * recordID : faf63b6a-e7bd-4ab4-aed4-5ee04bc2f18a
     * projectID : GC10001
     * projectAreaName : 顶模
     * floor : 1F
     * title : 关于顶模的SubPj10001的工序验收
     * content : 工序施工已经完成，申请工序验收
     * etpID : QY201704191139
     * etpShortName : 土建一部
     * createUserID : zhangsan
     * createUserName : 张三
     * createTime : 2017-06-13 11:52:56
     * status : 未读
     * isRead : false
     */

    private String recordID;
    private String projectID;
    private String projectAreaName;
    private String floor;
    private String title;
    private String content;
    private String etpID;
    private String etpShortName;
    private String createUserID;
    private String createUserName;
    private String createTime;
    private String status;
    private boolean isRead;

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

    private String subProjectID;
    private String subProjectName;
    public String getRecordID() {
        return recordID;
    }

    public void setRecordID(String recordID) {
        this.recordID = recordID;
    }

    public String getProjectID() {
        return projectID;
    }

    public void setProjectID(String projectID) {
        this.projectID = projectID;
    }

    public String getProjectAreaName() {
        return projectAreaName;
    }

    public void setProjectAreaName(String projectAreaName) {
        this.projectAreaName = projectAreaName;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEtpID() {
        return etpID;
    }

    public void setEtpID(String etpID) {
        this.etpID = etpID;
    }

    public String getEtpShortName() {
        return etpShortName;
    }

    public void setEtpShortName(String etpShortName) {
        this.etpShortName = etpShortName;
    }

    public String getCreateUserID() {
        return createUserID;
    }

    public void setCreateUserID(String createUserID) {
        this.createUserID = createUserID;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isIsRead() {
        return isRead;
    }

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }
}
