package com.a21zhewang.constructionapp.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2021/3/11.
 */

public class JygzNineGridModel implements Serializable {

    private String dicName;
    private String status;
    public List<String> questUrlList = new ArrayList<>();
    public boolean isShowAll = false;
    private String remark;
    private String defaultId;
    private String recordId;
    private boolean rectObjTag;
    private String projectName;
    private String rectUserId;
    private String questionContent;
    public List<String> requestUrlList = new ArrayList<>();

    public String getDefaultId() {
        return defaultId;
    }

    public void setDefaultId(String defaultId) {
        this.defaultId = defaultId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getRectUserId() {
        return rectUserId;
    }

    public void setRectUserId(String rectUserId) {
        this.rectUserId = rectUserId;
    }

    public String getDicName() {
        return dicName;
    }

    public void setDicName(String dicName) {
        this.dicName = dicName;
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

    public List<String> getRequestUrlList() {
        return requestUrlList;
    }

    public void setRequestUrlList(List<String> requestUrlList) {
        this.requestUrlList = requestUrlList;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }
}
