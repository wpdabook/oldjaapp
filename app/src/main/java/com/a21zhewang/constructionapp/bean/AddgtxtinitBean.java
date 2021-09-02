package com.a21zhewang.constructionapp.bean;

import android.widget.Button;

import java.util.List;

/**
 * Created by zhewang_ljw on 2017/4/27.
 * 控件命名规范：当前界面名+控件名+名字
 * 此文件作用：
 */

public class AddgtxtinitBean {

    /**
     * etpID : QY201704191155
     * etpShortName : 监理单位
     * isOwner : 0
     * userID : 13627210698
     * userLevel : 20
     * userName : 范传斌
     * userType : 分包负责人
     * userTypeID : C01001006
     */

    private List<UserInfoListBean> userInfoList;

    private List<Etp> transEtpList;

    public List<Etp> getTransEtpList() {
        return transEtpList;
    }

    public void setTransEtpList(List<Etp> transEtpList) {
        this.transEtpList = transEtpList;
    }

    public List<UserInfoListBean> getRecentUserList() {
        return recentUserList;
    }

    public void setRecentUserList(List<UserInfoListBean> recentUserList) {
        this.recentUserList = recentUserList;
    }

    private List<UserInfoListBean> recentUserList;
    /**
     * projectID : GC10001
     * projectShortName : 绿地636项目
     * projectAreaList : [{"projectAreaName":"核心筒内筒","subProjectList":[{"subProjectID":"SubPj10023","subProjectName":"内筒楼板砼浇筑"}]},{"projectAreaName":"顶模","subProjectList":[{"subProjectID":"SubPj10002","subProjectName":"核心筒钢骨筋安装"},{"subProjectID":"SubPj10003","subProjectName":"剪力墙铝膜合模"},{"subProjectID":"SubPj10004","subProjectName":"剪力墙砼浇筑"},{"subProjectID":"SubPj10006","subProjectName":"顶模顶升"}]},{"projectAreaName":"外框","subProjectList":[{"subProjectID":"SubPj10012","subProjectName":"外框楼板钢筋绑扎"},{"subProjectID":"SubPj10013","subProjectName":"外框楼板砼浇筑"},{"subProjectID":"SubPj10014","subProjectName":"外框巨柱钢筋绑扎"},{"subProjectID":"SubPj10015","subProjectName":"外框巨柱模板支架工程"},{"subProjectID":"SubPj10016","subProjectName":"外框巨柱砼浇筑"},{"subProjectID":"SubPj10017","subProjectName":"爬模爬升"}]},{"projectAreaName":"核心筒内筒","subProjectList":[{"subProjectID":"SubPj10022","subProjectName":"内筒楼板钢筋绑扎"}]}]
     */

    private List<Project> projectList;

    public List<Etp> getEtpList() {
        return etpList;
    }

    public void setEtpList(List<Etp> etpList) {
        this.etpList = etpList;
    }

    public List<Etp> getRecentEtpList() {
        return recentEtpList;
    }

    public void setRecentEtpList(List<Etp> recentEtpList) {
        this.recentEtpList = recentEtpList;
    }

    private List<Etp>  etpList;
    private  List<Etp> recentEtpList;
    public List<UserInfoListBean> getUserInfoList() {
        return userInfoList;
    }

    public void setUserInfoList(List<UserInfoListBean> userInfoList) {
        this.userInfoList = userInfoList;
    }

    public List<Project> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<Project> projectList) {
        this.projectList = projectList;
    }

    public static class UserInfoListBean {
        private String etpID;
        private String etpShortName;
        private int isOwner;
        private String userID;
        private String userLevel;
        private String userName;
        private String userType;
        private String userTypeID;

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

        public int getIsOwner() {
            return isOwner;
        }

        public void setIsOwner(int isOwner) {
            this.isOwner = isOwner;
        }

        public String getUserID() {
            return userID;
        }

        public void setUserID(String userID) {
            this.userID = userID;
        }

        public String getUserLevel() {
            return userLevel;
        }

        public void setUserLevel(String userLevel) {
            this.userLevel = userLevel;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserType() {
            return userType;
        }

        public void setUserType(String userType) {
            this.userType = userType;
        }

        public String getUserTypeID() {
            return userTypeID;
        }

        public void setUserTypeID(String userTypeID) {
            this.userTypeID = userTypeID;
        }
    }




}
