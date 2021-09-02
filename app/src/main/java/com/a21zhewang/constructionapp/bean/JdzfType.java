package com.a21zhewang.constructionapp.bean;

import java.util.List;

/**
 * Created by WP-PC on 2019/6/3.
 */

public class JdzfType {


    private List<JdzfStatus> backdata;

    private List<MsgStatusBean> msgStatusBeen;

    public static class JdzfStatus {
        private String typeId;
        private String typeName;

        public String getTypeId() {
            return typeId;
        }

        public void setTypeId(String typeId) {
            this.typeId = typeId;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }
    }
    public static class MsgStatusBean {
        private String status;
        private String color;
        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

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

    public List<JdzfStatus> getBackdata() {
        return backdata;
    }

    public void setBackdata(List<JdzfStatus> backdata) {
        this.backdata = backdata;
    }

    public List<MsgStatusBean> getMsgStatusBeen() {
        return msgStatusBeen;
    }

    public void setMsgStatusBeen(List<MsgStatusBean> msgStatusBeen) {
        this.msgStatusBeen = msgStatusBeen;
    }
}
