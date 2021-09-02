package com.a21zhewang.constructionapp.bean;

/**
 * Created by zhewang_ljw on 2017/5/15.
 * 控件命名规范：当前界面名+控件名+名字
 * 此文件作用：
 */

public class AqsclistBean {

    /**
     * recordID : 66183347-bc5d-4363-b372-90fc0571d810
     * projectID : GC10001
     * projectAreaName : 外框
     * floor : 3F
     * typeID : C01007001001
     * typeName : 电工带电操作
     * typeFatherID : C01007001
     * typeFatherName : 人的不安全行为
     * typeGraFaID : C01007
     * typeGraFaName : 触电
     * title :
     * recEtpID : QY201704191154
     * recEtpShortName : 土建一部
     * createUserID : lisi
     * createUserName : 李四
     * createUserType : 总包-生产经理
     * createUserTypeID : C01001003
     * createTime : 2017-05-12 16:48:14
     * status : 未读
     */

    private String recordID;
    private String projectID;
    private String projectAreaName;
    private String floor;
    private String typeID;
    private String typeName;
    private String typeFatherID;
    private String typeFatherName;
    private String typeGraFaID;
    private String typeGraFaName;
    private String title;
    private String recEtpID;
    private String recEtpShortName;
    private String createUserID;
    private String createUserName;
    private String createUserType;
    private String createUserTypeID;
    private String createTime;
    private String status;
    private String ApiUrl;
    private boolean isRead;

    public String getApiUrl() {
        return ApiUrl;
    }

    public void setApiUrl(String apiUrl) {
        ApiUrl = apiUrl;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
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

    public String getTypeID() {
        return typeID;
    }

    public void setTypeID(String typeID) {
        this.typeID = typeID;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeFatherID() {
        return typeFatherID;
    }

    public void setTypeFatherID(String typeFatherID) {
        this.typeFatherID = typeFatherID;
    }

    public String getTypeFatherName() {
        return typeFatherName;
    }

    public void setTypeFatherName(String typeFatherName) {
        this.typeFatherName = typeFatherName;
    }

    public String getTypeGraFaID() {
        return typeGraFaID;
    }

    public void setTypeGraFaID(String typeGraFaID) {
        this.typeGraFaID = typeGraFaID;
    }

    public String getTypeGraFaName() {
        return typeGraFaName;
    }

    public void setTypeGraFaName(String typeGraFaName) {
        this.typeGraFaName = typeGraFaName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getCreateUserType() {
        return createUserType;
    }

    public void setCreateUserType(String createUserType) {
        this.createUserType = createUserType;
    }

    public String getCreateUserTypeID() {
        return createUserTypeID;
    }

    public void setCreateUserTypeID(String createUserTypeID) {
        this.createUserTypeID = createUserTypeID;
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
}
