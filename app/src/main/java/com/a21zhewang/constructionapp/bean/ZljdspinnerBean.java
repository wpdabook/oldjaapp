package com.a21zhewang.constructionapp.bean;

import java.util.List;

/**
 * Created by zhewang_ljw on 2017/5/15.
 * 控件命名规范：当前界面名+控件名+名字
 * 此文件作用：
 */

public class ZljdspinnerBean {


    /**
     * typeID : C01013
     * typeName : 钢筋工程
     * msgTypes : [{"typeID":"C01013001","typeName":"梁、墙主筋锚固、搭接不规范"},{"typeID":"C01013002","typeName":"剪力墙错位搭接设置不合理"},{"typeID":"C01013003","typeName":"柱筋收头及箍筋随意割断"},{"typeID":"C01013004","typeName":"梁底跳扣绑扎或不绑扎"},{"typeID":"C01013005","typeName":"直螺纹连接丝扣不规范"},{"typeID":"C01013006","typeName":"各类焊接搭边长度不够"},{"typeID":"C01013007","typeName":"高梆梁腰筋偏位、绑扎不牢固"},{"typeID":"C01013008","typeName":"弯起钢筋不定位、不加固"},{"typeID":"C01013009","typeName":"多排钢筋定位不合理、错位"},{"typeID":"C01013010","typeName":"箍筋未按图纸设置加密"},{"typeID":"C01013011","typeName":"梁柱节点漏箍筋或间距不匀"},{"typeID":"C01013012","typeName":"箍筋加工尺寸不规范"},{"typeID":"C01013013","typeName":"不按方案密度放置\u201c马凳筋\u201d"},{"typeID":"C01013014","typeName":"各类构件钢筋保护层缺偏差大"},{"typeID":"C01013015","typeName":"浇筑过程钢筋成品保护无措施"},{"typeID":"C01013016","typeName":"楼梯钢筋不按图集要求设置"},{"typeID":"C01013017","typeName":"同一受力区存在不同连接形式"},{"typeID":"C01013018","typeName":"接头百分率超过规定"},{"typeID":"C01013019","typeName":"竖向构件起步箍筋位置偏差"},{"typeID":"C01013020","typeName":"钢筋原材焊接试件强度不达标"},{"typeID":"C01013021","typeName":"外挑板钢筋加工安装不规范"},{"typeID":"C01013022","typeName":"后浇带施工缝钢筋处理不规范"}]
     */

    private List<MsgTypesBean> msgTypes;
    /**
     * status : 未读
     * color : FF0001
     */

    private List<MsgStatusBean> msgStatus;

    public List<MsgTypesBean> getMsgTypes() {
        return msgTypes;
    }

    public void setMsgTypes(List<MsgTypesBean> msgTypes) {
        this.msgTypes = msgTypes;
    }

    public List<MsgStatusBean> getMsgStatus() {
        return msgStatus;
    }

    public void setMsgStatus(List<MsgStatusBean> msgStatus) {
        this.msgStatus = msgStatus;
    }

    public static class MsgTypesBean {
        private String typeID;
        private String typeName;
        /**
         * typeID : C01013001
         * typeName : 梁、墙主筋锚固、搭接不规范
         */

        private List<MsgTypeBean> msgTypes;

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

        public List<MsgTypeBean> getMsgTypes() {
            return msgTypes;
        }

        public void setMsgTypes(List<MsgTypeBean> msgTypes) {
            this.msgTypes = msgTypes;
        }

        public static class MsgTypeBean {
            private String typeID;
            private String typeName;

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
