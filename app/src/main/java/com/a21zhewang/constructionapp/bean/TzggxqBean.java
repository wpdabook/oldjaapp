package com.a21zhewang.constructionapp.bean;

import java.util.List;

/**
 * Created by zhewang_ljw on 2017/5/23.
 * 控件命名规范：当前界面名+控件名+名字
 * 此文件作用：
 */

public class TzggxqBean {


    /**
     * projectID : GC10001
     * projectShortName :
     * typeName : 通知公告
     * title : 下周检查
     * content : 下周检查，做好准备
     * recEtpID : QY201704191154
     * recEtpShortName : 土建一部
     * createUserID : zhangsan
     * createUserName : 张三
     * createTime : 05-22 17:54
     * etpID : QY201704191154
     * etpShortName : 土建一部
     * files : [{"fileBrief":"这是测试的图片描述","url":"http://localhost:12224/upload/images/xx.jpg"}]
     * readLogs : [{"userName":"刘冰洋","etpShortName":"土建一部","status":"未读","time":"05-23 13:25"},{"userName":"隆谨蔚","etpShortName":"土建一部","status":"未读","time":"05-23 13:25"},{"userName":"吴玉怀","etpShortName":"土建一部","status":"未读","time":"05-23 13:25"}]
     */

    private String projectID;
    private String projectShortName;
    private String typeName;
    private String title;
    private String content;
    private String recEtpID;
    private String recEtpShortName;
    private String createUserID;
    private String createUserName;
    private String createTime;
    private String etpID;
    private String etpShortName;
    private String createName;
    private String createDate;
    private String remark;
    private List<CompanyDate> itemCompanyList;
    private List<ElementDate> itemElementList;

    public List<CompanyDate> getItemCompanyList() {
        return itemCompanyList;
    }

    public void setItemCompanyList(List<CompanyDate> itemCompanyList) {
        this.itemCompanyList = itemCompanyList;
    }

    public List<ElementDate> getItemElementList() {
        return itemElementList;
    }

    public void setItemElementList(List<ElementDate> itemElementList) {
        this.itemElementList = itemElementList;
    }

    /**
     * fileBrief : 这是测试的图片描述
     * url : http://localhost:12224/upload/images/xx.jpg
     */

    private List<FilesBean> files;

    public String getCreateDate() {
        return createDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    /**
     * userName : 刘冰洋
     * etpShortName : 土建一部
     * status : 未读
     * time : 05-23 13:25
     */

    private List<ReadLogsBean> readLogs;
    private List<DealLog> dealLogs;

    public List<DealLog> getDealLogs() {
        return dealLogs;
    }

    public void setDealLogs(List<DealLog> dealLogs) {
        this.dealLogs = dealLogs;
    }

    public String getProjectID() {
        return projectID;
    }

    public void setProjectID(String projectID) {
        this.projectID = projectID;
    }

    public String getProjectShortName() {
        return projectShortName;
    }

    public void setProjectShortName(String projectShortName) {
        this.projectShortName = projectShortName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
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

    public List<ReadLogsBean> getReadLogs() {
        return readLogs;
    }

    public void setReadLogs(List<ReadLogsBean> readLogs) {
        this.readLogs = readLogs;
    }

    public static class FilesBean {
        private String fileBrief;
        private String url;

        public String getFileBrief() {
            return fileBrief;
        }

        public void setFileBrief(String fileBrief) {
            this.fileBrief = fileBrief;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class ReadLogsBean {
        private String userName;
        private String etpShortName;
        private String status;
        private String time;
private String userPhone;

        public String getUserPhone() {
            return userPhone;
        }

        public void setUserPhone(String userPhone) {
            this.userPhone = userPhone;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getEtpShortName() {
            return etpShortName;
        }

        public void setEtpShortName(String etpShortName) {
            this.etpShortName = etpShortName;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
