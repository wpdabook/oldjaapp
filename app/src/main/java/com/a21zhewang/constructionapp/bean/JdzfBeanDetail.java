package com.a21zhewang.constructionapp.bean;

import java.util.List;

/**
 * 监督执法列表详情
 * Created by WP-PC on 2019/5/31.
 */

public class JdzfBeanDetail {

    private String conManager;
    private String createDate;
    private String createId;
    private String createName;
    private String id;
    private String projectId;
    private String projectName;
    private String status;
    private String title;
    private int otherStatus;
    private int type;
    private String otherStatusReason;
    private String content;
    private List<CompanyInfo> itemCompanyList;
    private List<ReadInfo>itemReadList;
    private List<FileBean> itemFileList;
    private List<ItemReplyList> itemReplyList;

    public List<ItemReplyList> getItemReplyList() {
        return itemReplyList;
    }

    public void setItemReplyList(List<ItemReplyList> itemReplyList) {
        this.itemReplyList = itemReplyList;
    }

    public String getOtherStatusReason() {
        return otherStatusReason;
    }

    public void setOtherStatusReason(String otherStatusReason) {
        this.otherStatusReason = otherStatusReason;
    }

    public int getOtherStatus() {
        return otherStatus;
    }

    public void setOtherStatus(int otherStatus) {
        this.otherStatus = otherStatus;
    }

    public List<FileBean> getItemFileList() {
        return itemFileList;
    }

    public void setItemFileList(List<FileBean> itemFileList) {
        this.itemFileList = itemFileList;
    }

    public String getConManager() {
        return conManager;
    }

    public void setConManager(String conManager) {
        this.conManager = conManager;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<CompanyInfo> getItemCompanyList() {
        return itemCompanyList;
    }

    public void setItemCompanyList(List<CompanyInfo> itemCompanyList) {
        this.itemCompanyList = itemCompanyList;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<ReadInfo> getItemReadList() {
        return itemReadList;
    }

    public void setItemReadList(List<ReadInfo> itemReadList) {
        this.itemReadList = itemReadList;
    }
}
