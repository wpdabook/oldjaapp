package com.a21zhewang.constructionapp.bean;

import java.util.List;

/**
 * Created by zhewang_ljw on 2017/5/16.
 * 控件命名规范：当前界面名+控件名+名字
 * 此文件作用：
 */

public class AqscxqBean {


    /**
     * projectID : GC10001
     * projectShortName : 绿地636项目
     * projectAreaName : 外框
     * floor : 3F
     * typeID : C01007001001
     * typeName : 电工带电操作
     * typeGraFaID : C01007
     * typeGraFaName : 触电
     * typeFatherID : C01007001
     * typeFatherName : 人的不安全行为
     * title : 关于外框的安全生产整改
     * content : 这是测试的整改内容
     * cutoffTime : 2017-05-10
     * recEtpID : QY201704191154
     * recEtpShortName : 土建一部
     * createUserID : lisi
     * createUserName : 李四
     * createTime : 2017-05-10 17:13:54
     * etpID : QY201704191139
     * etpShortName : 总承包
     * status : 整改中
     * files : [{"fileBrief":"这是测试的图片描述","url":"http://localhost:12224/upload/images/xx.jpg"}]
     * readLogs : [{"userName":"张三","etpShortName":"总承包","status":"已读","time":"05-16 10:10"},{"userName":"王五","etpShortName":"","status":"未读","time":"05-16 10:10"},{"userName":"李四","etpShortName":"总承包","status":"发起","time":"05-10 17:13"}]
     * dealLogs : [{"userName":"发起人：李四","etpShortName":"总承包","content":"李四发起协调请求","time":"05-10 17:13"}]
     * buttons : [{"btnID":"cmBtn5","btnName":"回复"},{"btnID":"cmBtn2","btnName":"已整改"}]
     */

    private String projectID;
    private String projectShortName;
    private String projectAreaName;
    private String projectType;
    private String floor;
    private String typeID;
    private String typeName;
    private String typeGraFaID;
    private String typeGraFaName;
    private String typeFatherID;
    private String typeFatherName;
    private String title;
    private String content;
    private String cutoffTime;
    private String recEtpID;
    private String recEtpShortName;
    private String createUserID;
    private String createUserName;
    private String createTime;
    private String etpID;
    private String etpShortName;
    private String status;
    private String subProjectID;
    private String subProjectName;
    private String L;
    private String D;
    private String C;
    private String Rn;
    private String LText;

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
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

    public String getElevation() {
        return elevation;
    }

    public void setElevation(String elevation) {
        this.elevation = elevation;
    }

    private String elevation;
    /**
     * fileBrief : 这是测试的图片描述
     * url : http://localhost:12224/upload/images/xx.jpg
     */

    private List<FilesBean> files;
    /**
     * userName : 张三
     * etpShortName : 总承包
     * status : 已读
     * time : 05-16 10:10
     */

    private List<ReadLogsBean> readLogs;
    /**
     * userName : 发起人：李四
     * etpShortName : 总承包
     * content : 李四发起协调请求
     * time : 05-10 17:13
     */

    private List<DealLogsBean> dealLogs;
    /**
     * btnID : cmBtn5
     * btnName : 回复
     */

    private List<ButtonsBean> buttons;

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

    public String getProjectAreaName() {
        return projectAreaName;
    }

    public void setProjectAreaName(String projectAreaName) {
        this.projectAreaName = projectAreaName;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
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

    public String getTypeGraFaID() {
        return typeGraFaID;
    }

    public void setTypeGraFaID(String typeGraFaID) {
        this.typeGraFaID = typeGraFaID;
    }

    public String getTypeGraFaName() {
        return typeGraFaName;
    }

    public void setTypeGraFaName(String typeGraFaName) {
        this.typeGraFaName = typeGraFaName;
    }

    public String getTypeFatherID() {
        return typeFatherID;
    }

    public void setTypeFatherID(String typeFatherID) {
        this.typeFatherID = typeFatherID;
    }

    public String getTypeFatherName() {
        return typeFatherName;
    }

    public void setTypeFatherName(String typeFatherName) {
        this.typeFatherName = typeFatherName;
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

    public String getCutoffTime() {
        return cutoffTime;
    }

    public void setCutoffTime(String cutoffTime) {
        this.cutoffTime = cutoffTime;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public List<ButtonsBean> getButtons() {
        return buttons;
    }

    public void setButtons(List<ButtonsBean> buttons) {
        this.buttons = buttons;
    }

    public String getL() {
        return L;
    }

    public void setL(String l) {
        L = l;
    }

    public String getD() {
        return D;
    }

    public void setD(String d) {
        D = d;
    }

    public String getC() {
        return C;
    }

    public void setC(String c) {
        C = c;
    }

    public String getRn() {
        return Rn;
    }

    public void setRn(String rn) {
        Rn = rn;
    }

    public String getLText() {
        return LText;
    }

    public void setLText(String LText) {
        this.LText = LText;
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

    public static class ButtonsBean {
        private String btnID;
        private String btnName;

        public String getBtnID() {
            return btnID;
        }

        public void setBtnID(String btnID) {
            this.btnID = btnID;
        }

        public String getBtnName() {
            return btnName;
        }

        public void setBtnName(String btnName) {
            this.btnName = btnName;
        }
    }
}
