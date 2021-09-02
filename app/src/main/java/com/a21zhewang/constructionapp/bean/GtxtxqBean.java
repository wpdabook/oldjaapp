package com.a21zhewang.constructionapp.bean;

import java.util.List;

/**
 * Created by zhewang_ljw on 2017/4/26.
 * 控件命名规范：当前界面名+控件名+名字
 * 此文件作用：
 */

public class GtxtxqBean {

    /**
     * projectID : GC10001
     * projectShortName : 绿地636项目
     * projectAreaName : 顶模
     * subProjectID : SubPj10002
     * subProjectName : 核心筒钢骨筋安装
     * typeID : C01005001
     * typeName : 机械
     * etpID : QY201704191154
     * etpShortName : 土建一部
     * content : 12341234
     * createUserName : 吴玉怀
     * floor : 12F
     * elevation :
     * status : 处理中
     * title : 关于顶模的工序沟通协调
     * createYear : 2017
     * createTime : 05-19 17:39
     * files : [{"fileBrief":"这是一个无用字段","url":"http://192.168.1.90:8001/upload/images/jpg201705190539531.jpg","viewUrl":"http://192.168.1.90:8001/upload/images/jpg201705190539531.jpg"}]
     * readLogs : [{"userName":"张三","etpShortName":"总承包","status":"已读","time":"05-19 18:13","userPhone":""},{"userName":"吴玉怀","etpShortName":"土建一部","status":"发起","time":"05-19 17:39","userPhone":""}]
     * dealLogs : [{"userName":"发起人：吴玉怀","etpShortName":"土建一部","content":"发起沟通协调","time":"05-19 17:39"}]
     * buttons : [{"btnID":"cmBtn5","btnName":"回复"},{"btnID":"cmBtn3","btnName":"已处理"},{"btnID":"cmBtn7","btnName":"转发"},{"btnID":"cmBtn4","btnName":"上报"}]
     */

    private String projectID;
    private String projectShortName;
    private String projectAreaName;
    private String subProjectID;
    private String subProjectName;
    private String typeID;
    private String projectType;
    private String typeName;
    private String etpID;
    private String etpShortName;
    private String content;
    private String createUserName;
    private String floor;
    private String elevation;
    private String status;
    private String title;
    private String createYear;
    private String createTime;
    /**
     * fileBrief : 这是一个无用字段
     * url : http://192.168.1.90:8001/upload/images/jpg201705190539531.jpg
     * viewUrl : http://192.168.1.90:8001/upload/images/jpg201705190539531.jpg
     */

    private List<FilesBean> files;
    /**
     * userName : 张三
     * etpShortName : 总承包
     * status : 已读
     * time : 05-19 18:13
     * userPhone :
     */

    private List<ReadLogsBean> readLogs;
    /**
     * userName : 发起人：吴玉怀
     * etpShortName : 土建一部
     * content : 发起沟通协调
     * time : 05-19 17:39
     */

    private List<DealLogsBean> dealLogs;
    /**
     * btnID : cmBtn5
     * btnName : 回复
     */

    private List<ButtonBean> buttons;

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

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getProjectAreaName() {
        return projectAreaName;
    }

    public void setProjectAreaName(String projectAreaName) {
        this.projectAreaName = projectAreaName;
    }

    public String getSubProjectID() {
        return subProjectID;
    }

    public void setSubProjectID(String subProjectID) {
        this.subProjectID = subProjectID;
    }

    public String getSubProjectName() {
        return subProjectName;
    }

    public void setSubProjectName(String subProjectName) {
        this.subProjectName = subProjectName;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getElevation() {
        return elevation;
    }

    public void setElevation(String elevation) {
        this.elevation = elevation;
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

    public String getCreateYear() {
        return createYear;
    }

    public void setCreateYear(String createYear) {
        this.createYear = createYear;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
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

    public List<DealLogsBean> getDealLogs() {
        return dealLogs;
    }

    public void setDealLogs(List<DealLogsBean> dealLogs) {
        this.dealLogs = dealLogs;
    }

    public List<ButtonBean> getButtons() {
        return buttons;
    }

    public void setButtons(List<ButtonBean> buttons) {
        this.buttons = buttons;
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

        public void setUrl(String url) {
            this.url = url;
        }

        public String getViewUrl() {
            return viewUrl;
        }

        public void setViewUrl(String viewUrl) {
            this.viewUrl = viewUrl;
        }
    }

    public static class ReadLogsBean {
        private String userName;
        private String etpShortName;
        private String status;
        private String time;
        private String userPhone;

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

        public String getUserPhone() {
            return userPhone;
        }

        public void setUserPhone(String userPhone) {
            this.userPhone = userPhone;
        }
    }

    public static class DealLogsBean {
        private String userName;
        private String etpShortName;
        private String content;
        private String time;

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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }


}
