package com.a21zhewang.constructionapp.bean;

import java.util.List;

/**
 * Created by Administrator on 2021/3/26.
 */

public class DetailObj {

    private String recordId;
    private String content;
    private int status;
    private String statusName;
    private String createId;
    private String createName;
    private String createDate;
    private List<FileBean> fileDetailInfo;


    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
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

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
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

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public List<FileBean> getFileDetailInfo() {
        return fileDetailInfo;
    }

    public void setFileDetailInfo(List<FileBean> fileDetailInfo) {
        this.fileDetailInfo = fileDetailInfo;
    }
}
