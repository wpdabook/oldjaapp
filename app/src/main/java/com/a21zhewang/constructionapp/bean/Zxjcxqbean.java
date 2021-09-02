package com.a21zhewang.constructionapp.bean;

import java.util.List;

/**
 * Created by zhewang_ljw on 2017/9/15.
 *
 * @auto
 */

public class Zxjcxqbean {

    /**
     * recordID : d48c3f6b-1152-4e43-aee3-e277458775b5
     * projectID : GC10001
     * proShortName : 绿地636项目
     * checkTime : 2017/7/23 0:00:00
     * title : 测试内容标题2
     * content : 测试具体内容2
     * status : 0
     * createUserID : rzx
     * createUserName : rzx
     * etpID : QY201704191139
     * etpShortName : 总承包
     * errorNumber : 0
     */

    private String recordID;
    private String projectID;
    private String proShortName;
    private String checkTime;
    private String title;
    private String content;
    private String status;
    private String createUserID;
    private String createUserName;
    private String etpID;
    private String etpShortName;
    private int errorNumber;
    private int rectPosition;

    /**重点检查-整改需要*/
    private String projectType;
    private List<Project> projectList;

    public int getRectPosition() {
        return rectPosition;
    }

    public void setRectPosition(int rectPosition) {
        this.rectPosition = rectPosition;
    }

    public List<Project> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<Project> projectList) {
        this.projectList = projectList;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public List<CheckTypeOne> getCheckItemList() {
        return checkItemList;
    }

    public void setCheckItemList(List<CheckTypeOne> checkItemList) {
        this.checkItemList = checkItemList;
    }

    private List<CheckTypeOne> checkItemList;
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

    public String getProShortName() {
        return proShortName;
    }

    public void setProShortName(String proShortName) {
        this.proShortName = proShortName;
    }

    public String getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(String checkTime) {
        this.checkTime = checkTime;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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


    public void setErrorNumber(int errorNumber) {
        this.errorNumber = errorNumber;
    }

    public int getErrorNumber() {
        return errorNumber;
    }
}
