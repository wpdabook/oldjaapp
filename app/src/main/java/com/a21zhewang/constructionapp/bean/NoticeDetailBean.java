package com.a21zhewang.constructionapp.bean;

import java.util.List;

/**
 * Created by WP-PC on 2019/7/8.
 */

public class NoticeDetailBean {

    private String id;
    private String projectId;
    private String projectName;
    private String title;
    private String content;
    private String status;
    private String sendType;
    private String companyJson;
    private String createUserId;
    private String createUserName;
    private String createCompanyId;
    private String createCompanyName;
    private String createDate;
    private List<FilesBean> itemFileList;
    private List<ReadLogsBean> itemReadList;

    public List<ReadLogsBean> getItemReadList() {
        return itemReadList;
    }

    public List<FilesBean> getItemFileList() {
        return itemFileList;
    }

    public void setItemFileList(List<FilesBean> itemFileList) {
        this.itemFileList = itemFileList;
    }

    public void setItemReadList(List<ReadLogsBean> itemReadList) {
        this.itemReadList = itemReadList;
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

    public String getSendType() {
        return sendType;
    }

    public void setSendType(String sendType) {
        this.sendType = sendType;
    }

    public String getCompanyJson() {
        return companyJson;
    }

    public void setCompanyJson(String companyJson) {
        this.companyJson = companyJson;
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


    public static class FilesBean {
        private String fileBrief;
        private String url;
        private String viewUrl;
        public String getFileBrief() {
            return fileBrief;
        }

        public void setFileBrief(String fileBrief) {
            this.fileBrief = fileBrief;
        }

        public String getUrl() {
            return url;
        }

        public String getViewUrl() {
            return viewUrl;
        }

        public void setViewUrl(String viewUrl) {
            this.viewUrl = viewUrl;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
    public static class ReadLogsBean {
        private String id;
        private String pz_NoticeId;
        private String userId;
        private String userName;
        private String companyId;
        private String companyName;
        private int status;
        private String readTime;
        private int sort;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPz_NoticeId() {
            return pz_NoticeId;
        }

        public void setPz_NoticeId(String pz_NoticeId) {
            this.pz_NoticeId = pz_NoticeId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getCompanyId() {
            return companyId;
        }

        public void setCompanyId(String companyId) {
            this.companyId = companyId;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getReadTime() {
            return readTime;
        }

        public void setReadTime(String readTime) {
            this.readTime = readTime;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }
    }
}
