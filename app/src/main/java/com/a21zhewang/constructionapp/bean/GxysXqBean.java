package com.a21zhewang.constructionapp.bean;

import java.util.List;

/**
 * Created by zhewang_ljw on 2017/6/13.
 * 控件命名规范：当前界面名+控件名+名字
 * 此文件作用：
 */

public class GxysXqBean {

    /**
     * projectID : GC10001
     * projectShortName : 绿地636项目
     * projectAreaName : 顶模
     * floor : 1F
     * elevation :
     * subProjectID : SubPj10001
     * subProjectName : 核心筒钢骨架安装
     * title : 关于顶模的SubPj10001的工序验收
     * content : 工序施工已经完成，申请工序验收
     * applyTime : 2017-07-15
     * reformtime : 2017-07-02
     * createUserID : wuyuhuai
     * createUserName : 吴玉怀
     * createTime : 2017-06-08 18:14:42
     * etpID : QY201704191139
     * etpShortName : 土建一部
     * status : 验收通过
     */

    private String projectID;
    private String projectShortName;
    private String projectAreaName;
    private String projectType;
    private String floor;
    private String elevation;
    private String subProjectID;
    private String subProjectName;
    private String title;
    private String content;
    private String applyTime;
    private String reformtime;
    private String createUserID;
    private String createUserName;
    private String createTime;
    private String etpID;
    private String etpShortName;
    private String status;

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
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

    private List<FilesBean> files;
    private List<ReadLogsBean> readLogs;
    private List<DealLogsBean> dealLogs;
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

    public String getElevation() {
        return elevation;
    }

    public void setElevation(String elevation) {
        this.elevation = elevation;
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

    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }

    public String getReformtime() {
        return reformtime;
    }

    public void setReformtime(String reformtime) {
        this.reformtime = reformtime;
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
}
