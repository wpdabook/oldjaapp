package com.a21zhewang.constructionapp.bean;

/**
 * Created by zhewang_ljw on 2017/5/23.
 * 控件命名规范：当前界面名+控件名+名字
 * 此文件作用：
 */

public class TzgglistBean {

    /**
     * recordID : 258979df-6a15-4c3e-8fe6-135e4078cc9c
     * projectID : GC10001
     * typeName : 通知公告
     * title : 下周检查
     * content : 下周检查，做好准备
     * recEtpID : QY201704191154
     * recEtpShortName : 土建一部
     * createUserID : zhangsan
     * createUserName : 张三
     * createTime : 2017-05-22 17:54:39
     * etpID : QY201704191154
     * etpShortName : 土建一部
     */

    private String recordID;
    private String projectID;
    private String typeName;
    private String title;
    private String content;
    private String recEtpID;
    private String recEtpShortName;
    private String createUserID;
    private String createUserName;
    private String createTime;
    private String etpID;
    private String etpShortName;
    private Boolean isRead;


    public Boolean getRead() {
        return isRead;
    }

    public void setRead(Boolean read) {
        isRead = read;
    }
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

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
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

    public String getRecEtpID() {
        return recEtpID;
    }

    public void setRecEtpID(String recEtpID) {
        this.recEtpID = recEtpID;
    }

    public String getRecEtpShortName() {
        return recEtpShortName;
    }

    public void setRecEtpShortName(String recEtpShortName) {
        this.recEtpShortName = recEtpShortName;
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
}
