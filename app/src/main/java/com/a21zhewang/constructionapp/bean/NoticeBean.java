package com.a21zhewang.constructionapp.bean;

/**
 * Created by 56864 on 2019/7/7.
 */

public class NoticeBean {

    private String companyJson;
    private String content;
    private String createCompanyId;
    private String createCompanyName;
    private String createDate;
    private String createUserId;
    private String createUserName;
    private String id;
    private String projectId;
    private String projectName;
    /**
     * sendType=1 所有用户默认已读   只有sendType=2 时列表区分是否已读
     */
    private String sendType;
    private int status;
    private String title;
    /**
     * --1:发起	2：未读	3：已读
     */
    private int  readStatus;

    public int getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(int readStatus) {
        this.readStatus = readStatus;
    }

    public String getCompanyJson() {
        return companyJson;
    }

    public void setCompanyJson(String companyJson) {
        this.companyJson = companyJson;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateCompanyId() {
        return createCompanyId;
    }

    public void setCreateCompanyId(String createCompanyId) {
        this.createCompanyId = createCompanyId;
    }

    public String getCreateCompanyName() {
        return createCompanyName;
    }

    public void setCreateCompanyName(String createCompanyName) {
        this.createCompanyName = createCompanyName;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getSendType() {
        return sendType;
    }

    public void setSendType(String sendType) {
        this.sendType = sendType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
