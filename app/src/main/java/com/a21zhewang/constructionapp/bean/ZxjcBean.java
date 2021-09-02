package com.a21zhewang.constructionapp.bean;

import java.util.List;

/**
 * Created by zhewang_ljw on 2017/8/1.
 * 控件命名规范：当前界面名+控件名+名字
 * 此文件作用：
 */

public class ZxjcBean {

    /**
     * recordList : [{"recordID":"csid01","projectID":"GC10001","proShortName":"绿地636项目","checkTime":"2017-07-24 00:00:00","title":"测试内容标题","content":"测试具体内容","createUserID":"rzx","createUserName":"rzx","etpID":"QY201704191139","etpShortName":"总承包"},{"recordID":"csid02","projectID":"GC10001","proShortName":"绿地636项目","checkTime":"2017-07-23 00:00:00","title":"测试内容标题2","content":"测试具体内容2","createUserID":"rzx","createUserName":"rzx","etpID":"QY201704191139","etpShortName":"总承包"}]
     * total : 2
     */

    private String total;
    /**
     * recordID : csid01
     * projectID : GC10001
     * proShortName : 绿地636项目
     * checkTime : 2017-07-24 00:00:00
     * title : 测试内容标题
     * content : 测试具体内容
     * createUserID : rzx
     * createUserName : rzx
     * etpID : QY201704191139
     * etpShortName : 总承包
     */

    private List<RecordListBean> recordList;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<RecordListBean> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<RecordListBean> recordList) {
        this.recordList = recordList;
    }

    public static class RecordListBean {
        private String recordID;
        private String projectID;
        private String proShortName;

        /**重点检查 检查区域ID*/
        private String checkRegionId;
        /**重点检查 检查区域名称*/
        private String checkRegionName;
        /**重点检查_计划时间*/
        private String planTime;

        private String checkTime;
        private String title;
        private String content;
        private String createUserID;
        private String createUserName;
        private String etpID;
        private String etpShortName;
        private String status;
        /**检查类型*/
        private int checkType;
        /**检查率*/
        private int checkRate;

        public int getCheckRate() {
            return checkRate;
        }

        public void setCheckRate(int checkRate) {
            this.checkRate = checkRate;
        }

        public int getCheckType() {
            return checkType;
        }

        public void setCheckType(int checkType) {
            this.checkType = checkType;
        }



        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getErrorFinishPercent() {
            return errorFinishPercent;
        }

        public void setErrorFinishPercent(String errorFinishPercent) {
            this.errorFinishPercent = errorFinishPercent;
        }

        private String errorFinishPercent;
        public String getRecordID() {
            return recordID;
        }

        public void setRecordID(String recordID) {
            this.recordID = recordID;
        }

        public String getProjectID() {
            return projectID;
        }

        public void setProjectID(String projectID) {
            this.projectID = projectID;
        }

        public String getProShortName() {
            return proShortName;
        }

        public void setProShortName(String proShortName) {
            this.proShortName = proShortName;
        }

        public String getCheckTime() {
            return checkTime;
        }

        public void setCheckTime(String checkTime) {
            this.checkTime = checkTime;
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

        public String getCheckRegionId() {
            return checkRegionId;
        }

        public void setCheckRegionId(String checkRegionId) {
            this.checkRegionId = checkRegionId;
        }

        public String getCheckRegionName() {
            return checkRegionName;
        }

        public void setCheckRegionName(String checkRegionName) {
            this.checkRegionName = checkRegionName;
        }

        public String getPlanTime() {
            return planTime;
        }

        public void setPlanTime(String planTime) {
            this.planTime = planTime;
        }
    }
}
