package com.a21zhewang.constructionapp.bean;

import java.util.List;

/**
 * Created by zhewang_ljw on 2017/6/14.
 * 控件命名规范：当前界面名+控件名+名字
 * 此文件作用：
 */

public class ZgjlBean {


    /**
     * recordRLID : d2975ed3-8a35-4286-b3d0-7136891bf933
     * typeID : C01013001
     * typeName : 梁、墙主筋锚固、搭接不规范
     * typeFatherID : C01013
     * typeFatherName : 钢筋工程
     * title : 关于顶模的工序验收整改
     * recEtpID : QY201704191139
     * recEtpShortName : 土建一部
     * createUserID : zhangsan
     * createUserName : 张三
     * createTime : 2017-06-09 17:31:16
     * etpID : QY201704191139
     * etpShortName : 总承包
     * files : [{"fileBrief":"整改前","url":"http://localhost:12224/upload/images/xx.jpg"}]
     */

    private String recordRLID;
    private String typeID;
    private String typeName;
    private String typeFatherID;
    private String typeFatherName;
    private String title;
    private String recEtpID;
    private String recEtpShortName;
    private String createUserID;
    private String createUserName;
    private String createTime;
    private String etpID;
    private String etpShortName;
private  String dealResults;
    private  String status;

    public String getBtnID() {
        return btnID;
    }

    public void setBtnID(String btnID) {
        this.btnID = btnID;
    }

    private  String btnID;
    public String getDealResults() {
        return dealResults;
    }

    public void setDealResults(String dealResults) {
        this.dealResults = dealResults;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    /**
     * fileBrief : 整改前
     * url : http://localhost:12224/upload/images/xx.jpg
     */
private String content;
    private List<FilesBean> files;

    public String getRecordRLID() {
        return recordRLID;
    }

    public void setRecordRLID(String recordRLID) {
        this.recordRLID = recordRLID;
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

    public List<FilesBean> getFiles() {
        return files;
    }

    public void setFiles(List<FilesBean> files) {
        this.files = files;
    }


}
