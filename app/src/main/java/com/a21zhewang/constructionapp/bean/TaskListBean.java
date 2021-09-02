package com.a21zhewang.constructionapp.bean;

/**
 * Created by Administrator on 2021/8/2.
 */

public class TaskListBean {


    private String recordId;
    private int taskType;
    private String title;
    private String content;
    private int status;
    private String createDate;
    private String rectUserId;
    private String rectUserName;
    private String createId;
    private String createName;

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getRectUserName() {
        return rectUserName;
    }

    public void setRectUserName(String rectUserName) {
        this.rectUserName = rectUserName;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getRectUserId() {
        return rectUserId;
    }

    public void setRectUserId(String rectUserId) {
        this.rectUserId = rectUserId;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    public int getTaskType() {
        return taskType;
    }

    public void setTaskType(int taskType) {
        this.taskType = taskType;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
