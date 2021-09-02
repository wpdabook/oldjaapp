package com.a21zhewang.constructionapp.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2021/8/2.
 */

public class TaskDetailBean implements Serializable {

    private String content;
    private int status;
    public List<String> questUrlList = new ArrayList<>();
    public boolean isShowAll = false;
    private String remark;
    private String createDate;
    private String recordId;
    private boolean rectObjTag;
    private String projectName;
    public List<String> requestUrlList = new ArrayList<>();

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
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

    public List<String> getQuestUrlList() {
        return questUrlList;
    }

    public void setQuestUrlList(List<String> questUrlList) {
        this.questUrlList = questUrlList;
    }

    public boolean isShowAll() {
        return isShowAll;
    }

    public void setShowAll(boolean showAll) {
        isShowAll = showAll;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public boolean isRectObjTag() {
        return rectObjTag;
    }

    public void setRectObjTag(boolean rectObjTag) {
        this.rectObjTag = rectObjTag;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public List<String> getRequestUrlList() {
        return requestUrlList;
    }

    public void setRequestUrlList(List<String> requestUrlList) {
        this.requestUrlList = requestUrlList;

    }
}
