package com.a21zhewang.constructionapp.bean;

import java.util.List;

/**
 * Created by zhewang_ljw on 2017/6/13.
 * 控件命名规范：当前界面名+控件名+名字
 * 此文件作用：
 */

public class GxysinitB {

    /**
     * etpID : QY201704191154
     * etpShortName : 土建一部
     */

    private List<EtpsBean> etps;
    /**
     * status : 未读
     * color : FF0001
     */

    private List<MsgStatusBean> msgStatus;

    public List<EtpsBean> getEtps() {
        return etps;
    }

    public void setEtps(List<EtpsBean> etps) {
        this.etps = etps;
    }

    public List<MsgStatusBean> getMsgStatus() {
        return msgStatus;
    }

    public void setMsgStatus(List<MsgStatusBean> msgStatus) {
        this.msgStatus = msgStatus;
    }

    public static class EtpsBean {
        private String etpID;
        private String etpShortName;

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
    }

    public static class MsgStatusBean {
        private String status;
        private String color;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }
    }
}
